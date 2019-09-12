package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelTrack extends ModelBase implements Serializable {
	
	private static final long serialVersionUID = -7360154947298915434L;
	
	
	private String aspectratioX;    //屏幕纵横比X
	private String aspectratioY;    //屏幕纵横比Y
	private String offsetTop;       //上偏移量
	private String offsetLeft;       //左偏移量
	private String svgContentWidth;       //矢量图标准比例下宽度
	private String svgContentHeight;       //矢量图标准比例下高度
	
	
	private ArrayList<TrackInfo> tracks;    //股道集合	包括该站点的股道集合
	
	
	public class TrackInfo {
		public String trackno;  //股道编号	返回股道编号，如1
		public String capacity;  //股道容量	描述该股道可停放多少辆车厢
		public String startX;  //起始点X坐标
		public String startY;  //起始点Y坐标
		public String endX;  //结束点X坐标
		public String endY;  //结束点Y坐标
		
		public String getTrackno () {
			return trackno;
		}
		
		public void setTrackno (String trackno) {
			this.trackno = trackno;
		}
		
		public String getCapacity () {
			return capacity;
		}
		
		public void setCapacity (String capacity) {
			this.capacity = capacity;
		}
		
		public String getStartX () {
			return startX;
		}
		
		public void setStartX (String startX) {
			this.startX = startX;
		}
		
		public String getStartY () {
			return startY;
		}
		
		public void setStartY (String startY) {
			this.startY = startY;
		}
		
		public String getEndX () {
			return endX;
		}
		
		public void setEndX (String endX) {
			this.endX = endX;
		}
		
		public String getEndY () {
			return endY;
		}
		
		public void setEndY (String endY) {
			this.endY = endY;
		}
	}
	
	public String getOffsetTop () {
		return offsetTop;
	}
	
	public void setOffsetTop (String offsetTop) {
		this.offsetTop = offsetTop;
	}
	
	public String getOffsetLeft () {
		return offsetLeft;
	}
	
	public void setOffsetLeft (String offsetLeft) {
		this.offsetLeft = offsetLeft;
	}
	
	public String getSvgContentWidth () {
		return svgContentWidth;
	}
	
	public void setSvgContentWidth (String svgContentWidth) {
		this.svgContentWidth = svgContentWidth;
	}
	
	public String getSvgContentHeight () {
		return svgContentHeight;
	}
	
	public void setSvgContentHeight (String svgContentHeight) {
		this.svgContentHeight = svgContentHeight;
	}
	
	public String getAspectratioX () {
		return aspectratioX;
	}
	
	public void setAspectratioX (String aspectratioX) {
		this.aspectratioX = aspectratioX;
	}
	
	public String getAspectratioY () {
		return aspectratioY;
	}
	
	public void setAspectratioY (String aspectratioY) {
		this.aspectratioY = aspectratioY;
	}
	
	public ArrayList<TrackInfo> getTracks () {
		return tracks;
	}
	
	public void setTracks (ArrayList<TrackInfo> tracks) {
		this.tracks = tracks;
	}
}
