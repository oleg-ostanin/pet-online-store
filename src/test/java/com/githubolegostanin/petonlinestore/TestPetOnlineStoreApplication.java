package com.githubolegostanin.petonlinestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPetOnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(PetOnlineStoreApplication::main).with(TestPetOnlineStoreApplication.class).run(args);
	}

}
