package com.xy.ctl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlCtl {

	
	@RequestMapping("/manager/people")
	public String manager_people() {
		return "manager/people";
	}
	
	@RequestMapping("/manager/company")
	public String manager_company() {
		return "manager/company";
	}
	
	@RequestMapping("/manager/companySH")
	public String manager_companySH() {
		return "manager/companySH";
	}
	
	@RequestMapping("manager/order")
	public String manager_order() {
		return "manager/order";
	}
	
	@RequestMapping("manager/serviceType")
	public String manager_serviceType() {
		return "manager/serviceType";
	}
	
	@RequestMapping("people/xdd")
	public String people_xdd() {
		return "people/xdd";
	}
	
	@RequestMapping("people/ywcdd")
	public String people_ywcdd() {
		return "people/ywcdd";
	}
	
	@RequestMapping("people/wwcdd")
	public String people_wwcdd() {
		return "people/wwcdd";
	}
	
	@RequestMapping("people/ypjdd")
	public String people_ypjdd() {
		return "people/ypjdd";
	}
	
	@RequestMapping("people/fwgs")
	public String people_fwgs() {
		return "people/fwgs";
	}
	
	@RequestMapping("company/gsxx")
	public String company_gsxx() {
		return "company/gsxx";
	}
	
	@RequestMapping("company/sddd")
	public String company_sddd() {
		return "company/sddd";
	}
	
	@RequestMapping("company/lsdd")
	public String company_lsdd() {
		return "company/lsdd";
	}
	
	@RequestMapping("people/chat")
	public String people_chat() {
		return "people/chat";
	}

	@RequestMapping("company/chat")
	public String company_chat() {
		return "company/chat";
	}
}
