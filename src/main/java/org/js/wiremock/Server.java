package org.js.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Server {

    private WireMockServer wiremockServer;


    public Server() {
        wiremockServer = new WireMockServer(8084);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private void start() {
        wiremockServer.start();
        wiremockServer.stubFor(
                get(urlPathEqualTo("/jstest2/foo.html"))
                .willReturn(aResponse().withBody("Hello World js2").withFixedDelay(500))
        );
    }
}
