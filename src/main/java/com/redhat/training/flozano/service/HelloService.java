package com.redhat.training.flozano.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@ProxyGen
public interface HelloService {

	public void hello(String host, String name, Handler<AsyncResult<String>> resultHandler);
}
