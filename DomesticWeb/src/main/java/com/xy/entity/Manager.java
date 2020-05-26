package com.xy.entity;

public class Manager {
	

	private int id;
	

	private String name;
	

	private String pwd;

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Manager(int id, String name, String pwd) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	public Manager() {
		super();
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", name=" + name + ", pwd=" + pwd + "]";
	}
	
	
}