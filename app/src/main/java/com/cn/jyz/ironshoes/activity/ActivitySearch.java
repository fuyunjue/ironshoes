package com.cn.jyz.ironshoes.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.datepicker.common.DateRangePicker;
import com.cn.jyz.ironshoes.emodel.EResponseCode;
import com.cn.jyz.ironshoes.model.ModelSearchResult;
import com.cn.jyz.ironshoes.recyclerview.adapter.MultipleItemAdapter;
import com.cn.jyz.ironshoes.recyclerview.bean.RecordItem;
import com.cn.jyz.ironshoes.utils.AsyncHttpHelper;
import com.cn.jyz.ironshoes.utils.Constant;
import com.cn.jyz.ironshoes.utils.GsonTools;
import com.cn.jyz.ironshoes.utils.ToastUtil;
import com.codingending.library.FairySearchView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static com.cn.jyz.ironshoes.datepicker.common.DateRangePicker.YEAR_MONTH_DAY;

public class ActivitySearch extends AppCompatActivity implements View.OnClickListener {
	
	private Context mContext;
	
	private FairySearchView search_view;
	
	private Calendar calendar;
	
	private TextView tv_date;
	
	private static final String TAG = "lzx";
	
	/**服务器端一共多少条数据*/
	private int TOTAL_COUNTER = 64;
	
	/**每一页展示多少条数据*/
	private static final int REQUEST_COUNT = 10;
	
	/**已经获取到多少条数据了*/
	private static int mCurrentCounter = 0;
	
	private LRecyclerView mRecyclerView = null;
	
	private MultipleItemAdapter mMultipleItemAdapter = null;
	
	private PreviewHandler mHandler = new PreviewHandler(this);
	private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
	
	@Override
	protected void onCreate (@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		mContext = this;
		
		calendar = Calendar.getInstance();
		
		initView();
	}
	
	void initView() {
		search_view = (FairySearchView) findViewById(R.id.search_view);
		tv_date = (TextView) findViewById(R.id.tv_date);
		
		findViewById(R.id.iv_back).setOnClickListener(this);
		findViewById(R.id.ll_datepicker).setOnClickListener(this);
		
		search_view.setOnEnterClickListener(new FairySearchView.OnEnterClickListener() {
			@Override
			public void onEnterClick(String content) {
				//根据条件搜索
				String queryParameter = content == null ? "" : content;
				
				int startY = tv_date.getTag(R.string.date_start_year) == null ? year() : Integer.valueOf(tv_date.getTag(R.string.date_start_year).toString());
				int startM = tv_date.getTag(R.string.date_start_month) == null ? month() : Integer.valueOf(tv_date.getTag(R.string.date_start_month).toString());
				int startD = tv_date.getTag(R.string.date_start_day) == null ? day() : Integer.valueOf(tv_date.getTag(R.string.date_start_day).toString());
				int endY = tv_date.getTag(R.string.date_end_year) == null ? year() : Integer.valueOf(tv_date.getTag(R.string.date_end_year).toString());
				int endM = tv_date.getTag(R.string.date_end_month) == null ? month() : Integer.valueOf(tv_date.getTag(R.string.date_end_month).toString());
				int endD = tv_date.getTag(R.string.date_end_day) == null ? day() : Integer.valueOf(tv_date.getTag(R.string.date_end_day).toString());
				
				String startTime = startY + "-" + startM + "-" + startD;
				String endTime = endY + "-" + endM + "-" + endD;
				
				requestData(queryParameter, startTime, endTime);
			}
		});
		
		mRecyclerView = (LRecyclerView) findViewById(R.id.list);
		
		mMultipleItemAdapter = new MultipleItemAdapter(this);
		mLRecyclerViewAdapter = new LRecyclerViewAdapter(mMultipleItemAdapter);
		mRecyclerView.setAdapter(mLRecyclerViewAdapter);
		
		DividerDecoration divider = new DividerDecoration.Builder(this)
				.setHeight(R.dimen.dimen1)
				.setPadding(R.dimen.dimen1)
				.setColorResource(R.color.split)
				.build();
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(divider);
		
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
		
//		mLRecyclerViewAdapter.addHeaderView( new SampleHeader(this));
		
		mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				mMultipleItemAdapter.clear();
				mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
				mCurrentCounter = 0;
				
				//根据条件搜索
				doSearch();
			}
		});
		
		mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				if (mCurrentCounter < TOTAL_COUNTER) {
					doSearch();
					
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
	}
	
	private void doSearch () {
		//根据条件搜索
		String queryParameter = search_view.getSearchText() == null ? "" : search_view.getSearchText();
		
		int startY = tv_date.getTag(R.string.date_start_year) == null ? year() : Integer.valueOf(tv_date.getTag(R.string.date_start_year).toString());
		int startM = tv_date.getTag(R.string.date_start_month) == null ? month() : Integer.valueOf(tv_date.getTag(R.string.date_start_month).toString());
		int startD = tv_date.getTag(R.string.date_start_day) == null ? day() : Integer.valueOf(tv_date.getTag(R.string.date_start_day).toString());
		int endY = tv_date.getTag(R.string.date_end_year) == null ? year() : Integer.valueOf(tv_date.getTag(R.string.date_end_year).toString());
		int endM = tv_date.getTag(R.string.date_end_month) == null ? month() : Integer.valueOf(tv_date.getTag(R.string.date_end_month).toString());
		int endD = tv_date.getTag(R.string.date_end_day) == null ? day() : Integer.valueOf(tv_date.getTag(R.string.date_end_day).toString());
		
		String startTime = startY + "-" + startM + "-" + startD;
		String endTime = endY + "-" + endM + "-" + endD;
		
		requestData(queryParameter, startTime, endTime);
	}
	
	
	public void datePicker() {
		int startY = tv_date.getTag(R.string.date_start_year) == null ? year() : Integer.valueOf(tv_date.getTag(R.string.date_start_year).toString());
		int startM = tv_date.getTag(R.string.date_start_month) == null ? month() : Integer.valueOf(tv_date.getTag(R.string.date_start_month).toString());
		int startD = tv_date.getTag(R.string.date_start_day) == null ? day() : Integer.valueOf(tv_date.getTag(R.string.date_start_day).toString());
		int endY = tv_date.getTag(R.string.date_end_year) == null ? year() : Integer.valueOf(tv_date.getTag(R.string.date_end_year).toString());
		int endM = tv_date.getTag(R.string.date_end_month) == null ? month() : Integer.valueOf(tv_date.getTag(R.string.date_end_month).toString());
		int endD = tv_date.getTag(R.string.date_end_day) == null ? day() : Integer.valueOf(tv_date.getTag(R.string.date_end_day).toString());
		
		//初始化选择器
		DateRangePicker picker = new DateRangePicker(this, YEAR_MONTH_DAY, true);
		//选择器
		picker.setGravity(Gravity.CENTER);
		picker.setDateRangeStart(2010, 1, 1);
		picker.setDateRangeEnd(year(), month(), day() + 1);
		picker.setTextSize(16);
		picker.setSelectedItem(startY, startM, startD);
		picker.setSelectedSecondItem(endY, endM, endD);
		picker.setCancelText(R.string.datepicker_cancel);
		picker.setSubmitText(R.string.datepicker_sure);
		picker.setOnDatePickListener(new DateRangePicker.OnYearMonthDayDoublePickListener() {
			@Override
			public void onDatePicked(final String startYear,final String startMonth,final String startDay,final String endYear,final String endMonth,final String endDay) {
				final String dateStart = startYear + "-" + startMonth + "-" + startDay;
				final String dateEnd = endYear + "-" + endMonth + "-" + endDay;
				if (checkDate(startDay + "/" + startMonth + "/" + startYear, endDay + "/" + endMonth + "/" + endYear)) {
					runOnUiThread(new Runnable() {
						@Override
						public void run () {
							tv_date.setText(dateStart + "\t ~ \t" + dateEnd);
							
							tv_date.setTag(R.string.date_end_day, endDay);
							tv_date.setTag(R.string.date_end_month, endMonth);
							tv_date.setTag(R.string.date_end_year, endYear);
							tv_date.setTag(R.string.date_start_day, startDay);
							tv_date.setTag(R.string.date_start_month, startMonth);
							tv_date.setTag(R.string.date_start_year, startYear);
							doSearch();
						}
					});
				} else {
					runOnUiThread(new Runnable() {
						@Override
						public void run () {
							showToast("截止日期不能小于起始日期");
						}
					});
				}
			}
		});
		picker.show();
	}
	
	
	boolean checkDate(String start, String end) {
		try {
			if (start.equals(end)) {
				return true;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			if (endDate.after(startDate)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public void onClick (View view) {
		switch (view.getId()) {
			case R.id.iv_back:
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
				finish();
				break;
			case R.id.ll_datepicker:
				datePicker();
				break;
				default:
				break;
		}
	}
	
	private void notifyDataSetChanged() {
		mLRecyclerViewAdapter.notifyDataSetChanged();
	}
	
	private void addItems(ArrayList<RecordItem> list) {
		
		mMultipleItemAdapter.addAll(list);
		mCurrentCounter += list.size();
		
	}
	
	private class PreviewHandler extends Handler {
		
		private WeakReference<ActivitySearch> ref;
		
		PreviewHandler(ActivitySearch activity) {
			ref = new WeakReference<>(activity);
		}
		
		@Override
		public void handleMessage(Message msg) {
			final ActivitySearch activity = ref.get();
			if (activity == null || activity.isFinishing()) {
				return;
			}
			switch (msg.what) {
				
				case -1:
					String jsonResponse = String.valueOf(msg.obj);
					
					ArrayList<RecordItem> newList = new ArrayList<>();
					
					ModelSearchResult pollingRecord = GsonTools.getObjectData(jsonResponse, ModelSearchResult.class);
					
					if (pollingRecord.getCode() == EResponseCode.SUCCESS.getCode()) {
						TOTAL_COUNTER = pollingRecord.getCount();
						List<ModelSearchResult.SearchInfo> data = pollingRecord.getData();
						for (ModelSearchResult.SearchInfo t : data) {
							RecordItem item = new RecordItem(RecordItem.SEARCH);
							item.setSearchInfo(t);
							
							newList.add(item);
						}
						
						activity.addItems(newList);
						
						activity.mRecyclerView.refreshComplete(REQUEST_COUNT);
						activity.notifyDataSetChanged();
					} else if (pollingRecord.getCode() == EResponseCode.WRONG.getCode()) {
						ToastUtil.showShort(mContext, pollingRecord.getMsg());
					}
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
							
							doSearch();
						}
					});
					break;
				default:
					break;
			}
		}
	}
	
	/**
	 * 执行查询
	 *
	 * @param queryParameter
	 * @param startTime
	 * @param endTime
	 */
	private void requestData(String queryParameter, String startTime, String endTime) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", mMultipleItemAdapter.getItemCount() / REQUEST_COUNT + 1);
		params.put("limit", REQUEST_COUNT);
		params.put("queryParameter", queryParameter);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		
		AsyncHttpHelper.getInstance().get(mContext, Constant.API_SEARCH, params, new AsyncHttpResponseHandler() {
			
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
	
	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	private int year() {
		return calendar.get(Calendar.YEAR);
	}
	
	private int month() {
		return calendar.get(Calendar.MONTH);
	}
	
	private int day() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
}
