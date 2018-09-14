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
import io.vertx.serviceproxy.ProxyHelper;

@RunWith(VertxUnitRunner.class)
public class HelloServiceEBProxyTest {

    private Vertx vertx;

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    final static String ADDRESS = "hello-service";

    @Test(timeout=3000)
    public void helloProxyTest(TestContext testContext) {
        final Async async = testContext.async();
      
        HelloService serviceImpl = new HelloServiceImpl();        
  	    ProxyHelper.registerService(HelloService.class, vertx, serviceImpl, ADDRESS);
  	    HelloService proxy = new HelloServiceVertxEBProxy(vertx, ADDRESS);
  	
        final String host = "test.example.com";
        final String name = "Test";
        proxy.hello(host, name, ar -> {
      	    String msg = ar.result();
      	    testContext.assertTrue(msg.contains("Hello"));
            testContext.assertTrue(msg.contains(name));
      	    testContext.assertTrue(msg.contains(host));
            async.complete();
        });
    }

}
