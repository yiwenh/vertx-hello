package com.redhat.training.flozano;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ProxyHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.training.flozano.service.HelloService;
import com.redhat.training.flozano.service.HelloServiceImpl;
import com.redhat.training.flozano.service.HelloServiceVertxEBProxy;

public class MainVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    private HelloService ebProxy;
    
    @Override
    public void stop() {
        LOG.info("Shutting down Hello service...");
    }

    @Override
    public void start(Future<Void> future) {

        LOG.info("Starting Hello service...");
        
        registerHelloService();

        Router router = Router.router(vertx);
        router.get("/api/hello/:name")
            .produces("application/text")
            .handler(this::hello);

        vertx.createHttpServer()
        .requestHandler(router::accept)
            .listen(8080, result -> {
                if (result.succeeded()) {
                    future.complete();
                }
                else {
                    future.fail(result.cause());
                }
            });
    }
    
    final static String ADDRESS = "hello-service";
    
    private void registerHelloService() {
        LOG.info("Registering Hello service to the event bus...");
        
        HelloService serviceImpl = new HelloServiceImpl();
        ProxyHelper.registerService(HelloService.class, vertx, serviceImpl,
            ADDRESS);
        ebProxy = new HelloServiceVertxEBProxy(vertx, ADDRESS);
    }
    
    private void hello(RoutingContext rc) {
    	String name = rc.request().getParam("name");
        String host = rc.request().host();
        
        LOG.info("Got API request for name = '" + name + "' ...");
        
        ebProxy.hello(host, name, ar -> {
        	rc.response().end(ar.result());
        });
    }

}
