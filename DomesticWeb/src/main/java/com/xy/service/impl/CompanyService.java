package com.xy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xy.entity.Company;
import com.xy.mapper.CompanyMapper;
import com.xy.service.ICompanyService;
import com.xy.utils.PageBean;


@Service
public class CompanyService implements ICompanyService {
	
	@Autowired
	private CompanyMapper cMapper;
	
	//通过名字找公司，去mapper包下的CompanyMapper.java的getCompanyByName方法找
	@Override
	public Company getCompanyByName(String name) {
		return this.cMapper.getCompanyByName(name);
	}

	@Override
	public void save(Company company) {
		this.cMapper.save(company);
	}//注册时用到的保存公司用户，去mapper包下的CompanyMapper.java的save方法找

	//通过状态(审核)找公司
	//从CompanyCtl.java的getCompanyByStatus方法过来，getCompanyByStatus(null,"1",1,10)
	@Override
	public List<Company> getCompanyByStatus(String serviceType, String status, int currentPage, int pageSize) {
		//建1个存放实体公司的集合
		List<Company> allItems = null;
		int countNums = 0;
		 //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        if(serviceType == null) {
        	//去mapper包下CompanyMapper.java找getCompanyByStatus方法，这里的status="1",获得审核中的所有公司整条字段的集合
        	allItems = this.cMapper.getCompanyByStatus(status);        //全部商品
        	//去mapper包下CompanyMapper.java找getCountByStatus方法，这里的status="1",获得审核中的公司数量
        	countNums = this.cMapper.getCountByStatus(status);          //总记录数
        }else {
        	allItems = this.cMapper.getCompanyByStatusAndService(status, serviceType);        //全部商品
        	countNums = this.cMapper.getCountByStatusAndService(status, serviceType);          //总记录数
        }
        //PageBean类在utils包下，(当前页,1页数量,审核中的公司数量)
        PageBean<Company> pageData = new PageBean<>(currentPage, pageSize, countNums);
        //把所有审核中的公司整条字段List集合取当前页的字段填入pageData
        pageData.setItems(allItems);
        //返回PageBean<company>回到CompanyCtl.java
        return pageData.getItems();
	}

	@Override
	public int getCompanyCountByStatus(String status) {
		return this.cMapper.getCountByStatus(status);
	}
	
	//从CompanyCtl.java的changeStatus过来，去mapper下CompanyMapper.java找saveById方法
	@Override
	public void saveById(Company company) {
		this.cMapper.saveById(company);
	}
	
	//从CompanyCtl.java过来，(1,10)
	@Override
	public List<Company> getAll(int currentPage, int pageSize) {
		//设1个List集合
		List<Company> allItems = null;
		int countNums = 0;
		 //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        //去mapper包下CompanyMapper.java找getAll方法，获得所有公司整条字段，存在集合allItems中
        allItems = this.cMapper.getAll();        //全部商品
        //去mapper包下CompanyMapper.java找getAllCount方法，获得公司用户数量，赋值给countNums
        countNums = this.cMapper.getAllCount();          //总记录数
        //PageBean类在utils包下，(当前页,1页数量,公司用户数量)
        PageBean<Company> pageData = new PageBean<>(currentPage, pageSize, countNums);
        //把所有公司整条字段List集合填入pageData
        pageData.setItems(allItems);
        //返回？List<company>回到CompanyCtl.java
        return pageData.getItems();
	}

	//从CompanyCtl.java过来，为了加载分页信息
	@Override
	public int getAllCount() {
		//去mapper包下找CompanyMapper.java的getAllCount方法，取到公司数量
		return this.cMapper.getAllCount();
	}

	@Override
	public void delete(int id) {
		this.cMapper.delete(id);
	}

	//从CompanyCtl.java的changeStatus过来
	@Override
	public Company getCompanyById(int id) {
		//去mapper包下CompanyMapper.java找getCompanyById方法，通过id找出公司的整条字段
		return this.cMapper.getCompanyById(id);
	}

}
