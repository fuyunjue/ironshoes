package com.cn.jyz.ironshoes.emodel;

public enum EResponseCode {
	/**
	 * 成功
	 */
	SUCCESS(0),
	
	/**
	 * 接口请求成功，提示错误内容
	 */
	WRONG(500);
	
	private int code;
	EResponseCode(int code) {
		this.code = code;
	}
	
	public int getCode () {
		return code;
	}
}
