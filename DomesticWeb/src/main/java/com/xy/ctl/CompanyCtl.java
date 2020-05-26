package com.xy.ctl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xy.entity.Company;
import com.xy.service.ICompanyService;

@Controller
@RequestMapping("/company/")
public class CompanyCtl {
	
	@Autowired
    private ICompanyService companyService;
	
	//1从companySH.js的companySH方法过来
	//2从居民搜索框过来的
	//3下订单勾选公司过来
	@RequestMapping("/getCompanyByStatus")
	@ResponseBody
	public List<Company> getCompanyByStatus(HttpServletRequest request) {
		int currentPage = 0;
		int pageSize = 0;
		//从居民搜索框\下订单勾选公司过来的，就是要status = "2"(审核通过)的公司整条字段
		String status = "2";
		String serviceType = null;
		if(!request.getParameter("currentPage").equals("")) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if(!request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//companySH.js传过来的status="1"(审核中)，公司资质审核界面需要这些是审核中的公司数据
		if(!request.getParameter("status").equals("")) {
			status = request.getParameter("status");
		}
		//1居民搜索框过来的，把要搜索的内容赋值给serviceType
		//2下订单勾选公司过来,把服务内容名称赋值给serviceType
		if(!request.getParameter("serviceType").equals("")) {
			serviceType = request.getParameter("serviceType");
		}
		//getCompanyByStatus(null,"1",1,10),去service.impl包下找getCompanyByStatus方法,返回PageBean<company>，回到company.js
		//下订单回到xdd.js
		return companyService.getCompanyByStatus(serviceType,status,currentPage, pageSize);
	}
	
	//通过状态取得公司信息
	@RequestMapping("/getCompanyCountByStatus")
	@ResponseBody
	public int getCompanyCountByStatus(HttpServletRequest request) {
		String status = "2";
		if(!request.getParameter("status").equals("")) {
			status = request.getParameter("status");
		}
		int count = this.companyService.getCompanyCountByStatus(status);
		return count;
	}
	
	//公司用户页面右上角公司名
	@RequestMapping("/getCompanyUsername")
	@ResponseBody
	public String getUserName(HttpSession session) {
		String username = (String) session.getAttribute("Cusername");
		return username;
	}
	
	//获得当前登录公司信息，在公司信息页面
	@RequestMapping("/getCompany")
	@ResponseBody
	public Company getCompany(HttpSession session) {
		String username = (String) session.getAttribute("Cusername");
		//com.xy.service.impl CompanyService.java getCompanyByName
		//返回公司的整条字段
		return this.companyService.getCompanyByName(username);
	}
	
	//获得所有公司数据
	//从company.js的company方法过来
	@RequestMapping("/getAll")
	@ResponseBody
	//返回1个集合List，里面存放的是entity包下的Company实体类
	public List<Company> getAll(HttpServletRequest request) {
		int currentPage = 0;
		int pageSize = 0;
		//"currentPage"是1，不等于""，判断正确
		if(!request.getParameter("currentPage").equals("")) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		//"pageSize"是10，不等于""，判断正确
		if(!request.getParameter("pageSize").equals("")) {
			//让右边pageSize=10
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//getAll(1,10)，去service.impl包下companyService.java中找getAll方法，返回？List<company>，回到company.js
		return this.companyService.getAll(currentPage, pageSize);
	}
	
	//共有。。。数据
	//从company.js的加载分页信息LoadXinXi方法过来
	@RequestMapping("/getAllCount")
	@ResponseBody
	public int getAllCount(HttpServletRequest request) {
		//去companyService找getAllCount方法，取到公司数量
		int count = this.companyService.getAllCount();
		return count;
	}
	
	//删除公司
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		this.companyService.delete(id);
		return "true";
	}
	
	//改变状态
	//从companySH.js的拼接表格字段过来,点击审核通过和不通过都是这里
	@RequestMapping("/changeStatus")
	@ResponseBody
	public String changeStatus(HttpServletRequest request) {
		//将companySH.js传过来的需评审公司的id赋值给左边id
		int id = Integer.parseInt(request.getParameter("id"));
		//将审核状态status也赋值给左边
		String status = request.getParameter("status");
		//建1个company对象，去service.impl包下CompanyService.java找getCompanyById，得到对应id的整条公司字段
		Company company = this.companyService.getCompanyById(id);
		//将状态赋值给company，2是过审，3是不过审
		company.setStatus(status);
		//将company对象存入company表单，去service.impl包下CompanyService.java找saveById方法
		this.companyService.saveById(company);
		return "true";
	}
	
	//上传文件
	@RequestMapping("/uploadFile")
	public String uploadFile(HttpSession session, HttpServletRequest req, MultipartHttpServletRequest multiReq) {
		String username = (String) session.getAttribute("Cusername");
		Company company = this.companyService.getCompanyByName(username);
		//更换服务
		String type = req.getParameter("type");
		//公司简介
		String content = req.getParameter("content");
		System.out.println(content);
		company.setType(type);
		company.setContent(content);
		// 获取上传文件的路径
		//获取文件名
		String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
		System.out.println("uploadFlePath:" + uploadFilePath);
		//trim去除文件名两端空格
		if(!uploadFilePath.trim().equals("")) {
			// 截取上传文件的文件名
			String uploadFileName = uploadFilePath.substring(
					uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));//substring lastIndexOf indexOf
			System.out.println("multiReq.getFile()" + uploadFileName);
			// 截取上传文件的后缀
			String uploadFileSuffix = uploadFilePath.substring(
					uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
			String fileUrl = "E://uploadFiles//" + uploadFileName + "." + uploadFileSuffix;
			//保存到company实体上传路径值
			company.setFileUrl(fileUrl);
			company.setStatus("1");
			System.out.println("uploadFileSuffix:" + uploadFileSuffix);
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				//给输入流赋值前端上传文件
				fis = (FileInputStream) multiReq.getFile("file1").getInputStream();
				//定义输出到指定的路径
				fos = new FileOutputStream(new File("E://uploadFiles//" + uploadFileName
						+ ".")
						+ uploadFileSuffix);
				byte[] temp = new byte[1024];
				int i = fis.read(temp);
				while (i != -1){
					fos.write(temp,0,temp.length);
					fos.flush();
					i = fis.read(temp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		this.companyService.saveById(company);
		return "company/gsxx";
	}
	
	//文件下载
	//从companySH.js的拼接表格字段过来
	@RequestMapping("/download")
	public String testDownload(HttpServletRequest request, HttpServletResponse res) {
		//fileName是文件路径字符串
		String fileName = request.getParameter("fileUrl");
		System.out.println(fileName);
		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		//定义了一个byte类型的数组，数组长度为1024
		byte[] buff = new byte[1024];
		//输入流
		BufferedInputStream bis = null;
		//输出流
		OutputStream os = null;
		try {
			os = res.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//下载完成，回到companySH.html
		return "manager/companySH";
	}

}
