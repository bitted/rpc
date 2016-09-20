package com.xxx.rpc.sample.server;

import com.xxx.rpc.sample.api.HelloService;
import com.xxx.rpc.sample.api.Person;
import com.xxx.rpc.server.RpcService;

@RpcService(value = HelloService.class, version = "sample.hello3")
public class HelloServiceImpl3 implements HelloService {

    @Override
    public String hello(String name) {
        return "你好Hello! " + name;
    }

    @Override
    public String hello(Person person) {
        return "你好Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
