package com.redhat.training.flozano.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class HelloServiceImpl implements HelloService {

	@Override
	public void hello(String host, String name, Handler<AsyncResult<String>> resultHandler) {
		String msg = "Hello demo " + name +  ", from '" + host + "'.\n";
		resultHandler.handle(Future.succeededFuture(msg));
	}
}
