package com.cjy.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("web1")
public class Web1Controller {
	
	@RequestMapping(value="/data1")
	@ResponseBody
	public Object index(){
		System.out.println("==========web1 data1==========");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("请求成功", "data1");
		return map;
	}

	@ResponseBody
	@RequestMapping(value="data2")
	public Object data2(){
		System.out.println("==========web1 data2==========");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("请求成功", "data2");
		return map;
	}

	@ResponseBody
	@RequestMapping(value="data3")
	public Object data3(){
		System.out.println("==========web1 data3==========");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("请求成功", "data3");
		return map;
	}

	@RequestMapping("web1Main")
	public String web1Main(){
		return "/web1Main";
	}
	
}
