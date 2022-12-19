package kr.jobtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringSpaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSpaProjectApplication.class, args);
	}

}
