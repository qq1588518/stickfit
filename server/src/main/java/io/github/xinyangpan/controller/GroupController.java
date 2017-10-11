package io.github.xinyangpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.xinyangpan.service.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@GetMapping("/dismiss")
	public void dismiss(long customerId, long groupId) {
		groupService.dismiss(customerId, groupId);
	}

	@GetMapping("/leave")
	public void leave(long customerId, long groupId) {
		groupService.leave(customerId, groupId);
	}

	@GetMapping("/transfer")
	public void transfer(long from, long to, long groupId) {
		groupService.transfer(from, to, groupId);
	}

	@GetMapping("/join")
	public void join(long customerId, long groupId, String password) {
		groupService.join(customerId, groupId, password);
	}

}
