package io.github.xinyangpan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class StickfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(StickfitApplication.class, args);
	}

}
