package com.xy.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xy.entity.Company;
import com.xy.mapper.CompanyMapper;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class Task {
	
	@Autowired
	private CompanyMapper cMapper;  
	
    //@Scheduled用法https://www.jianshu.com/p/1defb0f22ed1
	//每30秒执行一次
    @Scheduled(cron = "0/30 * * * * ?")
	//每月1号凌晨执行一次判断企业服务质量
    //@Scheduled(cron="0 0 0 1 * ?")
    private void configureTasks() {
    	List<Company> list = this.cMapper.getAll();
    	for(Company company : list) {
    		float score = company.getScore();
    		if(company.getStatus().equals("2")) {
    			if(score >= 4) {
    				company.setRemark(0);
    			}else if(score>=3) {
    				company.setRemark(1);
    			}else if(score>=2) {
    				company.setRemark(2);
    			}else {
    				company.setRemark(3);
    				company.setStatus("4");
    			}
    			this.cMapper.saveById(company);
    		}
    	}
        System.out.println("执行定时任务时间: " + LocalDateTime.now());
    }
}
