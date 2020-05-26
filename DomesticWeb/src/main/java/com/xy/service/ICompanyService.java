package com.xy.service;

import java.util.List;

import com.xy.entity.Company;

public interface ICompanyService {
	
	public Company getCompanyByName(String name);
	
	public void save(Company company);
	
	public List<Company> getCompanyByStatus(String serviceType, String status, int currentPage, int pageSize);
	
	public int getCompanyCountByStatus(String status);
	
	public void saveById(Company company);
	
	public List<Company> getAll(int currentPage, int pageSize);
	
	public int getAllCount();
	
	public void delete(int id);
	
	public Company getCompanyById(int id);

}
