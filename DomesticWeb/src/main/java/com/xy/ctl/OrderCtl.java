package com.xy.ctl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.entity.Company;
import com.xy.entity.Order;
import com.xy.service.ICompanyService;
import com.xy.service.IOrderService;



@Controller
@RequestMapping("/order/")
public class OrderCtl {
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ICompanyService companyService;
	
	//下订单保存信息
	@RequestMapping("/saveInfo")
	public String saveInfo(HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("Pusername");
		Order order = new Order();
		String serviceType = request.getParameter("serviceType");
		String company = request.getParameter("company");
		String date = request.getParameter("date");
		String date1 = request.getParameter("date1");
		String time = request.getParameter("time");
		String time1 = request.getParameter("time1");
		String people = request.getParameter("people");
		String phone = request.getParameter("phone");
		String wechat = request.getParameter("wechat");
		String content = request.getParameter("content");
		order.setPeople(username);
		order.setCompany(company);
		//如果没选结束日期，就进if判断
		if(date1.equals("")) {
			order.setTime(date + " " + time);
		}else {
			order.setTime(date + " " + time + "至" + date1 + " " + time1);
		}
		order.setType(serviceType);
		order.setPhone(phone);
		order.setName(people);
		order.setWechat(wechat);
		order.setContent(content);
		order.setStatus("0");
		this.orderService.save(order);
		return "people/xdd";
	}
 
	
	//取分页的订单集合
	@RequestMapping("/getOrderByPeopleAndStatus")
	@ResponseBody
	public List<Order> getOrderByPeopleAndStatus(HttpSession session, HttpServletRequest request) {
		int currentPage = 0;
		int pageSize = 0;
		String status = "0";
		String people = (String) session.getAttribute("Pusername");
		if(!request.getParameter("currentPage").equals("")) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if(!request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if(!request.getParameter("status").equals("")) {
			status = request.getParameter("status");
		}
		return orderService.getOrderByPeopleAndStatus(people,status,currentPage, pageSize);
	}
	
	@RequestMapping("/getOrderCountByPeopleAndStatus")
	@ResponseBody
	public int getOrderCountByPeopleAndStatus(HttpSession session, HttpServletRequest request) {
		String status = "0";
		String people = (String) session.getAttribute("Pusername");
		if(!request.getParameter("status").equals("")) {
			status = request.getParameter("status");
		}
		int count = this.orderService.getOrderCountByPeopleAndStatus(people, status);
		return count;
	}
	
	//公司未完成订单页面过来
	@RequestMapping("/getOrderByCompanyAndStatus")
	@ResponseBody
	public List<Order> getOrderByCompanyAndStatus(HttpSession session, HttpServletRequest request) {
		int currentPage = 0;
		int pageSize = 0;
		String status = "0";
		String company = (String) session.getAttribute("Cusername");
		if(!request.getParameter("currentPage").equals("")) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if(!request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if(!request.getParameter("status").equals("")) {
			status = request.getParameter("status");
		}
		return orderService.getOrderByCompanyAndStatus(company,status,currentPage, pageSize);
	}
	
	@RequestMapping("/getOrderCountByCompanyAndStatus")
	@ResponseBody
	public int getOrderCountByCompanyAndStatus(HttpSession session, HttpServletRequest request) {
		String status = "0";
		String company = (String) session.getAttribute("Cusername");
		if(!request.getParameter("status").equals("")) {
			status = request.getParameter("status");
		}
		int count = this.orderService.getOrderCountByCompanyAndStatus(company, status);
		return count;
	}
	
	//给订单打分
	@RequestMapping("/score")
	public String score(HttpSession session, HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("demo"));
		int score = Integer.parseInt(request.getParameter("score"));
		Order order = this.orderService.getOrderById(id);
		order.setScore(score);
		order.setStatus("2");
		this.orderService.saveById(order);
		String companyName = order.getCompany();
		Company company = this.companyService.getCompanyByName(companyName);
		//getOrdersByCompany(String name, String status)
		//status 订单状态  0-未完成  1-未评分  2-已完成
		//建一个集合List<Order>，取这个公司已完成的订单
		List<Order> list = this.orderService.getOrdersByCompany(companyName, "2");
		//number是这个公司已完成订单的数量
		int number = list.size();
		float sum = 0;
		//给公司实体的number赋值，是已完成订单的数量
		company.setNumber(number);
		if(number > 0) {
			//java的增强for循环
			for(Order neworder : list) {
				sum = sum + neworder.getScore();
			}//完成订单的总分
			//计算平均分
			company.setScore(sum/number);
			//保存公司数据到数据库
			this.companyService.saveById(company);
		}
		return "people/ywcdd";
	}
	
	//获取所有订单
	@RequestMapping("/getAll")
	@ResponseBody
	public List<Order> getAll(HttpServletRequest request) {
		int currentPage = 0;
		int pageSize = 0;
		if(!request.getParameter("currentPage").equals("")) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if(!request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//返回那1页的所有订单
		return this.orderService.getAll(currentPage, pageSize);
	}
	
	@RequestMapping("/getAllCount")
	@ResponseBody
	public int getAllCount(HttpServletRequest request) {
		int count = this.orderService.getAllCount();
		return count;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		this.orderService.delete(id);
		return "true";
	}
	
	//改变订单状态，用户点击已完成执行
	@RequestMapping("/changeStatus")
	@ResponseBody
	public String changeStatus(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		Order order = this.orderService.getOrderById(id);
		order.setStatus(status);
		this.orderService.saveById(order);
		return "true";
	}

}
