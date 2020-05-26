package com.xy.entity;

public class People {
	
	private int id;
	
	private String code;
	
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public People(int id, String code, String password) {
		super();
		this.id = id;
		this.code = code;
		this.password = password;
	}

	public People() {
		super();
	}

	@Override
	public String toString() {
		return "People [id=" + id + ", code=" + code + ", password=" + password + "]";
	}

}
