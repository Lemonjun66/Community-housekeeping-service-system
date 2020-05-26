package com.xy.service;

import com.xy.entity.Manager;

public interface IManagerService {

	public void addManager(Manager manager);
	
	public Manager getManagerByName(String name);
}
