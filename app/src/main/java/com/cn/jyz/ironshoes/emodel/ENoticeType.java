package com.cn.jyz.ironshoes.emodel;

/**
 * 消息提醒类别
 */
public enum ENoticeType {
	/**
	 * 1表示作业预警
	 */
	TYPE_1(1),
	
	/**
	 * 2表示铁鞋状态预警
	 */
	TYPE_2(2),
	
	/**
	 * 3表示铁鞋故障预警
	 */
	TYPE_3(3);
	
	private int code;
	ENoticeType (int code) {
		this.code = code;
	}
	
	public int getCode () {
		return code;
	}
}
