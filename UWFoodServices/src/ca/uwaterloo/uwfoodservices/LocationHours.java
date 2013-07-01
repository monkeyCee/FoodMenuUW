package ca.uwaterloo.uwfoodservices;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class LocationHours extends FragmentActivity implements
		ActionBar.TabListener {
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_hours);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.location_hours, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
	    		if(position == 0){
	    			Fragment fragment = new MyMapFragment();
	    			Bundle args = new Bundle();
		    		args.putInt(MyMapFragment.ARG_SECTION_NUMBER, position);
		    		fragment.setArguments(args);
		    		return fragment;	
	    		}
	    		else{
	    			Fragment fragment = new ListViewFragment();
	    			Bundle args = new Bundle();
		    		args.putInt(ListViewFragment.ARG_SECTION_NUMBER, position);
		    		fragment.setArguments(args);
		    		return fragment;
	    		}
	    				
			}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_map).toUpperCase(l);
			case 1:
				return getString(R.string.title_list).toUpperCase(l);
			}
			return null;
		}
	}

	public static class MyMapFragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		GoogleMap myMap;
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = null;
			  try {
			        view = inflater.inflate(
							R.layout.fragment_map, container, false);
			          SupportMapFragment mySupportMapFragment 
			           = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
			             myMap = mySupportMapFragment.getMap();   
			             
			             if(myMap != null){
			            	 myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			             }
			             
			    } catch (InflateException e) {}
			 return view;
		}
	}
	
	public static class ListViewFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";
		private ListView listView;
		private Context context;
		
		public void onAttach(Activity activity){
	        super.onAttach(activity);
	        context = getActivity();
	      }

		 @Override
		    public void onActivityCreated(Bundle savedInstanceState) {
		     super.onActivityCreated(savedInstanceState);
		     init();
		    }
		 
		 public void init() {
		     listView.setAdapter(new ImageAdapter(context, -1));
		   }
		 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = null;
			  try {
			        view = inflater.inflate(
							R.layout.activity_restaurant_menu_list, container, false);
			        listView = (ListView) view.findViewById(R.id.list_restaurant);
			    } catch (InflateException e) {}
			 return view;
		}
	}

}
