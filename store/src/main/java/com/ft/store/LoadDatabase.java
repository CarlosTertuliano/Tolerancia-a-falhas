package com.ft.store;

import com.ft.store.domain.Product;
import com.ft.store.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            repository.save(new Product() {{
                setName("Laptop");
                setValue(1200.00);
            }});
            repository.save(new Product() {{
                setName("Phone");
                setValue(200.00);
            }});
            repository.save(new Product() {{
                setName("Mouse");
                setValue(200.00);
            }});
            repository.save(new Product() {{
                setName("Keyboard");
                setValue(300.00);
            }});
            repository.save(new Product() {{
                setName("Processor");
                setValue(500.00);
            }});
            repository.save(new Product() {{
                setName("Mouse Pad");
                setValue(100.00);
            }});
            repository.save(new Product() {{
                setName("Monitor");
                setValue(1000.00);
            }});
        };
    }
}
