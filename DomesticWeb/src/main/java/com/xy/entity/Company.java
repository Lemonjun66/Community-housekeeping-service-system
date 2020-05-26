package com.xy.entity;

public class Company {
	
	//唯一标志
	private int id;
	
	//公司名
	private String name;
	
	//密码
	private String password;
	
	//状态   0--未审核   1--审核中   2--审核通过    3--审核未通过   4--停止服务
	private String status;
	
	//上传文件路径
	private String fileUrl;
	
	//服务类型
	private String type;

	//公司简介
	private String content;
	
	//公司评分
	private float score;
	
	//服务次数
	private int number;
	
	//记录系统对公司的评分   0-服务良好  1-服务一般  2-尚需改进  3-停业整改
	private int remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getRemark() {
		return remark;
	}

	public void setRemark(int remark) {
		this.remark = remark;
	}

	
}
