package com.xy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xy.entity.Manager;
import com.xy.mapper.ManagerMapper;
import com.xy.service.IManagerService;

@Service
public class ManagerService implements IManagerService {
	
	@Resource
	private ManagerMapper managerMapper;

	@Override
	public void addManager(Manager manager) {
		this.managerMapper.addManager(manager);

	}

	//注册时用到，从LoginCtl.java过来，取和注册名相同的管理员名
	//去mapper包下ManagerMapper.java找managerMapper接口下的getManagerByName方法
	@Override
	public Manager getManagerByName(String name) {		
		return this.managerMapper.getManagerByName(name);
	}

}
