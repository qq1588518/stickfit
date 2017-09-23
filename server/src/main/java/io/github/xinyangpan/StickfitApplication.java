package io.github.xinyangpan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

@SpringBootApplication
@EnableTransactionManagement
public class StickfitApplication {

	@Bean
//	@ConditionalOnMissingBean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Lists.newArrayList(new MappingJackson2HttpMessageConverter()));
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(StickfitApplication.class, args);
	}

}
