package io.github.xinyangpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.xinyangpan.persistent.po.GroupPo;
import io.github.xinyangpan.service.GroupService;
import io.github.xinyangpan.vo.GroupVo;

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

	@GetMapping("/create")
	public void create(GroupPo groupPo) {
		groupService.create(groupPo);
	}

	@GetMapping("/get")
	public GroupVo get(long groupId) {
		return groupService.get(groupId);
	}

}
