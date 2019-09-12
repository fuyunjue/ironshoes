package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelTiexies extends ModelBase implements Serializable {
	
	
	private static final long serialVersionUID = 6438298859902719564L;
	
	private ArrayList<TiexieInfo> list;
	
	public ArrayList<TiexieInfo> getList () {
		return list;
	}
	
	public void setList (ArrayList<TiexieInfo> list) {
		this.list = list;
	}
	
	public class TiexieInfo {
		private Integer txId;//铁鞋主键
		private String txno;//铁鞋编号
		private String txName;//铁鞋名称	-1=左铁鞋，-2=右铁鞋
		private String trackId;//股道主键（编号）	不是逻辑
		private Integer trainIndex;//序号	逻辑股道上的序号
		private Integer coordinateStartX;//坐标开始X
		private Integer coordinateStartY;//坐标开始Y
		private Integer coordinateEndX;//坐标结束X
		private Integer coordinateEndY;//坐标结束Y
		
		public Integer getTxId () {
			return txId;
		}
		
		public void setTxId (Integer txId) {
			this.txId = txId;
		}
		
		public String getTxno () {
			return txno;
		}
		
		public void setTxno (String txno) {
			this.txno = txno;
		}
		
		public String getTxName () {
			return txName;
		}
		
		public void setTxName (String txName) {
			this.txName = txName;
		}
		
		public String getTrackId () {
			return trackId;
		}
		
		public void setTrackId (String trackId) {
			this.trackId = trackId;
		}
		
		public Integer getTrainIndex () {
			return trainIndex;
		}
		
		public void setTrainIndex (Integer trainIndex) {
			this.trainIndex = trainIndex;
		}
		
		public Integer getCoordinateStartX () {
			return coordinateStartX;
		}
		
		public void setCoordinateStartX (Integer coordinateStartX) {
			this.coordinateStartX = coordinateStartX;
		}
		
		public Integer getCoordinateStartY () {
			return coordinateStartY;
		}
		
		public void setCoordinateStartY (Integer coordinateStartY) {
			this.coordinateStartY = coordinateStartY;
		}
		
		public Integer getCoordinateEndX () {
			return coordinateEndX;
		}
		
		public void setCoordinateEndX (Integer coordinateEndX) {
			this.coordinateEndX = coordinateEndX;
		}
		
		public Integer getCoordinateEndY () {
			return coordinateEndY;
		}
		
		public void setCoordinateEndY (Integer coordinateEndY) {
			this.coordinateEndY = coordinateEndY;
		}
	}
}
