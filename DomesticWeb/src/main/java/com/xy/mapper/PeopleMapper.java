package com.xy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.xy.entity.People;

public interface PeopleMapper {
	
	//注册时找有没有重复的居民名
	@Select("select * from people where code = #{name}")
	public People getPeopleByName(String name);
	
	//注册时保存居民的方法
	@Insert("insert into people(code, password) values(#{code}, #{password})")
	public void save(People people);
	
	//从PeopleService.java过来，从people表中返回所有居民整条字段，存在集合List<People>中
	@Select("select * from people")
	public List<People> getAll();
	
	//1从PeopleService.java过来，从people表中返回居民用户数量
	//2从PeopleService.java过来，为了加载分页信息，所以要取居民数量
	@Select("select count(*) from people")
	public int getAllCount();
	
	//从PeopleService.java过来，删除指定id的people整条字段
	@Delete("delete from people where id = #{id}")
	public void delete(int id);

}
