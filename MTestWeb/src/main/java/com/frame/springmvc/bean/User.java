package com.frame.springmvc.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Component
@XmlRootElement
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3873423135026753071L;
	private int Cd;
	private String username;
	private String password;
	private Date birthday;
	private String address;
	public User(){
		
	}
	public User(int cd, String username, String password, Date birthday, String address) {
		super();
		Cd = cd;
		this.username = username;
		this.password = password;
		this.birthday = birthday;
		this.address = address;
	}
	@XmlElement
	public int getCd() {
		return Cd;
	}
	public void setCd(int cd) {
		Cd = cd;
	}
	@XmlElement
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@XmlElement
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@XmlElement
	//@XmlJavaTypeAdapter(DateAdapter.class)
	@JsonSerialize(using=UserDateSerializer.class)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@XmlElement
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "User [Cd=" + Cd + ", username=" + username + ", password=" + password + ", birthday=" + birthday
				+ ", address=" + address + "]";
	}
	
	
}
