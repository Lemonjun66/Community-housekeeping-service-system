package com.xy;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.github.pagehelper.PageHelper;

//127.0.0.1本电脑IP     :   8080端口号      /
@Controller
//启动类注解
@SpringBootApplication
//扫描数据处理层
@MapperScan("com.xy.mapper")
//扫描所有java后端代码信息
@ComponentScan(basePackages = {"com.xy.*"})
public class Application {
	
	@RequestMapping("/")
	public String getMain(){
		return "index";
	}

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    
    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
