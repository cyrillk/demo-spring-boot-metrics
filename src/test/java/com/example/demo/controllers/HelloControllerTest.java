package com.example.demo.controllers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest {

    private WireMockServer wireMockServer;

    @Autowired
    private HelloController helloController;

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

    @Test
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