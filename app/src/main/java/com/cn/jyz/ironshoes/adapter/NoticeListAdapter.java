package com.cn.jyz.ironshoes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.emodel.ENoticeType;
import com.cn.jyz.ironshoes.model.ModelMqtt;

import java.util.List;

/**
 * Created by Lzx on 2016/12/30.
 */

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {

    private Context mContext;
    private List<ModelMqtt> mqttList;
    private View.OnClickListener viewClickListener;
    public NoticeListAdapter (Context context, List<ModelMqtt> mqttList, View.OnClickListener viewClickListener) {
        super();
        this.mqttList = mqttList;
        this.mContext = context;
        this.viewClickListener = viewClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_main_notice, null), mContext);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mqttList.get(position), position, viewClickListener);
    }

    public void addItem(ModelMqtt mqtt) {
        mqttList.add(mqtt);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (null != mqttList.get(position)) {
            mqttList.remove(position);
            this.notifyItemRemoved(position);
            if (position != mqttList.size()) {
                this.notifyItemRangeChanged(position, mqttList.size() - position);
            }
        }
    }

    @Override
    public int getItemCount () {
        return mqttList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        Context context;

        public ViewHolder(View v, Context context) {
            super(v);
            this.view = v;
            this.context = context;
        }

        public void bind(@NonNull ModelMqtt model, int position, View.OnClickListener viewClickListener) {
            int layoutId = R.layout.item_notice_sf;
            int id = Integer.valueOf(model.getType() + "");
            if (ENoticeType.TYPE_1.getCode() == id) {
                layoutId = R.layout.item_notice_sf;
            } else if (ENoticeType.TYPE_2.getCode() == id) {
                layoutId = R.layout.item_notice_xj;
            } else if (ENoticeType.TYPE_3.getCode() == id) {
                layoutId = R.layout.item_notice_gz;
            }

            View item = LayoutInflater.from(context).inflate(layoutId, null);
            item.findViewById(R.id.rl_close).setTag(position);
            item.findViewById(R.id.rl_close).setOnClickListener(viewClickListener);
            ((TextView) item.findViewById(R.id.tv_title)).setText(model.getTitle() + "");
            ((TextView) item.findViewById(R.id.tv_txno)).setText(String.format(context.getResources().getString(R.string.tv_notice_txno), model.getTxno()));
            ((TextView) item.findViewById(R.id.tv_content)).setText(String.format(context.getResources().getString(R.string.tv_notice_content), model.getContent()));

            ((LinearLayout) view.findViewById(R.id.ll_notice)).removeAllViews();
            ((LinearLayout) view.findViewById(R.id.ll_notice)).addView(item);
        }
    }
}
