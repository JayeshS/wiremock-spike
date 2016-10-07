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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static org.joda.time.DateTimeZone.UTC;

public class SpikeEncrypt {

    private static final String KEY = "AwphL904WkJ4FxubNfIfx5yzlVSp+BEGPofQwr19BeE=";

    @Test
    public void should3() throws Exception {
        AESBouncyCastle aesBouncyCastle = new AESBouncyCastle();
        String decode = URLDecoder.decode("hiPewLWO964dyM3Y31DIag%3d%3d", "utf-8");
        System.out.println("decode = " + decode);
        String decrypt = aesBouncyCastle.decrypt(decode, KEY);
        System.out.println("decrypt = " + decrypt);
    }

    @Test
    public void shouldx2() throws Exception {
        AESBouncyCastle aesBouncyCastle = new AESBouncyCastle();
        String decrypt = aesBouncyCastle.decrypt("6kCksfNoiHAoE+v/JLQek+SwYcfeKutqIyrV+LJXmmZmviBjO+GJ2CdXFgiG13OI", "xHLGHBrGUFsPCbQjkDnFtI3nyn/FMep3UdOB6Bn8c6k=");
//        String decrypt2 = aesBouncyCastle.decrypt("zyqslAFvr7Ug4TtBbC5X2udY63CoEfIaXiDQAIl9u6eN01ApiomecQiCHBJULDT2vCx3tFnYv7SgEt3AIqopRyVJpDrCzzizcGQvf3B4OwvfyX3ibT63onPdfCfpslPcUkaz0oRqxyhqUp4CTNxq8tUtVfedPJQc/7cw3S8pSA9Otf7DEtUsvVmOBhA9KxPdHIutynuar0vmOnHvEAqP9JBi0iMZi7jsgXACkAxYD0g=", KEY);
//        String decrypt = aesBouncyCastle.decrypt("H5qi4cGw6DDM0FeUkERcBXQIYemPo3UGMq9WVsKEW8epUN9UnYuRFtj8r2ap9Lw7YyEOGa2dRNvwLxxISJ1DB4LJkPUQldRpNMaORHtbwPeh3s0Qq9+q85AfK8OIB16TbtbzStSR2BpaNKtQPAQsFjQ7XDjJKQ6+NP0hrq2JpRA8RJxO1tlPNGZn5aZtHG3+BIJ5hqbkMurYbSbjNW7eBA==", KEY);
        System.out.println("decrypt = " + URLDecoder.decode(decrypt, "utf-8"));
    }

    @Test
    public void shouldprod() throws Exception {
        AESBouncyCastle aesBouncyCastle = new AESBouncyCastle();
        String decrypt = aesBouncyCastle.decrypt("T3UtIakIzb+2VN2xVTBJUcbiraHuTzUu0Se27HZlMjkzvJP98UuQz9KF7I6BQhxG3UMdgcOPE5oWwbBPT8Xho+ialsydQaPn4uO2KyZ3ZDEoQ6SJCmB+Z69LAiqnUeo8slq6UQ9L/bcua4D92NSo8qR2LSATw6KolSHkl/xGQUc=", "DU+tJANGgSWeLnAlV3YzgwfukrXTOJf+lVPaa9z1Bas=");
//        String decrypt = aesBouncyCastle.decrypt("eBER+TFUyS38GCIhUEOrvcu/4rGNWBAbjwAZ3eUN/4aZIG5dyKh/8gBbvA4uIGdf", "hvtXqUPe5PTREMZFzPqZyZj6duMltYFT01pEej+Trxo=");
        System.out.println("decrypt = " + URLDecoder.decode(decrypt, "utf-8"));
    }

    @Test
    public void shouldEncrypt() throws Exception {
        AESBouncyCastle aesBouncyCastle = new AESBouncyCastle();

        String encrypted = URLDecoder.decode("H5qi4cGw6DDM0FeUkERcBZjSLQfFP%2bRVPl5Y8af00mVJSS5hWgaCLGZWbf6B9H6v%2fmwnDINZNWgqDAehfinmAMAA%2bWVaC98mg48mq27h3zJaVnAKi38DnXP0Sajx%2bddgSSvKPPgeojBYneXc%2fVBsLmjieIrMfsrqlRqP9U%2fkiFPErVn9bHMKvnsDDnaxETqWKzOejJyaJehKdDm65PKsLktf%2fPNNuh3eJL%2fThlIY0UE%3d",
                "utf-8");
        String plain = aesBouncyCastle.decrypt(encrypted, KEY);
        System.out.println("plain = " + plain);

//        String plain = "CALLBACK_URL=https://www.expediapartnercentral.com.lisqa7.sb.karmalab.net/&LANG_ID=1033&MSG_TIMESTAMP=2016-06-14T23:16:50.4538379Z&RP_ID=1";
        String encryptedBack = aesBouncyCastle.encrypt(plain, KEY);
        System.out.println("encrypted = " + encryptedBack);
        System.out.println("encrypted = " + URLEncoder.encode(encryptedBack, "utf-8"));

    }

    @Test
    public void shoulddecodeurl() throws UnsupportedEncodingException {
        String decode = URLDecoder.decode("T3UtIakIzb%2b2VN2xVTBJUcbiraHuTzUu0Se27HZlMjkzvJP98UuQz9KF7I6BQhxG3UMdgcOPE5oWwbBPT8Xho%2bialsydQaPn4uO2KyZ3ZDEoQ6SJCmB%2bZ69LAiqnUeo8jJxOMuSuqplybdhl71U8kZj23kQeUC9FmCdUtiz9Iiw%3d", "utf-8");
        System.out.println("decode = " + decode);
    }

    @Test
    public void shouldsdfsd() {
        String s = DateTime.now(UTC).toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS000"));
        System.out.println("s = " + s);
    }

    @Test
    public void shouldencrypt() throws Exception {
        DateTime now = DateTime.now(UTC).minusSeconds(5);
        String ts = URLEncoder.encode(now.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS0000Z")), "utf-8");
        System.out.println("ts = " + ts);
        AESBouncyCastle aesBouncyCastle = new AESBouncyCastle();
        String plain = "CALLBACK_URL=https%3a%2f%2fwww.expediapartnercentral.com.lisqa7.sb.karmalab.net%2f&LANG_ID=1033&MSG_TIMESTAMP=" + ts + "&RP_ID=1";
        System.out.println("URLDecoder.decode(plain = " + URLDecoder.decode(plain, "utf-8"));
        String encrypted = aesBouncyCastle.encrypt(plain, KEY);
        System.out.println("encrypted = " + URLEncoder.encode(encrypted, "utf-8"));
    }

    public static class AESBouncyCastle {

        public String decrypt(String enc, String key) throws Exception {

            byte[] bytesIn = Base64.getDecoder().decode(enc.getBytes("UTF-8"));

            byte[] ivBytes = Base64.getDecoder().decode("4bN8UHmJGfjhcFX6eSIPLw==");
            CipherParameters params = new ParametersWithIV(new KeyParameter(Base64.getDecoder().decode(key)), ivBytes);
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());

            // Decrypt all bytes that follow the IV
            cipher.init(false, params); // first param = encode/decode

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
                throw new GeneralSecurityException("encryption failed");
            }
            return bytesOut;
        }

    }
}
