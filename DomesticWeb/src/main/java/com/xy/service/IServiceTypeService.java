package com.xy.service;

import java.util.List;

import com.xy.entity.ServiceType;

public interface IServiceTypeService {
	
	public List<ServiceType> getAll();
	
	public List<ServiceType> getAll(int currentPage, int pageSize);
	
	public int getAllCount();
	
	public void delete(int id);
	
	public void save(ServiceType serviceType);
	
	public ServiceType getServiceTypeById(int id);

}
