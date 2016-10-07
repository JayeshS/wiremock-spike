package org.js;

import com.google.common.io.BaseEncoding;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class PortalDecrypt {

    public static void main(String[] args) throws Exception {
        // <machineKey decryptionKey="CF654475E7C075CC18442CC636DA59AFDF0DED3CFD1C99F0" validationKey="D5AF95901A6786690C6BFB1E369E7A2F976947B29C5F59BF300C123F08F20CE3F4327951736ECCB66651C5BCD4C314D36C89E9E2FDEA9209A959E85DB7E30224"/>


        String decrypted = new AESBouncyCastle().decrypt("80585704D785A9224C4BBA79A949AC151BCE3C004BE92E46CDFFF66B616F9B01313BDCBE703DFF2EEC206DC0D2BCACB68D31A8E8110C5C78CCED8A5E24C692D4DC8CA63DDC3510BEE31FDD50BE05785C4CBFC623051AF63C53F7D4CCD98E94BBF01D2EC0F090F5D7D71C983564A7D618384D1F934C2C06F10AA049A963A007B62FE456A97E467A2CA0563544277ED016FFA5DA17698D94722E98E750D3C9B89FD2235A3C97A3E5FFB596E91E89CAB32A669E53B95AB922901A10D051BA25510DC149F5FF6F28520F494DC94144DBE6C3DCB93268516C53038872C419B67BA79983FD44126D46A721DA95A3CE34692928A1AEAF5673503EFBD5E59670E275ED92ACC41B0158DBA82A3C3A8F7A174B1CCB11BB0605FEE2A4A4BD6CC19783D812D3303D9E7621557D31ADE031114E075DB08CE09CBD1C34958DCCF5E43D3EA17B4A8A9AF1B5A86F171415BBA0122B21B8D0863EC7308927DED678F6388703CD4239B0083FCFF90C77AD06A28A5CE7E8EF3A58C631385DF9A071C6E2A06DED2C91CD2CE91F193A8102129539290F07488D42035ED3625B890FE913530B824968C314",
                "CF654475E7C075CC18442CC636DA59AFDF0DED3CFD1C99F0");

        System.out.println("decrypted = " + decrypted);


        /*
        26 = -112
27 = 52
28 = 4
29 = 4
30 = 45
31 = -64
32 = -45
33 = 8
         */

//        Integer i = Integer.valueOf();

        ByteBuffer b = ByteBuffer.wrap(new byte[]{-112, 52, 4, 4, 45, -64, -45, 8});

        System.out.println("b.getInt() = " + b.getInt());
    }

    public static class AESBouncyCastle {

//        public String validate(String key, ) {


//        }


        public String decrypt(String enc, String key) throws Exception {

            byte[] bytesIn = Hex.decode(enc);
//            byte[] bytesIn = Base64.getDecoder().decode(enc.getBytes("UTF-8"));

//            byte[] ivBytes = Base64.getDecoder().decode("4bN8UHmJGfjhcFX6eSIPLw==");
            KeyParameter parameters = new KeyParameter(Hex.decode(key));
//            CipherParameters params = new ParametersWithIV(parameters, ivBytes);
//            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
            BufferedBlockCipher cipher = new BufferedBlockCipher(new CBCBlockCipher(new AESEngine()));

            // Decrypt all bytes that follow the IV
            cipher.init(false, parameters); // first param = encode/decode

            byte[] bytesOut = process(bytesIn, cipher);

            // And convert the result to a string
            return new String(bytesOut, Charset.forName("UTF-8"));
        }

        public String encrypt(String input, String key) throws Exception {

            byte[] bytesIn = input.getBytes("UTF-8");

            byte[] ivBytes = Base64.getDecoder().decode("4bN8UHmJGfjhcFX6eSIPLw==");
            CipherParameters params = new ParametersWithIV(new KeyParameter(Base64.getDecoder().decode(key)), ivBytes);
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());

            // Decrypt all bytes that follow the IV
            cipher.init(true, params); // first param = encode/decode

            byte[] bytesOut = process(bytesIn, cipher);

            return BaseEncoding.base64().encode(bytesOut);
        }

        private byte[] process(byte[] bytesIn, BufferedBlockCipher cipher) throws GeneralSecurityException {
            byte[] bytesOut;
            try {
                int buflen = cipher.getOutputSize(bytesIn.length);
                byte[] workingBuffer = new byte[buflen];
                int len = cipher.processBytes(bytesIn, 0, bytesIn.length, workingBuffer, 0);
                len += cipher.doFinal(workingBuffer, len);

                // Note that getOutputSize returns a number which includes space for "padding" bytes to be stored in.
                // However we don't want these padding bytes; the "len" variable contains the length of the *real* data
                // (which is always less than the return value of getOutputSize.
                bytesOut = new byte[len];
                System.arraycopy(workingBuffer, 0, bytesOut, 0, len);
            } catch (InvalidCipherTextException e) {
                throw new GeneralSecurityException("decode failed");
            } catch (RuntimeException e) {
                throw new GeneralSecurityException("failed", e);
            }
            return bytesOut;
        }

    }

}
