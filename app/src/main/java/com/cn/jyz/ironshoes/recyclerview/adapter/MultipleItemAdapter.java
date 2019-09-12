package com.cn.jyz.ironshoes.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.model.ModelPollingRecord;
import com.cn.jyz.ironshoes.model.ModelPollingRecordList;
import com.cn.jyz.ironshoes.model.ModelSearchResult;
import com.cn.jyz.ironshoes.recyclerview.base.SuperViewHolder;
import com.cn.jyz.ironshoes.recyclerview.bean.RecordItem;

import java.util.List;


public class MultipleItemAdapter extends BaseMultiAdapter<RecordItem> {

    private LayoutInflater inflater;
    
    public MultipleItemAdapter (Context context) {
        super(context);
        addItemType(RecordItem.TASK, R.layout.list_item_task);
        addItemType(RecordItem.INSPECTION, R.layout.list_item_inspection);
        addItemType(RecordItem.SEARCH, R.layout.list_item_search);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        RecordItem item = getDataList().get(position);
        switch (item.getItemType()) {
            case RecordItem.TASK:
                bindTaskItem(holder,item, position + 1);
                break;
            case RecordItem.INSPECTION:
                bindInspectionItem(holder,item, position + 1);
                break;
            case RecordItem.SEARCH:
                bindSearchItem(holder,item);
                break;
            default:
                break;
        }

    }
    
    String getXh(String position) {
        if (position.length() == 3) {
            return position;
        } else {
            position = "0" + position;
            return getXh(position);
        }
    }
    
    /**
     * 初始化作业记录item
     *
     * @param holder
     * @param item
     * @param position
     */
    private void bindTaskItem(SuperViewHolder holder, RecordItem item, int position) {
        TextView tv_xh = holder.getView(R.id.tv_xh);
        tv_xh.setText(String.format(mContext.getResources().getString(R.string.tv_xh), getXh(String.valueOf(position))));
    
        TextView tx_number = holder.getView(R.id.tx_number);
        tx_number.setText(String.format(mContext.getResources().getString(R.string.tx_number), item.getTask().getTxNumber()));
    
        TextView tv_location = holder.getView(R.id.tv_location);
        tv_location.setText(String.format(mContext.getResources().getString(R.string.tv_location), item.getTask().getLocation()));
    
        TextView tv_lefttxname = holder.getView(R.id.tv_lefttxname);
        tv_lefttxname.setText(String.format(mContext.getResources().getString(R.string.tv_lefttxname), item.getTask().getLeftTxName()));
    
        TextView tv_righttxname = holder.getView(R.id.tv_righttxname);
        tv_righttxname.setText(String.format(mContext.getResources().getString(R.string.tv_righttxname), item.getTask().getRightTxName()));
        
        TextView tv_executedate = holder.getView(R.id.tv_executedate);
        tv_executedate.setText(String.format(mContext.getResources().getString(R.string.tv_executedate), item.getTask().getExecuteDate(), item.getTask().getExecuteTime()));
        
        TextView tv_executor_examiner = holder.getView(R.id.tv_executor_examiner);
        tv_executor_examiner.setText(String.format(mContext.getResources().getString(R.string.tv_executor_examiner), item.getTask().getExecutor(), item.getTask().getExaminer()));
        
        TextView tv_dismantledate_dismantletime = holder.getView(R.id.tv_dismantledate_dismantletime);
        tv_dismantledate_dismantletime.setText(String.format(mContext.getResources().getString(R.string.tv_dismantledate_dismantletime), item.getTask().getDismantleDate(), item.getTask().getDismantleTime()));
    
        TextView tv_dismantleuser = holder.getView(R.id.tv_dismantleuser);
        tv_dismantleuser.setText(String.format(mContext.getResources().getString(R.string.tv_dismantleuser), item.getTask().getDismantleUser()));
    
        TextView tv_patroldate_patroltime = holder.getView(R.id.tv_patroldate_patroltime);
        tv_patroldate_patroltime.setText(String.format(mContext.getResources().getString(R.string.tv_patroldate_patroltime), item.getTask().getPatrolDate(), item.getTask().getPatrolTime()));
    
        TextView tv_patrolman = holder.getView(R.id.tv_patrolman);
        tv_patrolman.setText(String.format(mContext.getResources().getString(R.string.tv_patrolman), item.getTask().getPatrolman()));
    
        //TODO 巡检状态
        
    }
    
    /**
     * 初始化巡检记录item
     *
     * @param holder
     * @param item
     * @param position
     */
    private void bindInspectionItem(SuperViewHolder holder, RecordItem item, int position) {
        ModelPollingRecordList pList = item.getPollingList();
        TextView tv_xh = holder.getView(R.id.tv_xh);
        tv_xh.setText(String.format(mContext.getResources().getString(R.string.tv_xh), getXh(String.valueOf(position))));
        
        TextView tv_patrolate = holder.getView(R.id.tv_patrolate);
        tv_patrolate.setText(String.format(mContext.getResources().getString(R.string.tv_xh), pList.getPatrolDate()));
        
        TextView tv_patroltimeframe = holder.getView(R.id.tv_patroltimeframe);
        tv_patroltimeframe.setText(String.format(mContext.getResources().getString(R.string.tv_xh), pList.getPatrolDate()));
        
//        TextView tv_txnumber = holder.getView(R.id.tv_txnumber);
//        tv_txnumber.setText(String.format(mContext.getResources().getString(R.string.tv_txnumber), pList.getTrainNumber()));
//
//        TextView tv_location = holder.getView(R.id.tv_location);
//        tv_location.setText(String.format(mContext.getResources().getString(R.string.tv_location), pList.getLocation()));
    
        LinearLayout ll_tx_list = (LinearLayout) holder.getView(R.id.ll_tx_list);
        ll_tx_list.removeAllViews();
        //循环输入铁鞋编号信息
        List<ModelPollingRecord.PollingRecord> txList = pList.getData();
        for (ModelPollingRecord.PollingRecord pItem : txList) {
            View view = inflater.inflate(R.layout.item_plist, null);
    
            TextView tv_txcode = view.findViewById(R.id.tv_txcode);
            tv_txcode.setText(String.format(mContext.getResources().getString(R.string.tv_device_bm), pItem.getTxCode()));
            
            TextView tv_location = view.findViewById(R.id.tv_location);
            tv_location.setText(String.format(mContext.getResources().getString(R.string.tv_location), pItem.getLocation()));
    
            TextView tv_patrolman = view.findViewById(R.id.tv_patrolman);
            tv_patrolman.setText(String.format(mContext.getResources().getString(R.string.tv_location), pItem.getPatrolman()));
            
            TextView tv_txPatrolTime = view.findViewById(R.id.tv_txPatrolTime);
            tv_txPatrolTime.setText(String.format(mContext.getResources().getString(R.string.tv_xh), pItem.getTxPatrolTime()));
    
            TextView tv_rummager = view.findViewById(R.id.tv_rummager);
            tv_rummager.setText(String.format(mContext.getResources().getString(R.string.tv_rummager), pItem.getRummager()));
    
            ll_tx_list.addView(view);
        }
        
    }
    
//    /**
//     * 初始化巡检记录item
//     *
//     * @param holder
//     * @param item
//     * @param position
//     */
//    private void bindInspectionItem(SuperViewHolder holder, RecordItem item, int position) {
//        ModelPollingRecordList pList = item.getPollingList();
//        TextView tv_xh = holder.getView(R.id.tv_xh);
//        tv_xh.setText(String.format(mContext.getResources().getString(R.string.tv_xh), getXh(String.valueOf(position))));
//
//        TextView tv_patroltimeframe = holder.getView(R.id.tv_patroltimeframe);
//        tv_patroltimeframe.setText(String.format(mContext.getResources().getString(R.string.tv_xh), item.getPolling().getPatrolTimeFrame()));
//
//        TextView tv_patrolate = holder.getView(R.id.tv_patrolate);
//        tv_patrolate.setText(String.format(mContext.getResources().getString(R.string.tv_xh), item.getPolling().getPatrolDate()));
//
//        TextView tv_patrolman = holder.getView(R.id.tv_patrolman);
//        tv_patrolman.setText(String.format(mContext.getResources().getString(R.string.tv_patrolman), item.getPolling().getPatrolman()));
//
//        TextView tv_rummager = holder.getView(R.id.tv_rummager);
//        tv_rummager.setText(String.format(mContext.getResources().getString(R.string.tv_rummager), item.getPolling().getRummager()));
//
//        TextView tv_location = holder.getView(R.id.tv_location);
//        tv_location.setText(String.format(mContext.getResources().getString(R.string.tv_location), item.getPolling().getLocation()));
//
//        TextView tv_txnumber = holder.getView(R.id.tv_txnumber);
//        tv_txnumber.setText(String.format(mContext.getResources().getString(R.string.tv_txnumber), item.getPolling().getTrainNumber()));
//
//        TextView tv_lefttxcode = holder.getView(R.id.tv_lefttxcode);
//        tv_lefttxcode.setText(String.format(mContext.getResources().getString(R.string.tv_xh), item.getPolling().getTxCode()));
//
//        TextView tv_lefttxpatroltime = holder.getView(R.id.tv_lefttxpatroltime);
//        tv_lefttxpatroltime.setText(String.format(mContext.getResources().getString(R.string.tv_xh), item.getPolling().getTxPatrolTime()));
////
////        ImageView avatarImage = holder.getView(R.id.avatar_image);
////
////        textView.setText(item.getTitle());
////        avatarImage.setImageResource(R.mipmap.activity_login_logo);
//    }
    
    /**
     * 初始化搜索item
     *
     * @param holder
     * @param item
     */
    private void bindSearchItem(SuperViewHolder holder, RecordItem item) {
        ModelSearchResult.SearchInfo info = item.getSearchInfo();
        
        TextView tv_txcode = holder.getView(R.id.tv_txcode);
        tv_txcode.setText(String.format(mContext.getResources().getString(R.string.tv_device_bm), info.getTxCode()));
        
        TextView tv_location = holder.getView(R.id.tv_location);
        tv_location.setText(String.format(mContext.getResources().getString(R.string.tv_location), info.getLocation()));
    
        TextView tv_moveperiodtime = holder.getView(R.id.tv_moveperiodtime);
        tv_moveperiodtime.setText(String.format(mContext.getResources().getString(R.string.tv_xh), info.getMovePeriodTime()));
    
        TextView tv_moveperson_movegroup = holder.getView(R.id.tv_moveperson_movegroup);
        tv_moveperson_movegroup.setText(String.format(mContext.getResources().getString(R.string.tv_moveperson_movegroup), info.getMovePerson(), info.getMoveGroup()));
    
        TextView tv_trainnumber_search = holder.getView(R.id.tv_trainnumber_search);
        tv_trainnumber_search.setText(String.format(mContext.getResources().getString(R.string.tv_trainnumber_search), info.getTrainNumber()));
    
        TextView tv_orientation = holder.getView(R.id.tv_orientation);
        tv_orientation.setText(String.format(mContext.getResources().getString(R.string.tv_orientation), info.getOrientation()));
    
        TextView tv_removeperiodtime = holder.getView(R.id.tv_removeperiodtime);
        tv_removeperiodtime.setText(String.format(mContext.getResources().getString(R.string.tv_xh), info.getRemovePeriodTime()));
    
        TextView tv_removeperson = holder.getView(R.id.tv_removeperson);
        tv_removeperson.setText(String.format(mContext.getResources().getString(R.string.tv_removeperson), info.getRemovePerson()));
    
        TextView tv_patroltime = holder.getView(R.id.tv_patroltime);
        tv_patroltime.setText(String.format(mContext.getResources().getString(R.string.tv_xh), info.getPatrolTime()));
    
        TextView tv_patrolman = holder.getView(R.id.tv_patrolman);
        tv_patrolman.setText(String.format(mContext.getResources().getString(R.string.tv_patrolman), info.getPatrolman()));
        //TODO 巡检状态补充
        
    }

}
