package com.example;

import com.example.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class SpringbootApplication implements CommandLineRunner {

	@Value("${netty.port}")
	private int port;

	@Value("${netty.url}")
	private String url;

	@Autowired
	private NettyServer server;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		InetSocketAddress address = new InetSocketAddress(url,port);
		server.start(address);
	}
}

