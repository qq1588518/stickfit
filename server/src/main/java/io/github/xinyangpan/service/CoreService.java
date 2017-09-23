package io.github.xinyangpan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.xinyangpan.vo.Session;

@Service
public class CoreService {
	@Autowired
	private RestTemplate restTemplate;
	
	public Session jscode2session(String appId, String secret, String code) {
		String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appId, secret, code);
		return restTemplate.getForObject(url, Session.class);
	}

}
