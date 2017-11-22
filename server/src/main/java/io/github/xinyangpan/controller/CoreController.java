package io.github.xinyangpan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
public class CoreController {

	private volatile long cacheTimestamp = System.currentTimeMillis();

	@GetMapping("/version")
	public String version() {
		return "2.0.0";
	}

	@GetMapping("/cacheTimestamp")
	public long cacheTimestamp(boolean renew) {
		if (renew) {
			cacheTimestamp = System.currentTimeMillis();
		}
		return cacheTimestamp;
	}

}
