package com.ft.store;

import com.ft.store.domain.Product;
import com.ft.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.ft.store.domain")
public class StoreApplication {

	@Autowired
	private ProductRepository productRepository;

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			productRepository.save(new Product("Laptop", 1200.00));
			productRepository.save(new Product("Phone", 200.00));
			productRepository.save(new Product("Mouse", 50.00));
			productRepository.save(new Product("Keyboard", 100.00));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

}
