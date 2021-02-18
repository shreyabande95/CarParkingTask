package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import spring.dto.Car;

@SpringBootApplication
@Component
public class Application {

	@Bean
	JedisConnectionFactory jedisConnection(){
		return new JedisConnectionFactory();
	}

	@Bean
	RedisTemplate<String, Car> redisTemplate(){
		RedisTemplate<String, Car> redisTemplate=new RedisTemplate<String, Car>();
		redisTemplate.setConnectionFactory(jedisConnection());
		return redisTemplate;
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
