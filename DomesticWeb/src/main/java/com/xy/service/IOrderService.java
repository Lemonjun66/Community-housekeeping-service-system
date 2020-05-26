package com.xy.service;

import java.util.List;

import com.xy.entity.Order;

public interface IOrderService {
	
	public void save(Order order);
	
	public List<Order> getOrderByPeopleAndStatus(String people, String status, int currentPage, int pageSize);
	
	public int getOrderCountByPeopleAndStatus(String people, String status);
	
	public List<Order> getOrderByCompanyAndStatus(String company, String status, int currentPage, int pageSize);
	
	public int getOrderCountByCompanyAndStatus(String company, String status);
	
	public Order getOrderById(int id);
	
	public List<Order> getOrdersByCompany(String name, String status);
	
	public void saveById(Order order);
	
	public List<Order> getAll(int currentPage, int pageSize);
	
	public int getAllCount();
	
	public void delete(int id);

}
