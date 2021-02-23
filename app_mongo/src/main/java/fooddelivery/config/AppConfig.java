package fooddelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import fooddelivery.OrderRepositoryListener;

@Configuration
@EnableMongoAuditing
public class AppConfig {
	@Bean
    public OrderRepositoryListener orderRepositoryListener() {
        return new OrderRepositoryListener();
    }

}
