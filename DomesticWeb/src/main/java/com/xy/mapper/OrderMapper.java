package com.xy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xy.entity.Order;

public interface OrderMapper {
	
	@Insert("insert into theOrder(people, company, time, type, phone, name, wechat, content, status) value(#{people}, #{company}, #{time}, #{type}, #{phone}, #{name}, #{wechat}, #{content}, #{status})")
	public void save(Order order);
	
	@Select("select * from theOrder where people = #{people} and status = #{status}")
	public List<Order> getOrderByPeopleAndStatus(@Param(value = "people") String people, @Param(value = "status") String status);
	
	@Select("select count(*) from theOrder where people = #{people} and status = #{status}")
	public int getOrderCountByPeopleAndStatus(@Param(value = "people") String people, @Param(value = "status") String status);
	
	@Select("select * from theOrder where company = #{company} and status = #{status}")
	public List<Order> getOrderByCompanyAndStatus(@Param(value = "company") String company, @Param(value = "status") String status);
	
	@Select("select count(*) from theOrder where company = #{company} and status = #{status}")
	public int getOrderCountByCompanyAndStatus(@Param(value = "company") String company, @Param(value = "status") String status);
	
	@Select("select * from theOrder where id = #{id}")
	public Order getOrderById(int id);
	
	@Select("select * from theOrder where company = #{name} and status = #{status}")
	public List<Order> getOrdersByCompany(@Param(value = "name") String name, @Param(value = "status") String status);
	
	@Update("UPDATE theOrder SET people = #{people}, company = #{company}, time = #{time}, type = #{type}, score = #{score}, phone = #{phone}, name = #{name}, content = #{content}, wechat = #{wechat}, status = #{status} WHERE id = #{id}")
	public void saveById(Order order);
	
	@Select("select * from theOrder")
	public List<Order> getAll();
	
	@Select("select count(*) from theOrder")
	public int getAllCount();
	
	@Delete("delete from theOrder where id = #{id}")
	public void delete(int id);

}
