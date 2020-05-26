package com.xy.ctl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.entity.ServiceType;
import com.xy.service.IServiceTypeService;

@Controller
@RequestMapping("/serviceType/")
public class ServiceTypeCtl {
	
	@Autowired
	private IServiceTypeService serviceTypeService;
	
	//获取所有的服务类型
	@RequestMapping("/getAll")
	@ResponseBody
	public List<ServiceType> getAll() {
		//com.xy.service.impl ServiceTypeService.java getAll
		//返回所有服务类型List集合List<ServiceType>
		return this.serviceTypeService.getAll();
	}
	
	//从ServiceType.js的serviceType方法过来
	@RequestMapping("/getAllFY")
	@ResponseBody
	public List<ServiceType> getAll(HttpServletRequest request) {
		int currentPage = 0;
		int pageSize = 0;
		if(!request.getParameter("currentPage").equals("")) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if(!request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//去取那一页的所有服务类型字段
		return this.serviceTypeService.getAll(currentPage, pageSize);
	}
	
	//获得服务类型总数
	@RequestMapping("/getAllCount")
	@ResponseBody
	public int getAllCount(HttpServletRequest request) {
		int count = this.serviceTypeService.getAllCount();
		return count;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpSession session) {
		int id = Integer.parseInt(request.getParameter("id"));
		ServiceType serviceType = this.serviceTypeService.getServiceTypeById(id);
		session.setAttribute("aService", serviceType.getName());
		this.serviceTypeService.delete(id);
		return "true";
	}
	
	//添加服务类型，从ServiceType.html来
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpSession session) {
		String a = (String) session.getAttribute("aService");
		//从name="theService"的input取value赋值给左边name
		String name = request.getParameter("theService");
		//判断名字不为空就进入
		if(!name.equals("") && !name.equals(a)) {
			//新建ServiceType实体
			ServiceType serviceType = new ServiceType();
			//把服务类型名字赋值给实体
			serviceType.setName(name);
			//保存实体到服务类型表，去service.impl包ServiceTypeService.java下找save
			this.serviceTypeService.save(serviceType);
		}
		//回到serviceType.html页面
		return "manager/serviceType";
	}
}
