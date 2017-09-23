package io.github.xinyangpan;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
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
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaList = Lists.newArrayList(converter.getSupportedMediaTypes());
		mediaList.add(MediaType.TEXT_PLAIN);
		converter.setSupportedMediaTypes(mediaList);
		// 
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Lists.newArrayList(converter));
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(StickfitApplication.class, args);
	}

}
