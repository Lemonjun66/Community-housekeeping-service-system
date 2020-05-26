package com.xy.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.entity.Company;
import com.xy.entity.Manager;
import com.xy.entity.People;
import com.xy.service.ICompanyService;
import com.xy.service.IManagerService;
import com.xy.service.IPeopleService;
import com.xy.utils.RandomValidateCode;
import com.xy.utils.SecurityUtil;

@Controller
@RequestMapping("/login")
public class LoginCtl {
	
	private static String basePath = "/";
	
	@Autowired
	private IPeopleService peopleService;
	
	@Autowired
	private ICompanyService companyService;
	
	@Autowired
	private IManagerService managerService;

	/*
	 * 生成验证码
	 */
	@RequestMapping(value="/checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
		//设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        
        //设置响应头信息，告诉浏览器不要缓存此内容 
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        
        RandomValidateCode randomValidateCode = new RandomValidateCode();
//        session.setAttribute("verifyCodeValue", randomValidateCode.);
//        System.out.println(session.getAttribute("verifyCodeValue").toString());
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/*
	 * 登录方法
	 */
	//从index.html的form过来，用户名、密码、验证码正确，登录前验证通过，触发form里的action属性
	@RequestMapping(value="/login", method = RequestMethod.POST)
	//HttpServletRequest是前端请求 getParameter(String name)指根据name获取请求参数 HttpSession是建立一个缓存的容器
	public String login(HttpServletRequest request, HttpSession session) {
		//取home.js里loginCheck方法的data中username赋值给name
		//或者取index.html中input中的name="username"来取value中输入的用户名
		String name = request.getParameter("username");
		String pwd = request.getParameter("password");
		//通过名字找用户，找到了就把整个用户的字段赋值给实体
		Manager manager = this.managerService.getManagerByName(name);
		Company company = this.companyService.getCompanyByName(name);
		People people = this.peopleService.getPeopleByName(name);
		//管理员实体不空，说明登录的是管理员
		if(manager != null) {
			//判断用户名和密码是否正确
			if(manager.getName().equals(name) && SecurityUtil.MD5(pwd).equals(manager.getPwd())) {
				//正确，就把管理员的用户名赋值给Musername
				session.setAttribute("Musername", name);
				return "manager/manager";//跳转到manager文件夹下的manager.html页面(管理员首页)
			}
		}else if(company != null) {
			if(company.getName().equals(name) && SecurityUtil.MD5(pwd).equals(company.getPassword())) {
				session.setAttribute("Cusername", name);
				return "company/company_main";//跳转到company文件夹下的company_main.html页面(公司首页)
			}			
		}else if(people != null) {
			if(people.getCode().equals(name) && SecurityUtil.MD5(pwd).equals(people.getPassword())) {
				session.setAttribute("Pusername", name);
				return "people/people_main";
			}			
		}
		
		return basePath + "index";
	}
	
	/*
	 * 登出方法
	 */
	@RequestMapping("/loginOut")
	public String loginOut() {
		return basePath + "index";
	}
	
	/*
	 * 跳转到注册页面
	 */
	@RequestMapping("/register")
	public String register() {
		return "register";//去找register.html页面
	}
	
	/*
	 * 注册方法
	 */
	@RequestMapping("/doRegister")
	public String doRegister(HttpServletRequest request) {
		String sex = request.getParameter("sex");//去找register.html下的name值是"sex"的input的value
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(sex);
		if(sex != "" && sex.equals("公司")) {
			Company company = new Company();//Company方法在entity包下
			//if里的判断，找一下有没有和注册过的公司名重复，
			//通过service.impl包下CompanyService.java下getCompanyByName方法，
			//可以找到重复的公司名
			if(this.companyService.getCompanyByName(username) == null) {
				company.setName(username);
				company.setPassword(SecurityUtil.MD5(password));
				company.setNumber(0);
				company.setScore(3);
				company.setStatus("0");
				company.setRemark(-1);
				this.companyService.save(company);
			}//company方法在entity包下company.java文件里
			//最后保存，用service.impl包下CompanyService.java的save方法
			//设置密码是在xy.utils包下的SecurityUtil.java方法中，写了MD5这个密码加密方法
		}
		else if(sex != "" && sex.equals("居民")) {
			People people = new People();
			//这个if判断找有没有和注册过的居民名重复，
			//通过service.impl包下peopleService.java下getPeopleByName方法，
			//可以找到重复的居民名
			if(this.peopleService.getPeopleByName(username) == null) {
				people.setCode(username);
				people.setPassword(SecurityUtil.MD5(password));
				this.peopleService.save(people);
			}//people方法在entity包下people.java文件里
			//最后保存，用service.impl包下PeopleService.java的save方法
			
		}
		//注册完成，返回登录页面
		return basePath + "index";
	}
	
	/*
	 * 确认用户名是否存在
	 */
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	@ResponseBody
	//注册时用到
	public int checkUserName(HttpServletRequest request) {
		//从home.js那里取到了username，赋给username
		String username = request.getParameter("username");
		//检测有没有和管理员、公司、居民重复
		//Manager实体类在entity包下Manager.java里
		//右边去service.impl包下找getManagerByName方法
		//如果管理员名和注册名相同，就将整条管理员字段赋值给manager对象
		Manager manager = this.managerService.getManagerByName(username);
		Company company = this.companyService.getCompanyByName(username);
		People people = this.peopleService.getPeopleByName(username);
		//如果管理员、公司、居民值都为空，说明没取到重复名，返回给home.js-1
		if(manager == null && company == null && people == null) {
			return -1;
		}else {
			return 1;
		}//不然，就是取到了重复名，返回给home.js1
	}
	
	/*
	 * 登录前进行验证
	 */
	//从home.js的loginCheck过来，
	@RequestMapping(value = "/beforeLogin", method = RequestMethod.POST)
	@ResponseBody
	public String beforeLogin(HttpServletRequest request, HttpSession session) {
		//home.js中loginCheck方法传过来了"username""pwd""verifyCode"的值，赋给左边username、password、inputVerifyCode
		String username = request.getParameter("username");
		String password = request.getParameter("pwd");
		String inputVerifyCode = request.getParameter("verifyCode");//自己输入的验证码
		String verifyCodeValue = (String) session.getAttribute("RANDOMCODEKEY");//电脑读的验证码
		//toUpperCase把字符串转换为大写
		//左边电脑读的验证码verifyCodeValue和右边自己输的并转成大写的验证码inputVerifyCode如果相等，就进入
		if(verifyCodeValue.equals(inputVerifyCode.toUpperCase())){
			Manager manager = this.managerService.getManagerByName(username);
			if(manager == null) {
			Company company = this.companyService.getCompanyByName(username);
				if(company == null) {
					//如果既不是管理员，又不是公司，那有可能是居民
					//去peopleService实体下的getPeopleByName方法通过找用户名的方法找到整条居民字段
					People people = this.peopleService.getPeopleByName(username);
					//也不是居民，那只能是用户名错误，返回"nameFalse"给home.js的loginCheck
					if(people == null) {								
						return "nameFalse";
					}else {
						//equals前面是输入的密码转化成MD5加密形式，后面是输入的密码去数据库取密码
						if(SecurityUtil.MD5(password).equals(people.getPassword())) {
							return "true";
						} else {
							return "pwdFalse";
						}
					}			
				}else {
					if(SecurityUtil.MD5(password).equals(company.getPassword())) {
						return "true";
					} else {
						return "pwdFalse";
					}
				}
			}else {
				if(SecurityUtil.MD5(password).equals(manager.getPwd())) {
					return "true";
				} else {
					return "pwdFalse";
				}
			}
		} else {
			return "yzmFalse";
		}
	}
}
