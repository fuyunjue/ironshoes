package com.cn.jyz.ironshoes.model;

import java.io.Serializable;


public class ModelUserInfo extends ModelBase implements Serializable {
	private static final long serialVersionUID = 7010819391810827241L;
	
	private String levelmark;
	private String role;
	private int expire;
	private String chinesename;
	private String mobile;
	private String userid;
	private String token;
	
	
	public enum Role {
		/**
		 * 系统管理员
		 */
		SYSADMIN(0),
		
		/**
		 * 普通管理员
		 */
		ADMIN(1),
		
		/**
		 * 领导
		 */
		LEAD(2),
		
		/**
		 * 查询用户
		 */
		QUERYUSER(3),
		
		/**
		 * 操作用户
		 */
		OPUSER(4),
		
		/**
		 * 预留1
		 */
		OPRESERVED1(5),
		
		/**
		 * 预留2
		 */
		OPRESERVED2(6);
		
		private int key;
		
		Role(int key) {
			this.key = key;
		}
		
		public int getKey () {
			return key;
		}
	}
	
	
	public String getLevelmark () {
		return levelmark;
	}
	
	public void setLevelmark (String levelmark) {
		this.levelmark = levelmark;
	}
	
	public String getRole () {
		return role;
	}
	
	public void setRole (String role) {
		this.role = role;
	}
	
	public int getExpire () {
		return expire;
	}
	
	public void setExpire (int expire) {
		this.expire = expire;
	}
	
	public String getChinesename () {
		return chinesename;
	}
	
	public void setChinesename (String chinesename) {
		this.chinesename = chinesename;
	}
	
	public String getMobile () {
		return mobile;
	}
	
	public void setMobile (String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserid () {
		return userid;
	}
	
	public void setUserid (String userid) {
		this.userid = userid;
	}
	
	public String getToken () {
		return token;
	}
	
	public void setToken (String token) {
		this.token = token;
	}
	
}
