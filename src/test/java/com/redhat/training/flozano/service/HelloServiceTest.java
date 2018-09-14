package com.redhat.training.flozano.service;

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
public class HelloServiceTest {

    private Vertx vertx;

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test(timeout=3000)
    public void helloImplTest(TestContext testContext) {
        final Async async = testContext.async();
        
        HelloService service = new HelloServiceImpl();
        
        final String host = "test.example.com";
        final String name = "Test";
        service.hello(host, name, ar -> {
        	String msg = ar.result();
        	testContext.assertTrue(msg.contains("Hello"));
        	testContext.assertTrue(msg.contains(name));
        	testContext.assertTrue(msg.contains(host));
        	async.complete();
        });
    }

}
