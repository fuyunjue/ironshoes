package com.cn.jyz.ironshoes.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class TabFragmentAdapter extends FragmentStatePagerAdapter {
	
	private String[] titles;
	private List<Fragment> fragments;
	public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
		super(fm);
		this.titles=titles;
		this.fragments=fragmentList;
	}
	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
	
	@Override
	public int getCount() {
		return fragments.size();
	}
	
	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
}
