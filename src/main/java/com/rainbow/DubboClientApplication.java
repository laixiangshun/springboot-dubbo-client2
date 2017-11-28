package com.rainbow;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Dubbo泛化调用:泛化调用，最最直接的表现就是服务消费者不需要有任何接口的实现，就能完成服务的调用
 */
@SpringBootApplication
public class DubboClientApplication {

	public static void main(String[] args) {

		//普通编码配置方式
		ApplicationConfig application=new ApplicationConfig();
		application.setName("dubbo-consumer2");

		//连接注册中心配置
		RegistryConfig registryConfig=new RegistryConfig();
		registryConfig.setAddress("zookeeper://localhost:2181");

		//订阅服务配置
		ReferenceConfig<GenericService> reference=new ReferenceConfig<GenericService>();
		reference.setApplication(application);
		reference.setRegistry(registryConfig);
		reference.setInterface("com.rainbow.service.TestService");
		reference.setGeneric(true);//声明为泛化接口

		ReferenceConfigCache cache=ReferenceConfigCache.getCache();
		GenericService genericService=cache.get(reference);

		//第一个参数是你调用远程接口的具体方法名，第二个参数是这个方法的入参的类型，最后一个参数是值
		Object result=genericService.$invoke("sayHello",new String[]{"java.lang.String"},new Object[]{"hello no spring"});
		System.out.println(result);

//		SpringApplication.run(DubboClientApplication.class, args);

	}
}
