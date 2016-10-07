package org.js;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Spike {

    private static final String XML = "<ns5:RevalidateUserLoginRequest\n" +
            "        xmlns:ns1=\"urn:expedia:e3:data:basetypes:defn:v4\" xmlns:ns2=\"urn:expedia:e3:data:messagetypes:defn:v4\"\n" +
            "        xmlns:ns3=\"urn:expedia:e3:data:timetypes:defn:v4\" xmlns:ns4=\"urn:expedia:e3:ews:core:messages:common:defn:v1\"\n" +
            "        xmlns:ns5=\"urn:expedia:e3:es:userservice:messages:v1\">\n" +
            "    <ns2:MessageInfo\n" +
            "            ns2:MessageNameString=\"expedia.e3.es.userservice.messages.v1.RevalidateUserLoginRequestType\"\n" +
            "            ns2:MessageVersion=\"v1\" ns2:MessageGUID=\"79ada49f-65c6-4699-b64c-efb26751bd3b\" ns3:CreateDateTime=\"2015-07-07T15:20:20\"/>\n" +
            "    <ns4:PointOfSale>\n" +
            "        <ns1:SiteID>1002</ns1:SiteID>\n" +
            "    </ns4:PointOfSale>\n" +
            "    <ns5:TravelerGUID>cb658003-73f6-4337-a1ac-168317481cd9</ns5:TravelerGUID>\n" +
            "    <ns5:UserToken>\n" +
            "        <ns5:TokenVersion>8</ns5:TokenVersion>\n" +
            "        <ns5:TokenValue>${userToken}</ns5:TokenValue>\n" +
            "    </ns5:UserToken>\n" +
            "    <ns5:AccountTypeToken>\n" +
            "        <ns5:TokenVersion>2</ns5:TokenVersion>\n" +
            "        <ns5:TokenValue>3,1,EX01EEA9E312$8B$3AyQ$C5E$0AK$D6lYT$B4$3Ci$E2V$FB$18$B5$0F$5Bl$1F$EB4$39$96</ns5:TokenValue>\n" +
            "    </ns5:AccountTypeToken>\n" +
            "    <ns5:MemberInfoToken>\n" +
            "        <ns5:TokenVersion>5</ns5:TokenVersion>\n" +
            "        <ns5:TokenValue>${memberInfoToken}</ns5:TokenValue>\n" +
            "    </ns5:MemberInfoToken>\n" +
            "    <ns5:SecurityLevel>Base</ns5:SecurityLevel>\n" +
            "</ns5:RevalidateUserLoginRequest>";

    private Optional<String> getRequestTypeSax(InputStream stream) throws ParserConfigurationException, SAXException, IOException {


        try (AutoCloseableXmlStreamReader xml = new AutoCloseableXmlStreamReader(stream)) {

            xml.nextTag();
            return Optional.of(xml.getLocalName());
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static void main(String[] args) throws Exception {

        Spike spike = new Spike();
        Optional<String> requestTypeSax = spike.getRequestTypeSax(new ByteArrayInputStream(XML.getBytes()));

        System.out.println("requestTypeSax = " + requestTypeSax);


    }
}
