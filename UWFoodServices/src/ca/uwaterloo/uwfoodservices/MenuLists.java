package ca.uwaterloo.uwfoodservices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ca.uwaterloo.uwfoodservicesutility.RestarauntMenuHolder;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MenuLists extends SlidingMenus implements ActionBar.TabListener{
	
	public static String url = "http://api.uwaterloo.ca/public/v2/foodservices/2013/26/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
	
    ViewPager vp;
    String restaurant_selection;
    static int positionRestaurant;
    RestaurantLocationHolder holder = RestaurantLocationHolder.getInstance(getBaseContext());
    
    public static String formattedDate;
    static int weekDay;
    static Calendar calendar;
    static SimpleDateFormat simpleDateFormat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_lists);
		
		Intent intent = getIntent();
		restaurant_selection = intent.getStringExtra("Restaurant Name");
		positionRestaurant = intent.getIntExtra("Restaurant Position", 0);
		
		Log.d("Restaurant Selected", restaurant_selection);
		
		// Date handling
		calendar = Calendar.getInstance();
		Log.d(calendar.getTime() + "", "current time");
		
		simpleDateFormat = new SimpleDateFormat("MMMMMMMMM dd", Locale.CANADA);
		formattedDate = simpleDateFormat.format(calendar.getTime());
		
		String weekInYear = (new SimpleDateFormat("w", Locale.CANADA)).format(calendar.getTime());

		Log.d(formattedDate + "", "current time - formmated");
		
		weekDay = 0;
		if (calendar.getTime().toString().split(" ")[0].equals("Mon")) { weekDay = 0; }
		if (calendar.getTime().toString().split(" ")[0].equals("Tue")) { weekDay = 1; }
		if (calendar.getTime().toString().split(" ")[0].equals("Wed")) { weekDay = 2; }
		if (calendar.getTime().toString().split(" ")[0].equals("Thu")) { weekDay = 3; }
		if (calendar.getTime().toString().split(" ")[0].equals("Fri")) { weekDay = 4; }
		if (calendar.getTime().toString().split(" ")[0].equals("Sat")) { weekDay = 5; }
		if (calendar.getTime().toString().split(" ")[0].equals("Sun")) { weekDay = 6; }
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.setTitle(restaurant_selection);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(holder.image_map.get(restaurant_selection));

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
		
		for (int i = 0; i < 7; i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(MenuAdapter.days[i])
					.setTabListener(this));
		}

		vp.setCurrentItem(weekDay);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
	public static class MenuAdapter extends FragmentPagerAdapter {

		private ArrayList<MenuFragment> mFragments;

		public final static String[] days = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

		public MenuAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<MenuFragment>();
			for (int i = 0; i < days.length; i++)
				mFragments.add(new MenuFragment());
		}

		@Override
		public int getCount() {
			return days.length;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new MenuFragment();
			Bundle args = new Bundle();
			args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

	}

	public static class MenuFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public String day;
		private Context context;

		public static final int HDR_POS1 = 0;
	    public static int HDR_POS2 = 2;
	    
	    List<String> LIST = new ArrayList<String>();

	    private static final Integer LIST_HEADER = 0;
	    private static final Integer LIST_ITEM = 1;
		
		public MenuFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_restaurant_menu,
					container, false);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, getArguments().getInt(ARG_SECTION_NUMBER) - weekDay);
			
			simpleDateFormat = new SimpleDateFormat("MMMMMMMMM dd");
			
			if (simpleDateFormat.format(calendar.getTime()).split(" ")[1].startsWith("0")) {
				formattedDate = simpleDateFormat.format(calendar.getTime()).split(" ")[0] +
						" " + simpleDateFormat.format(calendar.getTime()).split(" ")[1].charAt(1);
			} else {
				formattedDate = simpleDateFormat.format(calendar.getTime());
			}
			
			if (formattedDate.endsWith("1")) { 
				formattedDate += "st"; 
			} else {
				if (formattedDate.endsWith("2")) { 
					formattedDate += "nd";
				} else {
					if (formattedDate.endsWith("3")) {
						formattedDate += "rd";
					} else { 
					formattedDate += "th"; 
					}
				}
			}
			
			RestarauntMenuHolder menuHolder = RestarauntMenuHolder.getInstance(null);
			
			Log.d((menuHolder.restaurantMenu.get(0).getMenu()[0].getDinner() == null) + "", "REST1");
			//Log.d(menuHolder.restaurantMenu.get(0).getMenu()[0].getDinner().equals(null) + "", "REST12");
			//Log.d(menuHolder.restaurantMenu.get(0).getMenu()[0].getDinner().equals(new DailyMenu(null, null).getDinner()) + "", "REST13");
			//Log.d(menuHolder.restaurantMenu.get(1).getMenu()[0].getLunch().get(0).getProductName(), "REST2");
			//Log.d(menuHolder.restaurantMenu.get(0).getMenu()[6].getLunch() + "", "REST3");
			
			TextView textDay = (TextView) rootView.findViewById(R.id.textDay);
			SpannableString content = new SpannableString(formattedDate);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			textDay.setText(content);
			
			ListView listView = (ListView) rootView.findViewById(R.id.list_menu);
			listView.setAdapter(new MenuListAdapter(getActivity()));
			
			LIST.clear();
			LIST.add("LUNCH");
			
			int day = getArguments().getInt(ARG_SECTION_NUMBER);
			
			/*
			for (int i = 0; i < menuHolder.restaurantMenu.size(); i++) {
				if (menuHolder.restaurantMenu.get(i).getRestaurant().equals("Bon Appetit")) {
					positionRestaurant = i;
				}
			}
			*/
			
			//Log.d(menuHolder.restaurantMenu.get(positionRestaurant).getMenu()[day].getLunch().size() + "", "getLunch");
			
			if (menuHolder.restaurantMenu.get(positionRestaurant).getMenu()[day].getLunch() == null) {
				LIST.add("There is nothing on the menu");
			} else {
				for (int i = 0; i < menuHolder.restaurantMenu.get(positionRestaurant).getMenu()[day].getLunch().size(); i++) {
					LIST.add(menuHolder.restaurantMenu.get(positionRestaurant).getMenu()[day].getLunch().get(i).getProductName());
				}
			}
			/*
			if (menuHolder.menuObject.get(positionRestaurant).getMenu().equals("null")) {
				LIST.add("There is nothing on the menu");
			} else {
				for (int i = 0; i < dwt.get(positionLunch).size() - 1; i ++) {
					Log.d(i+"", "size i");
					LIST.add(dwt.get(positionLunch).get(i).get(0));
					Log.d("yes", "size");
				}
			}*/
			HDR_POS2 = LIST.size();
			LIST.add("DINNER");
			
			if (menuHolder.restaurantMenu.get(positionRestaurant).getMenu()[day].getDinner() == null) {
				LIST.add("There is nothing on the menu");
			} else {
				for (int i = 0; i < menuHolder.restaurantMenu.get(positionRestaurant).getMenu()[day].getDinner().size(); i++) {
					LIST.add(menuHolder.restaurantMenu.get(positionRestaurant).getMenu()[day].getDinner().get(i).getProductName());
				}
			}
			return rootView;
		}
		
		@Override
		public void onResume() {
			//Log.d(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER) - 1),"result");
			super.onResume();
		}
		
		private class MenuListAdapter extends BaseAdapter {
	        public MenuListAdapter(Context context) {
	            mContext = context;
	        }

	        @Override
	        public int getCount() {
	            return LIST.size();
	        }

	        @Override
	        public boolean areAllItemsEnabled() {
	            return true;
	        }

	        @Override
	        public boolean isEnabled(int position) {
	            return true;
	        }

	        @Override
	        public Object getItem(int position) {
	            return position;
	        }

	        @Override
	        public long getItemId(int position) {
	            return position;
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {

	            String headerText = getHeader(position);
	            if(headerText != null) {

	                View item = convertView;
	                if(convertView == null || convertView.getTag() == LIST_ITEM) {

	                    item = LayoutInflater.from(mContext).inflate(
	                            R.layout.lv_header_layout, parent, false);
	                    item.setTag(LIST_HEADER);

	                }
	                Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
	        	            "Roboto-Bold.ttf");
	                TextView headerTextView = (TextView)item.findViewById(R.id.lv_list_hdr);
	                headerTextView.setTypeface(tf);
	                headerTextView.setText(headerText);
	                return item;
	            }

	            View item = convertView;
	            if(convertView == null || convertView.getTag() == LIST_HEADER) {
	                item = LayoutInflater.from(mContext).inflate(
	                        R.layout.lv_layout, parent, false);
	                item.setTag(LIST_ITEM);
	            }

	            TextView header = (TextView)item.findViewById(R.id.lv_item_header);
	            Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
	    	            "Roboto-Light.ttf");
	            header.setText(LIST.get(position % LIST.size()));
	            header.setTypeface(tf);

	            //Set last divider in a sublist invisible
	            View divider = item.findViewById(R.id.item_separator);
	            if(position == HDR_POS2 -1 || position == LIST.size() - 1) {
	                divider.setVisibility(View.INVISIBLE);
	            }
	            

	            return item;
	        }

	        private String getHeader(int position) {

	            if(position == HDR_POS1  || position == HDR_POS2) {
	                return LIST.get(position);
	            }

	            return null;
	        }

	        private final Context mContext;
	    }
	}
	

	@Override
	public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		vp.getAdapter().notifyDataSetChanged();
		vp.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		Log.d(item.getTitle() + "", "itemtostring");
		if (item.getTitle() == "Settings") {
			Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
			startActivity(settingsActivity);
			return true;
		} else if (itemId == android.R.id.home) {
			toggle();
			return true;
		} else if (item.getTitle() == "Refresh") {
			Log.d("load", "load");
			//loadPage();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}


