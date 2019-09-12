package com.cn.jyz.ironshoes.recyclerview.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.emodel.EResponseCode;
import com.cn.jyz.ironshoes.model.ModelPollingRecord;
import com.cn.jyz.ironshoes.model.ModelPollingRecordList;
import com.cn.jyz.ironshoes.model.ModelTaskRecord;
import com.cn.jyz.ironshoes.recyclerview.adapter.MultipleItemAdapter;
import com.cn.jyz.ironshoes.recyclerview.bean.RecordItem;
import com.cn.jyz.ironshoes.utils.AsyncHttpHelper;
import com.cn.jyz.ironshoes.utils.Constant;
import com.cn.jyz.ironshoes.utils.GsonTools;
import com.cn.jyz.ironshoes.utils.ToastUtil;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ListFragment extends Fragment {
	
	private Context mContext = null;
	private int type = 0;
	
	/**服务器端一共多少条数据*/
	private static int TOTAL_COUNTER = 1000;
	
	/**每一页展示多少条数据*/
	private static final int REQUEST_COUNT = 10;
	
	/**已经获取到多少条数据了*/
	private static int mCurrentCounter = 0;
	
	private LRecyclerView mRecyclerView = null;
	
	private MultipleItemAdapter mMultipleItemAdapter = null;
	
	private PreviewHandler mHandler = new PreviewHandler(this);
	private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
	
	public ListFragment() {}
	
	public ListFragment(Context context, int type) {
		this.type = type;
		this.mContext = context;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pager_item,container,false);
		mRecyclerView = (LRecyclerView) view.findViewById(R.id.list);
		
		mMultipleItemAdapter = new MultipleItemAdapter(mContext);
		mLRecyclerViewAdapter = new LRecyclerViewAdapter(mMultipleItemAdapter);
		mRecyclerView.setAdapter(mLRecyclerViewAdapter);
		
		DividerDecoration divider = new DividerDecoration.Builder(mContext)
				.setHeight(R.dimen.dimen1)
				.setPadding(R.dimen.dimen4)
				.setColorResource(R.color.split)
				.build();
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(divider);
		
		mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
		
		mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				mMultipleItemAdapter.clear();
				mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
				mCurrentCounter = 0;
				requestData();
			}
		});
		
		mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				if (mCurrentCounter < TOTAL_COUNTER) {
					// loading more
					requestData();
				} else {
					//the end
					mRecyclerView.setNoMore(true);
				}
			}
		});
		
		mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
			
			@Override
			public void onScrollUp() {
			}
			
			@Override
			public void onScrollDown() {
			}
			
			
			@Override
			public void onScrolled(int distanceX, int distanceY) {
			}
			
			@Override
			public void onScrollStateChanged(int state) {
			
			}
			
		});
		
		mRecyclerView.refresh();
		
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private void notifyDataSetChanged() {
		mLRecyclerViewAdapter.notifyDataSetChanged();
	}
	
	private void addItems(ArrayList<RecordItem> list) {
		
		mMultipleItemAdapter.addAll(list);
		mCurrentCounter += list.size();
		
	}
	
	private class PreviewHandler extends Handler {
		
		private WeakReference<ListFragment> ref;
		
		PreviewHandler(ListFragment activity) {
			ref = new WeakReference<>(activity);
		}
		
		@Override
		public void handleMessage(Message msg) {
			final ListFragment activity = ref.get();
			if (activity == null) {
				return;
			}
			switch (msg.what) {
				
				case -1:
					
					int currentSize = activity.mMultipleItemAdapter.getItemCount();
					
					String jsonResponse = String.valueOf(msg.obj);
					
					ArrayList<RecordItem> newList = new ArrayList<>();
					
					if (type == RecordItem.TASK) {
						ModelTaskRecord task = GsonTools.getObjectData(jsonResponse, ModelTaskRecord.class);
						TOTAL_COUNTER = task.getCount();
						List<ModelTaskRecord.TaskRecord> tasks = task.getData();
						for (ModelTaskRecord.TaskRecord t : tasks) {
							RecordItem item = new RecordItem(type);
							item.setTask(t);
							
							newList.add(item);
						}
						
					} else if (type == RecordItem.INSPECTION) {
						final ModelPollingRecord pollingRecord = GsonTools.getObjectData(jsonResponse, ModelPollingRecord.class);
						if (pollingRecord.getCode() == EResponseCode.SUCCESS.getCode()) {
							List<ModelPollingRecord.PollingRecord> pollings = pollingRecord.getData();
							//TODO 数据处理，整理成HashMap<String, List> HashMap<patrolTimeFrame, List<ModelPollingRecord.PollingRecord>>
							
							HashMap<String, ModelPollingRecordList> hash = new HashMap<>();
							
							if (pollings.size() == 0) {
								mRecyclerView.setNoMore(true);
							} else {
								for (ModelPollingRecord.PollingRecord t : pollings) {
									String key = t.getPatrolDate() + " " + t.getPatrolTimeFrame();
									if (hash.containsKey(key)) {
										hash.get(key).getData().add(t);
									} else {
										ModelPollingRecordList pList = new ModelPollingRecordList();
										pList.setPatrolTimeFrame(t.getPatrolTimeFrame());
										pList.setPatrolDate(t.getPatrolDate());
										pList.getData().add(t);
										hash.put(key, pList);
									}
								}
								
								//重新组成集合
								for (String key : hash.keySet()) {
									RecordItem item = new RecordItem(type);
									item.setPollingList(hash.get(key));
									newList.add(item);
								}
							}
						} else if (pollingRecord.getCode() == EResponseCode.WRONG.getCode()) {
							((Activity) mContext).runOnUiThread(new Runnable() {
								@Override
								public void run () {
									ToastUtil.showShort(mContext, pollingRecord.getMsg());
								}
							});
						}
					}
					
					activity.addItems(newList);
					
					activity.mRecyclerView.refreshComplete(REQUEST_COUNT);
					activity.notifyDataSetChanged();
					break;
				case -2:
					activity.notifyDataSetChanged();
					break;
				case -3:
					activity.mRecyclerView.refreshComplete(REQUEST_COUNT);
					activity.notifyDataSetChanged();
					activity.mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
						@Override
						public void reload() {
							requestData();
						}
					});
					break;
				default:
					break;
			}
		}
	}
	
	
	/**
	 * 请求网络
	 */
	private void requestData() {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", mMultipleItemAdapter.getItemCount() / REQUEST_COUNT + 1);
		params.put("limit", REQUEST_COUNT);
		
		String api = this.type == RecordItem.TASK ? Constant.API_UNSLIPLEVEL : Constant.API_POLLINGRECORD;
		
		AsyncHttpHelper.getInstance().get(mContext, api, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				((Activity) mContext).runOnUiThread(new Runnable() {
					@Override
					public void run () {
						
						ToastUtil.showShort(mContext, arg0 + "");
					}
				});
			}
			
			
			@Override
			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
				Message msg = mHandler.obtainMessage();
				msg.what = -1;
				msg.obj = new String(arg2);
				mHandler.sendMessage(msg);
			}
		});
	}
}
