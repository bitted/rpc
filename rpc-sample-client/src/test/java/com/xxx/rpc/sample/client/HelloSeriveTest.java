package com.xxx.rpc.sample.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xxx.rpc.client.RpcProxy;
import com.xxx.rpc.sample.api.HelloService;
import com.xxx.rpc.sample.api.Person;

/**
 * 类名称：HelloSeriveTest
 * 类描述：(添加hello服务的test用例)
 *
 * 创建人：feijian8
 * 创建时间：2016年9月20日 下午5:19:16
 * 修改人：
 * 修改时间：2016年9月20日 下午5:19:16
 * 修改备注：
 * 
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class HelloSeriveTest {

	@Autowired
	private RpcProxy rpcProxy;

	@Test
	public void hello() {
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello("World");
		System.out.println("hello-> " + result);
	}

	@Test
	public void hello2() {
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello(new Person("Yong", "Huang"));
		System.out.println("hello2-> " + result);
	}

	@Test
	public void hello3() {
		int loopCount = 100;

		long start = System.currentTimeMillis();

		HelloService helloService = rpcProxy.create(HelloService.class);
		for (int i = 0; i < loopCount; i++) {
			String result = helloService.hello("World");
			System.out.println(result);
		}

		long time = System.currentTimeMillis() - start;
		System.out.println("loop: " + loopCount);
		System.out.println("time: " + time + "ms");
		System.out.println("tps: " + (double) loopCount / ((double) time / 1000));

	}

	@Test
	public void hello4() {
		int threadNum = 10;
		int loopCount = 100;

		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		final CountDownLatch latch = new CountDownLatch(loopCount);

		try {
			long start = System.currentTimeMillis();

			for (int i = 0; i < loopCount; i++) {
				executor.submit(new Runnable() {
					@Override
					public void run() {
						HelloService helloService = rpcProxy.create(HelloService.class);
						String result = helloService.hello("World");
						System.out.println(result);
						latch.countDown();
					}
				});
			}
			// latch.await();

			long time = System.currentTimeMillis() - start;
			System.out.println("thread: " + threadNum);
			System.out.println("loop: " + loopCount);
			System.out.println("time: " + time + "ms");
			System.out.println("tps: " + (double) loopCount / ((double) time / 1000));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}
