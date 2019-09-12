package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用于画电子地图缓存数据
 */
public class HashTracksInfo implements Serializable {
	
	private static final long serialVersionUID = -1626072984832739654L;
	
	private int trackRealWidth; //电子地图真实展示时宽度（px）
	private int trackRealHeight;    //高度(px)
	private int trackno;    //股道编号
	
	
	//股道实体
	private ModelTrack.TrackInfo trackInfo;
	//该股道上的机车、铁鞋列表
	private ArrayList<ModelMotorsAndTx.MotorAndTxInfo> list;
	
	public int getTrackRealWidth () {
		return trackRealWidth;
	}
	
	public void setTrackRealWidth (int trackRealWidth) {
		this.trackRealWidth = trackRealWidth;
	}
	
	public int getTrackRealHeight () {
		return trackRealHeight;
	}
	
	public void setTrackRealHeight (int trackRealHeight) {
		this.trackRealHeight = trackRealHeight;
	}
	
	public int getTrackno () {
		return trackno;
	}
	
	public void setTrackno (int trackno) {
		this.trackno = trackno;
	}
	
	public ModelTrack.TrackInfo getTrackInfo () {
		return trackInfo;
	}
	
	public void setTrackInfo (ModelTrack.TrackInfo trackInfo) {
		this.trackInfo = trackInfo;
	}
	
	public ArrayList<ModelMotorsAndTx.MotorAndTxInfo> getList () {
		return list;
	}
	
	public void setList (ArrayList<ModelMotorsAndTx.MotorAndTxInfo> list) {
		this.list = list;
	}
}
