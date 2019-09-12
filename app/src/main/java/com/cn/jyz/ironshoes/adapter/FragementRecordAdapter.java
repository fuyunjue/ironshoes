package com.cn.jyz.ironshoes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragementRecordAdapter  extends FragmentStatePagerAdapter {
	
	List<Fragment> mFragments;
	List<String> mTitles;
	public FragementRecordAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
		super(fm);
		this.mFragments = fragments;
		this.mTitles = titles;
	}
	
	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}
	
	@Override
	public int getCount() {
		return mFragments.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles.get(position);
	}
}
