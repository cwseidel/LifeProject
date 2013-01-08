package com.sim.lifeproject;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Button;

public class MyFragmentsAdapter extends FragmentPagerAdapter{

	List<Fragment> fragments = null;
	public MyFragmentsAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		
	}
	public void addFragment(Fragment fragment){
		fragments.add(fragment);
	}
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}
	@Override
	public int getCount() {
		return fragments.size();
	}
}
