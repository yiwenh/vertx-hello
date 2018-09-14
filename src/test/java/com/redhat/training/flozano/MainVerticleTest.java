package com.redhat.training.flozano;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.flozano.MainVerticle;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class MainVerticleTest {

    private Vertx vertx;

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();

        vertx.deployVerticle(MainVerticle.class.getName(), testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test
    public void ciaoTest(TestContext testContext) {
        final Async async = testContext.async();

        vertx.createHttpClient()
                .getNow(8080, "localhost", "/api/hello/Developer", response -> {
                    response.handler(responseBody -> {
                    	String body = responseBody.toString();
                        testContext.assertTrue(body.contains("Hello"));
                        testContext.assertTrue(body.contains("Developer"));
                        async.complete();
                    });
                });
    }

}
