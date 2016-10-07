package org.js.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Server2 {

    private WireMockServer wiremockServer;


    public Server2() {
        wiremockServer = new WireMockServer(1102);
    }

    public static void main(String[] args) {
        Server2 server = new Server2();
        server.start();
    }

    private void start() {
        wiremockServer.start();
        wiremockServer.stubFor(
                post(urlPathMatching("/v2/revalidateUserLogin"))
                .willReturn(aResponse().withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<RevalidateUserLoginResponse xmlns=\"urn:expedia:e3:es:userservice:messages:v1\"\n" +
                        "                             xmlns:ns2=\"urn:expedia:e3:ews:core:messages:common:defn:v1\"\n" +
                        "                             xmlns:ns3=\"urn:expedia:e3:data:basetypes:defn:v4\"\n" +
                        "                             xmlns:ns4=\"urn:expedia:e3:data:messagetypes:defn:v4\"\n" +
                        "                             xmlns:ns5=\"urn:expedia:e3:data:timetypes:defn:v4\"\n" +
                        "                             xmlns:ns6=\"urn:expedia:e3:data:placetypes:defn:v4\"\n" +
                        "                             xmlns:ns7=\"urn:expedia:payment:messages:v1\"\n" +
                        "                             xmlns:ns8=\"urn:expedia:e3:data:basetypes:defn:v3\">\n" +
                        "    <LoginResponseStatus>\n" +
                        "        <ns2:StatusCodeCategory>Success</ns2:StatusCodeCategory>\n" +
                        "        <ns2:StatusCodeNamespace>com.expedia.lux.webservice.lodgingusersvc.handlers.RevalidateUserLoginHandler\n" +
                        "        </ns2:StatusCodeNamespace>\n" +
                        "        <ns2:StatusCode>Success</ns2:StatusCode>\n" +
                        "    </LoginResponseStatus>\n" +
                        "    <UserCredentials>\n" +
                        "        <LoggedInUserID>\n" +
                        "            <ns3:UserID>33</ns3:UserID>\n" +
                        "            <ns3:SiteKey>\n" +
                        "                <ns3:SiteID>1002</ns3:SiteID>\n" +
                        "                <ns3:ReportingID>0</ns3:ReportingID>\n" +
                        "            </ns3:SiteKey>\n" +
                        "            <ns3:LegacySiteKey>\n" +
                        "                <ns3:TPID>20001</ns3:TPID>\n" +
                        "                <ns3:EAPID>0</ns3:EAPID>\n" +
                        "            </ns3:LegacySiteKey>\n" +
                        "        </LoggedInUserID>\n" +
                        "        <ActAsUserID>\n" +
                        "            <ns3:UserID>33</ns3:UserID>\n" +
                        "            <ns3:SiteKey>\n" +
                        "                <ns3:SiteID>1002</ns3:SiteID>\n" +
                        "                <ns3:ReportingID>0</ns3:ReportingID>\n" +
                        "            </ns3:SiteKey>\n" +
                        "            <ns3:LegacySiteKey>\n" +
                        "                <ns3:TPID>20001</ns3:TPID>\n" +
                        "                <ns3:EAPID>0</ns3:EAPID>\n" +
                        "            </ns3:LegacySiteKey>\n" +
                        "        </ActAsUserID>\n" +
                        "    </UserCredentials>\n" +
                        "    <AccountTypeToken>\n" +
                        "        <TokenVersion>2</TokenVersion>\n" +
                        "        <TokenValue>3,1,EX0146E7D7B7k$0B$F0$3A$CD$1E$E4k6$17$DA$3A$BFf$27$DE$F7$8B$E5$2C$2EHD$0F$D7$2B$98k$B0\n" +
                        "        </TokenValue>\n" +
                        "    </AccountTypeToken>\n" +
                        "    <UserToken>\n" +
                        "        <TokenVersion>8</TokenVersion>\n" +
                        "        <TokenValue>0,EX016E2BA4CC10001000$8Cq08$21000$21000$21000$21O001000$7F31!90$86$85jO$0B$8BM$29!i02000\n" +
                        "        </TokenValue>\n" +
                        "    </UserToken>\n" +
                        "    <CookieInfo>\n" +
                        "        <LoggedInUserId>33</LoggedInUserId>\n" +
                        "        <ActAsUserId>33</ActAsUserId>\n" +
                        "        <emulationType>0</emulationType>\n" +
                        "        <authenticationMode>3</authenticationMode>\n" +
                        "        <pgmID>0</pgmID>\n" +
                        "        <miut>0</miut>\n" +
                        "        <idSession>1</idSession>\n" +
                        "        <reqs>1</reqs>\n" +
                        "        <sessionFlags>1</sessionFlags>\n" +
                        "        <securityType>1</securityType>\n" +
                        "        <browserID>0</browserID>\n" +
                        "    </CookieInfo>\n" +
                        "</RevalidateUserLoginResponse>").withFixedDelay(1000))
        );

    }
}
