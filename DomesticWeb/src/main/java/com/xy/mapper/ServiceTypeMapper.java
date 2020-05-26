package com.xy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.xy.entity.ServiceType;

public interface ServiceTypeMapper {

	//获取所有的服务类型
	@Select("select * from serviceType")
	public List<ServiceType> getAll(); 
	
	//获得服务类型总数
	@Select("select count(*) from serviceType")
	public int getAllCount();
	
	@Delete("delete from serviceType where id = #{id}")
	public void delete(int id);
	
	//从ServiceTypeService.java过来，将新建服务类型存入表单
	@Insert("insert into serviceType(name) values(#{name})")
	public void save(ServiceType serviceType);
	
	@Select("select * from serviceType where id = #{id}")
	public ServiceType getServiceTypeById(int id);
}
