package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 机车坐标
 */
public class ModelMotors extends ModelBase implements Serializable {
	
	
	private static final long serialVersionUID = 9105180969656274589L;
	
	private ArrayList<MotorInfo> list;
	
	public class MotorInfo {
		private String carId;	//机车主键
		private String trackId;	//股道主键（编号）
		private int trainIndex;	//在逻辑股道上的序号
		private String coordinateStartX;	//坐标开始X
		private String coordinateStartY;	//坐标开始Y
		private String coordinateEndX;	//坐标结束X
		private String coordinateEndY;	//坐标结束Y
		
		public String getCarId () {
			return carId;
		}
		
		public void setCarId (String carId) {
			this.carId = carId;
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
		
		public String getCoordinateStartX () {
			return coordinateStartX;
		}
		
		public void setCoordinateStartX (String coordinateStartX) {
			this.coordinateStartX = coordinateStartX;
		}
		
		public String getCoordinateStartY () {
			return coordinateStartY;
		}
		
		public void setCoordinateStartY (String coordinateStartY) {
			this.coordinateStartY = coordinateStartY;
		}
		
		public String getCoordinateEndX () {
			return coordinateEndX;
		}
		
		public void setCoordinateEndX (String coordinateEndX) {
			this.coordinateEndX = coordinateEndX;
		}
		
		public String getCoordinateEndY () {
			return coordinateEndY;
		}
		
		public void setCoordinateEndY (String coordinateEndY) {
			this.coordinateEndY = coordinateEndY;
		}
	}
	
	public ArrayList<MotorInfo> getList () {
		return list;
	}
	
	public void setList (ArrayList<MotorInfo> list) {
		this.list = list;
	}
}
