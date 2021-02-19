package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class SpringwithesApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringwithesApplication.class, args);
	}
}
