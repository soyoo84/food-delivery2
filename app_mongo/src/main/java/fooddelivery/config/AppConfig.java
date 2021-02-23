package fooddelivery.config;

import javax.annotation.PostConstruct;

import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import fooddelivery.OrderSaveListener;

@Configuration
@EnableMongoAuditing
public class AppConfig {
	
}
