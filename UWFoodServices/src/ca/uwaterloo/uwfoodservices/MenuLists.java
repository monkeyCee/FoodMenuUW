package ca.uwaterloo.uwfoodservices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MenuLists extends SlidingMenus implements ActionBar.TabListener{
	
	public static String url = "http://api.uwaterloo.ca/public/v2/foodservices/2013/26/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
	
	// Network Verification
	public static final String WIFI = "Wi-Fi Only";
    public static final String BOTH = "Both Wi-Fi and Data";
	
    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;

    // The user's current network preference setting.
    public static String sPref = null;
    
    // The BroadcastReceiver that tracks network connectivity changes.
    private NetworkReceiver receiver = new NetworkReceiver();
    
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // Checks the user prefs and the network connection. Based on the result, decides
            // whether to refresh the display or keep the current display.
            // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
            if (WIFI.equals(sPref) && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // If device has its Wi-Fi connection, sets refreshDisplay
                // to true. This causes the display to be refreshed when the user
                // returns to the app.
                refreshDisplay = true;
                Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();

                // If the setting is ANY network and there is a network connection
                // (which by process of elimination would be mobile), sets refreshDisplay to true.
            } else if (BOTH.equals(sPref) && networkInfo != null) {
                refreshDisplay = true;

                // Otherwise, the app can't download content--either because there is no network
                // connection (mobile or Wi-Fi), or because the pref setting is WIFI, and there
                // is no Wi-Fi connection.
                // Sets refreshDisplay to false.
            } else {
                refreshDisplay = false;
                Toast.makeText(context, R.string.lost_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    ViewPager vp;
    String restaurant_selection;
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
		
		//Log.d("Restaurant Selected", restaurant_selection);
		
		// Date handling
		calendar = Calendar.getInstance();
		Log.d(calendar.getTime() + "", "current time");
		
		simpleDateFormat = new SimpleDateFormat("MMMMMMMMM dd");
		formattedDate = simpleDateFormat.format(calendar.getTime());
		
		String weekInYear = (new SimpleDateFormat("w")).format(calendar.getTime());

		Log.d(formattedDate + "", "current time - formmated");
		
		weekDay = 0;
		if (calendar.getTime().toString().split(" ")[0].equals("Mon")) { weekDay = 0; }
		if (calendar.getTime().toString().split(" ")[0].equals("Tue")) { weekDay = 1; }
		if (calendar.getTime().toString().split(" ")[0].equals("Wed")) { weekDay = 2; }
		if (calendar.getTime().toString().split(" ")[0].equals("Thu")) { weekDay = 3; }
		if (calendar.getTime().toString().split(" ")[0].equals("Fri")) { weekDay = 4; }
		if (calendar.getTime().toString().split(" ")[0].equals("Sat")) { weekDay = 5; }
		if (calendar.getTime().toString().split(" ")[0].equals("Sun")) { weekDay = 6; }
		
		Log.d(Integer.parseInt(weekInYear) + "", "current time - weekInYear");
		
		url = "http://api.uwaterloo.ca/public/v2/foodservices/2013/" + Integer.parseInt(weekInYear) + "/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.setTitle(restaurant_selection);
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
		
		for (int i = 0; i < 7; i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(MenuAdapter.days[i])
					.setTabListener(this));
		}

		vp.setCurrentItem(weekDay);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		// Register BroadcastReceiver to track connection changes.
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkReceiver();
		this.registerReceiver(receiver, filter);
	}
	
	// Refreshes the display if the network connection and the
    // pref settings allow it.
    @Override
    public void onStart() {
        super.onStart();

        // Gets the user's network preference settings
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieves a string value for the preferences. The second parameter
        // is the default value to use if a preference value is not found.
        sPref = sharedPrefs.getString("connection_type_preference", "Both Wi-Fi and Data");

        updateConnectedFlags();

        // Only loads the page if refreshDisplay is true. Otherwise, keeps previous
        // display. For example, if the user has set "Wi-Fi only" in prefs and the
        // device loses its Wi-Fi connection midway through the user using the app,
        // you don't want to refresh the display--this would force the display of
        // an error page instead of the menu content.
        /*
        if (refreshDisplay) {
            loadPage();
        }*/
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	vp.getAdapter().notifyDataSetChanged();

    	IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);
        
        updateConnectedFlags();
        Log.d(refreshDisplay + "", "network refresh resume");
        if (refreshDisplay) {
            loadPage();
        }
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    // Checks the network connection and sets the wifiConnected and mobileConnected
    // variables accordingly.
    private void updateConnectedFlags() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null) {
        	Log.d(activeInfo.isConnected() + "", "network connected");
        } else {
        	Log.d(null + "", "network connected");
        }
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }
    
    // Uses AsyncTask subclass to download the JSON data. This avoids UI lock up. 
    // To prevent network operations from causing a delay that results in a poor 
    // user experience, always perform network operations on a separate thread from the UI.
    private void loadPage() {
    	Log.d(sPref, "network");
    	Log.d(BOTH, "network");
    	Log.d(wifiConnected + "", "network");
    	Log.d(mobileConnected + "", "network");
        if (((sPref.equals(BOTH)) && (wifiConnected || mobileConnected))
                || ((sPref.equals(WIFI)) && (wifiConnected))) {
        	// Load Page
        } else {
            showErrorPage();
        }
    }
    
    // Displays an error if the app is unable to load content.
    private void showErrorPage() {
        setContentView(R.layout.activity_menu_lists);

        // The specified network connection is not available. Displays error message.
        // Show: "Unable to load content. Check your network connection."
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
			
			Log.d(menuHolder.restaurantMenu.get(0).getMenu()[0].getLunch() + "", "getRestaurant");
			
			TextView textDay = (TextView) rootView.findViewById(R.id.textDay);
			SpannableString content = new SpannableString(formattedDate);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			textDay.setText(content);
			
			ListView listView = (ListView) rootView.findViewById(R.id.list_menu);
			listView.setAdapter(new MenuListAdapter(getActivity()));
			
			LIST.clear();
			LIST.add("LUNCH");
			
			int day = getArguments().getInt(ARG_SECTION_NUMBER);
			int positionRestaurant = 5;
			
			for (int i = 0; i < menuHolder.restaurantMenu.size(); i++) {
				if (menuHolder.restaurantMenu.get(i).getRestaurant().equals("Bon Appetit")) {
					positionRestaurant = i;
				}
			}
			
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
			loadPage();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}


