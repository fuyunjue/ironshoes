package com.cn.jyz.ironshoes.model;

import java.io.Serializable;

public class ModelWorkResult implements Serializable {
	
	private static final long serialVersionUID = -5534943531913336924L;
	private String msg;
	private int code;   //  0表示正常
	
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
	
}
