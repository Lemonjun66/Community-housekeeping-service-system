package com.xy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xy.entity.Company;



public interface CompanyMapper {
	
	@Select("select * from company where name = #{name}")
	public Company getCompanyByName(String name);

	//注册时保存公司的方法
	@Insert("insert into company(name, password, status, score, number) values(#{name}, #{password}, #{status}, #{score}, #{number})")
	public void save(Company company);
	
	//从CompanyService.java过来，从company表中返回所有公司整条字段，存在集合List<Company>中
	@Select("select * from company")
	public List<Company> getAll();
	
	//1从CompanyService.java过来，从company表中返回公司用户数量
	//2从CompanyService.java过来，为了加载分页信息，所以要取公司数量
	@Select("select count(*) from company")
	public int getAllCount();
	
	//CompanyService.java的getCompanyByStatus方法过来，找status="1"(审核中)的所有公司整条字段的集合
	@Select("select * from company where status = #{status}")
	public List<Company> getCompanyByStatus(String status);
	
	//CompanyService.java的getCountByStatus方法过来，找status="1"(审核中)的公司数量
	@Select("select count(*) from company where status = #{status}")
	public int getCountByStatus(String status);
	
	@Select("select * from company where status = #{status} and type like CONCAT(CONCAT('%', #{serviceType}), '%') order by score desc")
	public List<Company> getCompanyByStatusAndService(@Param(value = "status") String status, @Param(value = "serviceType") String serviceType);
	
	@Select("select count(*) from company where status = #{status} and CONCAT(CONCAT('%', #{serviceType}), '%')")
	public int getCountByStatusAndService(@Param(value = "status") String status, @Param(value = "serviceType") String serviceType);
	
	//CompanyService.java过来，将status更改后，保存公司数据
	@Update("UPDATE company SET name = #{name}, status = #{status}, fileUrl = #{fileUrl}, type = #{type}, content = #{content}, score = #{score}, number = #{number}, remark = #{remark} WHERE id = #{id}")
	public void saveById(Company company);
	
	@Delete("delete from company where id = #{id}")
	public void delete(int id);
	
	//从CompanyService.java过来，通过id找出公司的整条字段
	@Select("select * from company where id = #{id}")
	public Company getCompanyById(int id);
}
