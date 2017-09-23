package io.github.xinyangpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("/login")
	public CustomerPo login(String openId, String username) {
		return customerService.login(openId, username);
	}

}
