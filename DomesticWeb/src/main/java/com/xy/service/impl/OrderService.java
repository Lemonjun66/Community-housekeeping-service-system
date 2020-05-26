package com.xy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xy.entity.Order;
import com.xy.mapper.OrderMapper;
import com.xy.service.IOrderService;
import com.xy.utils.PageBean;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderMapper oMapper;
	
	@Override
	public void save(Order order) {
		this.oMapper.save(order);
	}

	//取分页的订单集合
	@Override
	public List<Order> getOrderByPeopleAndStatus(String people, String status, int currentPage, int pageSize) {
		List<Order> allItems = null;
		int countNums = 0;
		 //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        allItems = this.oMapper.getOrderByPeopleAndStatus(people, status);        //全部商品
        countNums = this.oMapper.getOrderCountByPeopleAndStatus(people, status);          //总记录数
        PageBean<Order> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(allItems);
        return pageData.getItems();
	}

	@Override
	public int getOrderCountByPeopleAndStatus(String people, String status) {
		return this.oMapper.getOrderCountByPeopleAndStatus(people, status);
	}
	
	@Override
	public Order getOrderById(int id) {
		return this.oMapper.getOrderById(id);
	}

	@Override
	public List<Order> getOrdersByCompany(String name, String status) {
		return this.oMapper.getOrdersByCompany(name, status);
	}

	@Override
	public void saveById(Order order) {
		this.oMapper.saveById(order);
	}

	@Override
	public List<Order> getAll(int currentPage, int pageSize) {
		List<Order> allItems = null;
		int countNums = 0;
		 //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        allItems = this.oMapper.getAll();        //全部商品
        countNums = this.oMapper.getAllCount();          //总记录数
        PageBean<Order> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(allItems);
        return pageData.getItems();
	}

	@Override
	public int getAllCount() {
		return this.oMapper.getAllCount();
	}

	@Override
	public void delete(int id) {
		this.oMapper.delete(id);
	}

	@Override
	public List<Order> getOrderByCompanyAndStatus(String company, String status, int currentPage, int pageSize) {
		List<Order> allItems = null;
		int countNums = 0;
		 //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        allItems = this.oMapper.getOrderByCompanyAndStatus(company, status);        //全部商品
        countNums = this.oMapper.getOrderCountByCompanyAndStatus(company, status);          //总记录数
        PageBean<Order> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(allItems);
        return pageData.getItems();
	}

	@Override
	public int getOrderCountByCompanyAndStatus(String company, String status) {
		return this.oMapper.getOrderCountByCompanyAndStatus(company, status);
	}

}
