package io.github.xinyangpan;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
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
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class StickfitApplication {

	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	@PostConstruct
	public void init() {
		repositoryRestConfiguration.exposeIdsFor(CustomerPo.class, GroupPo.class, ExercisePo.class, ExerciseTypePo.class);
	}

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.github.xinyangpan"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("下一马打卡App")
                .description("下一马打卡App后台程序")
                .termsOfServiceUrl("https://www.nextmarathon.cn/")
                .contact(new Contact("Pan Xinyang", "", "seanpan2008@gmail.com"))
                .version("2.1")
                .build();
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
