package com.frame.springmvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.springmvc.bean.User;
import com.frame.springmvc.service.UserService;

@Controller
public class HelloController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/helloWorld")
	public String helloWorld(HttpServletRequest request,Model model) throws Exception {
		User user =userService.searchUserById(2);
		model.addAttribute("message", "Hello World!");
		model.addAttribute("user",user);
		
		//System.out.println(request.getServletContext().getContextPath()); //
		System.out.println(request.getServletContext().getRealPath("/")); //F:\projects\Test\MTestWeb\target\m2e-wtp\web-resources
		/*File file = new File("abc1.txt");
		FileOutputStream out = new FileOutputStream(file);
		String content = "This is the text content";
		byte[] buff = content.getBytes();
		out.write(buff);
		out.flush();
		out.close();*/
		return "hello";
	}
	
	/**
	 * 循环获得所有机场三字码信息
	 * @param page
	 * @param resp
	 * @return
	 * @throws Exception 设置produces后会执行 response的Content-Type:application/json;charset=utf-8
	 */
	@RequestMapping(value="/getKey",produces="application/json;charset=utf-8")
	public @ResponseBody String getKey(@RequestParam(value="page",defaultValue="1") String page,HttpServletResponse resp) throws Exception{
		CloseableHttpClient httpClient= HttpClients.createDefault();  
		//HttpGet httpget = new HttpGet("http://www.6qt.net/index.asp?Field=City&keyword=&MaxPerPage=50&page="+page);  
		HttpGet httpget = new HttpGet("https://airport.supfree.net/index.asp?page="+page);
		CloseableHttpResponse response = httpClient.execute(httpget);  
		HttpEntity httpEntity= response.getEntity();  
		String strResult= EntityUtils.toString(httpEntity,"GBK");
		System.out.println(page);
		return strResult;
		
	}
	
	/**
	 * 根据机场三字码获得 每个机场的详细信息
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/getDetail",produces="application/json;charset=utf-8")
	public @ResponseBody String getDetail(@RequestParam("code") String code) throws Exception{
		CloseableHttpClient httpClient= HttpClients.createDefault();  
		HttpGet httpget = new HttpGet("http://www.5688.com.cn/airport/cn/"+code+".html");
		CloseableHttpResponse response = httpClient.execute(httpget);  
		HttpEntity httpEntity= response.getEntity();  
		String strResult= EntityUtils.toString(httpEntity);
		System.out.println(code);
		return strResult;
	}
}
