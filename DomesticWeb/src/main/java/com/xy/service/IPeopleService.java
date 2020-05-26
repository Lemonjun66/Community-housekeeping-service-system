package com.xy.service;

import java.util.List;

import com.xy.entity.People;

public interface IPeopleService {
	
	public People getPeopleByName(String name);
	
	public void save(People people);
	
	public List<People> getAll(int currentPage, int pageSize);
	
	public int getAllCount();
	
	public void delete(int id);

}
