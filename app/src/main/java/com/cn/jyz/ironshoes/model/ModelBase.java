package com.cn.jyz.ironshoes.model;

public abstract class ModelBase {

	private String msg;
	private int code;   //  0表示正常
	
	private int count;
	
	public String getMsg () {
		return msg;
	}
	
	public void setMsg (String msg) {
		this.msg = msg;
	}
	
	public int getCode () {
		return code;
	}
	
	public void setCode (int code) {
		this.code = code;
	}
	
	public int getCount () {
		return count;
	}
	
	public void setCount (int count) {
		this.count = count;
	}
}
