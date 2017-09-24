package io.github.xinyangpan;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

import io.github.xinyangpan.persistent.dao.ExerciseTypeDao;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;

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
		ConfigurableApplicationContext context = SpringApplication.run(StickfitApplication.class, args);
		ExerciseTypeDao exerciseTypeDao = context.getBean(ExerciseTypeDao.class);
		exerciseTypeDao.save(new ExerciseTypePo("跑步", "跑步", "公里", new BigDecimal(6), 1));
		exerciseTypeDao.save(new ExerciseTypePo("走步", "走步", "步", new BigDecimal(10000), 2));
		exerciseTypeDao.save(new ExerciseTypePo("游泳", "游泳", "米", new BigDecimal(1000), 3));
		exerciseTypeDao.save(new ExerciseTypePo("骑车", "骑车", "公里", new BigDecimal(12), 4));
		exerciseTypeDao.save(new ExerciseTypePo("其他", "其他运动", "分钟", new BigDecimal(45), 99));
	}

}
