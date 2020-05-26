package com.xy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xy.entity.People;
import com.xy.mapper.PeopleMapper;
import com.xy.service.IPeopleService;
import com.xy.utils.PageBean;

@Service
public class PeopleService implements IPeopleService {

	@Autowired
	private PeopleMapper pMapper;
	
	//通过名字找居民，去mapper包下的PeopleMapper.java的getPeopleByName方法找
	@Override
	public People getPeopleByName(String name) {
		return this.pMapper.getPeopleByName(name);
	}

	@Override
	public void save(People people) {
		this.pMapper.save(people);
	}//注册时用到的保存居民用户，去mapper包下的PeopleMapper.java的save方法找

	//从PeopleCtl.java过来，(1,10)
	@Override
	public List<People> getAll(int currentPage, int pageSize) {
		//设1个List集合
		List<People> allItems = null;
		int countNums = 0;
		 //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        //去mapper包下PeopleMapper.java找getAll方法，获得所有居民整条字段，存在集合allItems中
        allItems = this.pMapper.getAll();        //全部商品
        //去mapper包下PeopleMapper.java找getAllCount方法，获得居民用户数量，赋值给countNums
        countNums = this.pMapper.getAllCount();          //总记录数
        //PageBean类在utils包下，(当前页,1页数量,居民用户数量)
        PageBean<People> pageData = new PageBean<>(currentPage, pageSize, countNums);
        //把所有居民整条字段List集合取当前页的字段填入pageData
        pageData.setItems(allItems);
        //返回PageBean<people>回到PeopleCtl.java
        return pageData.getItems();
	}

	//从PeopleCtl.java过来，为了加载分页信息
	@Override
	public int getAllCount() {
		//去mapper包下找PeopleMapper.java的getAllCount方法，取到居民数量
		return this.pMapper.getAllCount();
	}

	//从PeopleCtl.java过来，根据id删除居民用户
	@Override
	public void delete(int id) {
		//去mapper包下找PeopleMapper.java的delete方法
		this.pMapper.delete(id);
	}

}
