package io.github.xinyangpan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.service.CoreService;
import io.github.xinyangpan.service.CustomerService;
import io.github.xinyangpan.vo.Session;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CoreService coreService;
	@Autowired
	private CustomerService customerService;

	@GetMapping("/login")
	public CustomerPo login(String code) {
		String appId = "wx7697e61e4407f57a";
		String secret = "672fa10aea63e42a39a683c65267dca2";
		Session session = coreService.jscode2session(appId, secret, code);
		log.info("jscode2session: {}", session);
		return customerService.login(session.getOpenId());
	}

	@GetMapping("/update")
	public CustomerPo update(String openId, String username) {
		return customerService.update(openId, username);
	}

}
