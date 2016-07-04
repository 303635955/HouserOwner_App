package com.yunguo.houserowner.adpter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdpter  extends FragmentStatePagerAdapter{
	private List<Fragment> viewLists;
	
	public ViewPagerAdpter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.viewLists = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return viewLists.get(arg0);
	}

	@Override
	public int getCount() {
		return viewLists.size();
	}  

}
