package io.github.xinyangpan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/core")
public class CoreController {

	private volatile long cacheTimestamp = System.currentTimeMillis();

    @ApiOperation(value="获取当前服务端版本号", notes="")
	@GetMapping("/version")
	public String version() {
		return "2.0.1";
	}

    @ApiOperation(value="获取Cache刷新时间", notes="")
	@GetMapping("/cacheTimestamp")
	public long cacheTimestamp(boolean renew) {
		if (renew) {
			cacheTimestamp = System.currentTimeMillis();
		}
		return cacheTimestamp;
	}

}
