package com.xy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.xy.entity.Manager;

public interface ManagerMapper {

//    @Select("SELECT * FROM manager")
//    @Results({
//        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
//        @Result(property = "nickName", column = "nick_name")
//    })
	@Insert(value="insert into manager(name, pwd) values (#{name}, #{pwd})")
	public void addManager(Manager m);
	
	@Select("SELECT * FROM manager where id = #{id}")
	@Results({
	    @Result(property = "name",  column = "name"),
	    @Result(property = "pwd", column = "pwd")
	})
	public void getById(String id);
	
	//column="数据库字段名" property="实体类属性" 
	//ManagerService.java方法过来，找出和注册名一样的管理员名的整条管理员数据
	@Select("SELECT * FROM manager where name = #{name}")
	@Results({
	    //1个字段，property表示当前实体类里的名称，column表示数据库里的名称，2个对应起来
		@Result(property = "name",  column = "name"),
	    @Result(property = "pwd", column = "pwd")
	})
	public Manager getManagerByName(String name);
	
	
}
