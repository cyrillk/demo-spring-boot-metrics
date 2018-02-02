package com.example.demo.controllers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class OtherControllerTest {

    private final HelloController helloController = new HelloController();
    private WireMockServer wireMockServer;

    @Before
    public void before() {
        wireMockServer = new WireMockServer(9999);
        wireMockServer.start();
    }

    @After
    public void after() {
        if (wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    @Test
    public void testThisThing() {
        final String expected = "this thing";
        wireMockServer.givenThat(get("/remote").willReturn(ok(expected)));
        StepVerifier.create(helloController.handle()).expectNext(expected).expectComplete().log().verify();
    }

    @Ignore
    public void testThisThing2() {
        wireMockServer.givenThat(get("/remote").willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
        StepVerifier.create(helloController.handle()).expectError().log().verify();
    }

    @Test
    public void testThatThing() {
        final String expected = "that thing";
        wireMockServer.givenThat(get("/remote").willReturn(ok(expected)));
        StepVerifier.create(helloController.handle()).expectNext(expected).expectComplete().log().verify();
    }
}