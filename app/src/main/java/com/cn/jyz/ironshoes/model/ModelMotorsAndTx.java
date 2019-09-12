package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 机车、铁鞋坐标
 */
public class ModelMotorsAndTx extends ModelBase implements Serializable {
	
	
	private static final long serialVersionUID = 9105180969656274589L;
	
	private ArrayList<MotorAndTxInfo> list;
	
	public class MotorAndTxInfo {
		private int fId	;	//	唯一标识（主键）
		private String fNo	;	//	编号
		private String fName	;	//	名称	-1=左铁鞋，-2=右铁鞋
		private String trackId	;	//	股道主键（编号）	不是逻辑股道
		private int trainIndex	;	//	序号	机车或铁鞋在股道上的序号
		private int fType	;	//	数据标志	1-机车，2-铁鞋
		private float coordinateStartX	;	//	坐标开始X
		private float floatcoordinateStartY	;	//	坐标开始Y
		private float floatcoordinateEndX	;	//	坐标结束X
		private float floatcoordinateEndY	;	//	坐标结束Y
		private String fmac;    //铁鞋mac地址
		
		private String state;   //
		private String stateStr;
		private String trainType;
		private String trainNumber;
		
		public String getState () {
			return state;
		}
		
		public void setState (String state) {
			this.state = state;
		}
		
		public String getStateStr () {
			return stateStr;
		}
		public String getTrainType () {
			return trainType;
		}
		public String getTrainNumber () {
			return trainNumber;
		}
		
		public void setStateStr (String stateStr) {
			this.stateStr = stateStr;
		}
		
		public int getfId () {
			return fId;
		}
		
		public void setfId (int fId) {
			this.fId = fId;
		}
		
		public String getfNo () {
			return fNo;
		}
		
		public void setfNo (String fNo) {
			this.fNo = fNo;
		}
		
		public String getfName () {
			return fName;
		}
		
		public void setfName (String fName) {
			this.fName = fName;
		}
		
		public String getTrackId () {
			return trackId;
		}
		
		public void setTrackId (String trackId) {
			this.trackId = trackId;
		}
		
		public int getTrainIndex () {
			return trainIndex;
		}
		
		public void setTrainIndex (int trainIndex) {
			this.trainIndex = trainIndex;
		}
		
		public int getfType () {
			return fType;
		}
		
		public void setfType (int fType) {
			this.fType = fType;
		}
		public void SetTrainType (String trainType) {
			this.trainType = trainType;
		}
		public void SetTrainNumber (String trainNumber) {
			this.trainNumber = trainNumber;
		}
		
		public float getCoordinateStartX () {
			return coordinateStartX;
		}
		
		public void setCoordinateStartX (float coordinateStartX) {
			this.coordinateStartX = coordinateStartX;
		}
		
		public float getFloatcoordinateStartY () {
			return floatcoordinateStartY;
		}
		
		public void setFloatcoordinateStartY (float floatcoordinateStartY) {
			this.floatcoordinateStartY = floatcoordinateStartY;
		}
		
		public float getFloatcoordinateEndX () {
			return floatcoordinateEndX;
		}
		
		public void setFloatcoordinateEndX (float floatcoordinateEndX) {
			this.floatcoordinateEndX = floatcoordinateEndX;
		}
		
		public float getFloatcoordinateEndY () {
			return floatcoordinateEndY;
		}
		
		public void setFloatcoordinateEndY (float floatcoordinateEndY) {
			this.floatcoordinateEndY = floatcoordinateEndY;
		}
		
		public String getFmac () {
			return fmac;
		}
		
		public void setFmac (String fmac) {
			this.fmac = fmac;
		}
	}
	
	public ArrayList<MotorAndTxInfo> getList () {
		return list;
	}
	
	public void setList (ArrayList<MotorAndTxInfo> list) {
		this.list = list;
	}
}
