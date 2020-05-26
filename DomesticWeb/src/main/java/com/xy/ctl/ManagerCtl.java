package com.xy.ctl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xy.service.IManagerService;

import com.xy.entity.Manager;

@RestController
@RequestMapping("/manager/")
public class ManagerCtl {

    @Autowired
    private IManagerService managerService;

	@RequestMapping("add")
	public String addManager() {
    	Manager manager = new Manager();
    	manager.setName("admin");
    	manager.setPwd("12345678");
    	this.managerService.addManager(manager);
    	return "success";
	}
	
	//从manager.js过来,获取当前登录的管理员名
	@RequestMapping("/getManagerUsername")
	@ResponseBody
	//？建立1个缓存容器，取键名为"Musername"的值赋给username
	//在LoginCtl.java的登录方法那里定义了Musername，值为登录的用户名
	public String getUserName(HttpSession session) {
		String username = (String) session.getAttribute("Musername");
		//把username返回给manager.js中的data
		return username;
	}
}
