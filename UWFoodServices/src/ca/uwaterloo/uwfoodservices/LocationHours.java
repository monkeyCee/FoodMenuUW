package ca.uwaterloo.uwfoodservices;


import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;
import ca.uwaterloo.uwfoodservices.MenuLists.MenuFragment;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class LocationHours extends SlidingMenus implements ActionBar.TabListener, ActivityCommunicator{

	ViewPager vp;
	ActionBar actionBar;
	public FragmentCommunicator fragmentCommunicator;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    NetworkReceiver receiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_hours);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		receiver = new NetworkReceiver(this);
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setIcon(R.drawable.clock_icon);
		
		actionBar.setDisplayUseLogoEnabled(false);

		vp = (ViewPager) findViewById(R.id.pager);
		vp.setAdapter(new MenuAdapter(getSupportFragmentManager()));

		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) { }

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;
				default:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
			}

		});
		
		vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		for (int i = 0; i < 2; i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(MenuAdapter.location_tabs[i])
					.setTabListener(this));
		}

		vp.setCurrentItem(0);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		vp.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	public static class MenuAdapter extends FragmentPagerAdapter {

		private ArrayList<MenuFragment> mFragments;

		public final static String[] location_tabs = new String[] {"ListView", "MapView"};

		public MenuAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<MenuFragment>();
			for (int i = 0; i < location_tabs.length; i++)
				mFragments.add(new MenuFragment());
		}

		@Override
		public int getCount() {
			return location_tabs.length;
		}

		@Override
		public Fragment getItem(int position) {
			if(position == 0){
				Fragment fragment = new ListViewFragment();
				Bundle args = new Bundle();
				args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
				fragment.setArguments(args);
				return fragment;		
			}
			else{
				Fragment fragment = new MyMapFragment();
				Bundle args = new Bundle();
				args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
				fragment.setArguments(args);
				return fragment;
			}
			
		}

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (item.getTitle() == "Settings") {
			Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
			startActivity(settingsActivity);
			return true;
		} else if (itemId == android.R.id.home) {
			toggle();
			return true;
		} else if (item.getTitle() == "Refresh") {
		    
	          if(receiver.isNetwork()){
	                Intent intent = new Intent(LocationHours.this, SplashScreen.class);
	                editor = pref.edit();
	                editor.putString("refresh", "locations");
	                editor.commit();
	                startActivity(intent);
	                finish();
	            }
	            else{
	                Toast.makeText(getApplicationContext(), "Cannot refresh because either there is no network or the network does not match your preferences", Toast.LENGTH_SHORT).show();
	            }
	          
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

    @Override
    public void onResume() {
        super.onResume();
    }
    
	@Override
	public void passDataToActivity(int position) {
		fragmentCommunicator.passDataToFragment(position);
		
	}


}
