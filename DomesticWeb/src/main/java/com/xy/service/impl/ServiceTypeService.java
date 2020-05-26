package com.xy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xy.entity.ServiceType;
import com.xy.mapper.ServiceTypeMapper;
import com.xy.service.IServiceTypeService;
import com.xy.utils.PageBean;

@Service
public class ServiceTypeService implements IServiceTypeService {
	
	@Autowired
	private ServiceTypeMapper sMapper;
	
	//获取所有的服务类型
	@Override
	public List<ServiceType> getAll() {
		//
		return this.sMapper.getAll();
	}

	@Override
	public List<ServiceType> getAll(int currentPage, int pageSize) {
		List<ServiceType> allItems = null;
		int countNums = 0;
		 //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        allItems = this.sMapper.getAll();        //全部商品
        countNums = this.sMapper.getAllCount();          //总记录数
        PageBean<ServiceType> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(allItems);
        return pageData.getItems();
	}

	//获得服务类型总数
	@Override
	public int getAllCount() {
		return this.sMapper.getAllCount();
	}

	@Override
	public void delete(int id) {
		this.sMapper.delete(id);
	}

	//从ServiceTypeCtl.java来，保存新建的服务类型，去mapper包的ServiceTypeMapper.java找save
	@Override
	public void save(ServiceType serviceType) {
		this.sMapper.save(serviceType);
	}
	
	@Override
	public ServiceType getServiceTypeById(int id) {
		return this.sMapper.getServiceTypeById(id);
	}

}
