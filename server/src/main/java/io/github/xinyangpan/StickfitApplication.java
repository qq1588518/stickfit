package io.github.xinyangpan;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;
import io.github.xinyangpan.persistent.po.GroupPo;

@SpringBootApplication
@EnableTransactionManagement
public class StickfitApplication {

	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	@PostConstruct
	public void init() {
		repositoryRestConfiguration.exposeIdsFor(CustomerPo.class, GroupPo.class, ExercisePo.class, ExerciseTypePo.class);
	}

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
