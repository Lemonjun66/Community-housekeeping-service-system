package com.xy.ctl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.entity.People;
import com.xy.service.IPeopleService;

@Controller
@RequestMapping("/people/")
public class PeopleCtl {
	
	@Autowired
	private IPeopleService peopleService;
	
	@RequestMapping("/getPeopleUsername")
	@ResponseBody
	public String getUserName(HttpSession session) {
		String username = (String) session.getAttribute("Pusername");
		return username;
	}
	
	//从people.js的people方法过来
	@RequestMapping("/getAll")
	@ResponseBody
	//返回1个集合List，里面存放的是entity包下的People实体类
	public List<People> getAll(HttpServletRequest request) {
		int currentPage = 0;
		int pageSize = 0;
		//"currentPage"是1，不等于""，判断正确
		if(!request.getParameter("currentPage").equals("")) {
			//Integer.parseInt()是把()里的内容转换成整数,让右边currentPage=1
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		//"pageSize"是10，不等于""，判断正确
		if(!request.getParameter("pageSize").equals("")) {
			//让右边pageSize=10
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//getAll(1,10)，去service.impl包下PeopleService.java中找getAll方法，返回PageBean<people>，回到people.js
		return this.peopleService.getAll(currentPage, pageSize);
	}
	
	//从people.js的加载分页信息LoadXinXi方法过来
	@RequestMapping("/getAllCount")
	@ResponseBody
	public int getAllCount(HttpServletRequest request) {
		//去peopleService找getAllCount方法，取到居民数量
		int count = this.peopleService.getAllCount();
		//返回居民数量
		return count;
	}
	
	//从people.js来
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest request) {
		//把前端读到的id赋值给左边id
		int id = Integer.parseInt(request.getParameter("id"));
		//去service.impl包下PeopleService.java找delete方法
		this.peopleService.delete(id);
		return "true";
	}

}
