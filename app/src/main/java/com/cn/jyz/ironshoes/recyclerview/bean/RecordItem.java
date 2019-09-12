package com.cn.jyz.ironshoes.recyclerview.bean;

import com.cn.jyz.ironshoes.model.ModelPollingRecord;
import com.cn.jyz.ironshoes.model.ModelPollingRecordList;
import com.cn.jyz.ironshoes.model.ModelSearchResult;
import com.cn.jyz.ironshoes.model.ModelTaskRecord;

public class RecordItem implements RecordEntity {
    private int itemType;
    private String title;
    
    public static final int SEARCH = 9;
    public static final int INSPECTION = 10;    //巡检记录
    public static final int TASK = 11;      //作业记录
    
    private ModelTaskRecord.TaskRecord task;
    private ModelPollingRecord.PollingRecord polling;
    private ModelSearchResult.SearchInfo searchInfo;
    private ModelPollingRecordList pollingList;
    
    
    public ModelPollingRecordList getPollingList () {
        return pollingList;
    }
    
    public void setPollingList (ModelPollingRecordList pollingList) {
        this.pollingList = pollingList;
    }
    
    public ModelTaskRecord.TaskRecord getTask () {
        return task;
    }
    
    public void setTask (ModelTaskRecord.TaskRecord task) {
        this.task = task;
    }
    
    public ModelPollingRecord.PollingRecord getPolling () {
        return polling;
    }
    
    public void setPolling (ModelPollingRecord.PollingRecord polling) {
        this.polling = polling;
    }
    
    public RecordItem (int itemType) {
        this.itemType = itemType;
    }
    
    public ModelSearchResult.SearchInfo getSearchInfo () {
        return searchInfo;
    }
    
    public void setSearchInfo (ModelSearchResult.SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }
    
    @Override
    public int getItemType() {
        return itemType;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
