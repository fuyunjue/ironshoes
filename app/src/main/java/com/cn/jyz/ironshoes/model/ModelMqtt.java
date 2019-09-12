package com.cn.jyz.ironshoes.model;

import java.io.Serializable;

public class ModelMqtt extends ModelBase implements Serializable {
	
	private static final long serialVersionUID = 6591686015840928064L;
	
	private int badge;      //数量
	private String content; //报警内容
	private String device;  //报警设备
	private String sound;   //提醒方式，默认为default震动
	private String station; //站场名称
	private String title;   //消息标题
	private String tracks;  //报警轨道
	private String type;    //报警类型，默认为1
	private String txno;    //铁鞋编号
	
	public String getTxno () {
		return txno;
	}
	
	public void setTxno (String txno) {
		this.txno = txno;
	}
	
	public int getBadge () {
		return badge;
	}
	
	public void setBadge (int badge) {
		this.badge = badge;
	}
	
	public String getContent () {
		return content;
	}
	
	public void setContent (String content) {
		this.content = content;
	}
	
	public String getDevice () {
		return device;
	}
	
	public void setDevice (String device) {
		this.device = device;
	}
	
	public String getSound () {
		return sound;
	}
	
	public void setSound (String sound) {
		this.sound = sound;
	}
	
	public String getStation () {
		return station;
	}
	
	public void setStation (String station) {
		this.station = station;
	}
	
	public String getTitle () {
		return title;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public String getTracks () {
		return tracks;
	}
	
	public void setTracks (String tracks) {
		this.tracks = tracks;
	}
	
	public String getType () {
		return type;
	}
	
	public void setType (String type) {
		this.type = type;
	}
}
