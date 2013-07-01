package ca.uwaterloo.uwfoodservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MenuLists extends SlidingMenus implements ActionBar.TabListener{
	
ViewPager vp;
String restaurant_selection;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_lists);
		
		Intent intent = getIntent();
		restaurant_selection = intent.getStringExtra("Restaurant Name");
		
		Log.d("Restaurant Selected", restaurant_selection);
		
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

		vp.setCurrentItem(0);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		try {
			dwt = new DownloadWebpageTask().execute().get();
			//Log.d(dwt.get(2).get(0), "result3");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String url = "http://api.uwaterloo.ca/public/v2/foodservices/2013/26/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
	private static final String TAG_META = "meta";
	private static final String TAG_MESSAGE = "message";
	
	private static final String TAG_DATA = "data";
	private static final String TAG_OUTLETS = "outlets";
	//private static final String TAG_BONAPPETIT = "BonAppetit";
	private static final String TAG_MENU = "menu";
	
	private static final String[] TAG_DAYS = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	private static final String TAG_MEALS = "meals";
	private static final String TAG_LUNCH = "lunch";
	private static final String TAG_DINNER = "dinner";
	
	private static final String TAG_PRODUCT_NAME = "product_name";
	private static final String TAG_PRODUCT_ID = "product_id";
	private static final String TAG_DIET_TYPE = "diet_type";
	private static final String TAG_RESULT = "result";
	
	//public final static String ITEM_TITLE = "title";
	//public final static String ITEM_CAPTION = "caption";

	static ArrayList<ArrayList<ArrayList<String>>> dwt;
	
	private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<ArrayList<ArrayList<String>>>> {
		
		public ArrayList<ArrayList<ArrayList<String>>> ParseDay() throws IOException, JSONException {
			
			// menuResult data structure:
			// 
			//             | mondayLunch      
			//             | mondayDinner     | Item #1      | product_name
			//             | tuesdayLunch ----| Item #2 -----| product_id
			//             | tuesdayDinner    | Item #3      | diet_type
			// menuResult -| wednesdayLunch
			//             | wednesdayDinner
			//             |     .
			//             |     .
			//             |     .
			
			ArrayList<ArrayList<ArrayList<String>>> menuResult = new ArrayList<ArrayList<ArrayList<String>>>();
			ArrayList<ArrayList<String>> mondayLunch = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> mondayDinner = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> tuesdayLunch = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> tuesdayDinner = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> wednesdayLunch = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> wednesdayDinner = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> thursdayLunch = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> thursdayDinner = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> fridayLunch = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> fridayDinner = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> saturdayLunch = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> saturdayDinner = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> sundayLunch = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> sundayDinner = new ArrayList<ArrayList<String>>();
			
			menuResult.add(mondayLunch);
			menuResult.add(mondayDinner);
			menuResult.add(tuesdayLunch);
			menuResult.add(tuesdayDinner);
			menuResult.add(wednesdayLunch);
			menuResult.add(wednesdayDinner);
			menuResult.add(thursdayLunch);
			menuResult.add(thursdayDinner);
			menuResult.add(fridayLunch);
			menuResult.add(fridayDinner);
			menuResult.add(saturdayLunch);
			menuResult.add(saturdayDinner);
			menuResult.add(sundayLunch);
			menuResult.add(sundayDinner);
			
			String TAG_RESTAURANT = "BonAppetit";
			
			// Creating JSON Parser instance
			JSONParser jParser = new JSONParser();
					
			// Getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			
			JSONObject meta = json.getJSONObject(TAG_META);
			
			//JSONObject message = meta.getJSONObject(TAG_MESSAGE);
			Log.d(meta.getString("message"), "message");
			
			JSONObject data = json.getJSONObject(TAG_DATA);
			JSONArray outlets = data.getJSONArray(TAG_OUTLETS);
			
			JSONObject Bon_Appetit = outlets.getJSONObject(1);
			JSONArray menu = Bon_Appetit.getJSONArray(TAG_MENU);
			
			JSONObject day;
			JSONObject meals;
			JSONArray lunch;
			JSONArray dinner;
			String product_name;
			String product_id;
			String diet_type;
			
			for (int i = 0; i < menuResult.size(); i++) {
				menuResult.get(i).add(new ArrayList<String>());
				menuResult.get(i).get(0).add("");
			}
			
			String weekDay;
			int position;
			
			for (int i = 0; i < menu.length(); i ++) {
				
				position = i;
				
				day = menu.getJSONObject(i);
				meals = day.getJSONObject(TAG_MEALS);
				weekDay = day.getString("day");
				
				if (weekDay == "Monday") { position = 0;}
				if (weekDay == "Tuesday") { position = 1;}
				if (weekDay == "Wednesday") { position = 2;}
				if (weekDay == "Thursday") { position = 3;}
				if (weekDay == "Friday") { position = 4;}
				if (weekDay == "Saturday") { position = 5;}
				if (weekDay == "Sunday") { position = 6;}
				
				// Lunch
				if (meals.getJSONArray(TAG_LUNCH).length() > 0) {
					lunch = meals.getJSONArray(TAG_LUNCH);
					for (int j = 0; j < lunch.length(); j ++) {
						product_name = lunch.getJSONObject(j).getString(TAG_PRODUCT_NAME);
						product_id = lunch.getJSONObject(j).getString(TAG_PRODUCT_ID);
						diet_type = lunch.getJSONObject(j).getString(TAG_DIET_TYPE);
						
						menuResult.get(2*position).add(new ArrayList<String>());
						menuResult.get(2*position).get(j).clear();
						menuResult.get(2*position).get(j).add(product_name);
						menuResult.get(2*position).get(j).add(product_id);
						menuResult.get(2*position).get(j).add(diet_type);
					}
				}
				// Dinner
				if (meals.getJSONArray(TAG_DINNER).length() > 0) {
					dinner = meals.getJSONArray(TAG_DINNER);
					for (int j = 0; j < dinner.length(); j ++) {
						product_name = dinner.getJSONObject(j).getString(TAG_PRODUCT_NAME);
						product_id = dinner.getJSONObject(j).getString(TAG_PRODUCT_ID);
						diet_type = dinner.getJSONObject(j).getString(TAG_DIET_TYPE);
					
						menuResult.get(2*position + 1).add(new ArrayList<String>());
						menuResult.get(2*position + 1).get(j).clear();
						menuResult.get(2*position + 1).get(j).add(product_name);
						menuResult.get(2*position + 1).get(j).add(product_id);
						menuResult.get(2*position + 1).get(j).add(diet_type);
					}
				}
			}
			return menuResult;
		}
		
		@Override
        protected ArrayList<ArrayList<ArrayList<String>>> doInBackground(String... params) {
            try {
            	Log.d(Integer.toString(params.length), "length");
                return ParseDay();
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<ArrayList<ArrayList<String>>> result) {
        	Log.d(result.get(2).get(0).get(0), "result2");
       }
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

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction arg1) {
		vp.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
	}
	
	public static class MenuFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public String day;

		public static final int HDR_POS1 = 0;
	    public static int HDR_POS2;
	    
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
			
			ListView listView = (ListView) rootView.findViewById(R.id.list_menu);
			listView.setAdapter(new MenuListAdapter(getActivity()));
			
			LIST.clear();
			LIST.add("LUNCH");
			
			int positionLunch = getArguments().getInt(ARG_SECTION_NUMBER)*2;
			int positionDinner = getArguments().getInt(ARG_SECTION_NUMBER)*2 + 1;
			
			if (dwt.get(positionLunch).get(0).get(0) != "") {
				Log.d(dwt.get(positionLunch) + "", "size");
				Log.d(dwt.get(positionLunch).get(0).get(0) + "", "size");
				Log.d(dwt.get(positionLunch).get(1).get(0) + "", "size");
				Log.d(dwt.get(positionLunch).get(2).get(0) + "", "size");
				for (int i = 0; i < dwt.get(positionLunch).size() - 1; i ++) {
					Log.d(i+"", "size i");
					LIST.add(dwt.get(positionLunch).get(i).get(0));
					Log.d("yes", "size");
				}
			} else {
				LIST.add("There is nothing on the menu");
			}
			
			HDR_POS2 = LIST.size();
			LIST.add("DINNER");
			
			if (dwt.get(positionDinner).get(0).get(0) != "") {
				for (int i = 0; i < dwt.get(positionDinner).size() - 1; i ++) {
					LIST.add(dwt.get(positionDinner).get(i).get(0));
				}
			} else {
				LIST.add("There is nothing on the menu");
			}
			
			/*TextView resultText = (TextView) rootView.findViewById(R.id.section_label);
			int positionLunch = getArguments().getInt(ARG_SECTION_NUMBER)*2;
			int positionDinner = getArguments().getInt(ARG_SECTION_NUMBER)*2 + 1;
			String textLunch = "Lunch: \n";
			String textDinner = "Dinner: \n";
			if (dwt.get(positionLunch).get(0).get(0) != "") {
				for (int i = 0; i < dwt.get(positionLunch).size(); i ++) {
					for (int j = 0; j < dwt.get(positionLunch).get(i).size(); j ++) {
						textLunch += dwt.get(positionLunch).get(i).get(j) + " ";
					}
					textLunch += "\n";
				}
			} else {
				textLunch += "There is nothing on the menu. \n";
			}
			if (dwt.get(positionDinner).get(0).get(0) != "") {
				for (int i = 0; i < dwt.get(positionDinner).size(); i ++) {
					for (int j = 0; j < dwt.get(positionDinner).get(i).size(); j ++) {
						textDinner += dwt.get(positionDinner).get(i).get(j) + " ";
					}
					textDinner += "\n";
				}
			} else {
				textDinner += "There is nothing on the menu. \n";
			}
			resultText.setText(textLunch + textDinner);
			*/
			/* if (dwt.get((getArguments().getInt(ARG_SECTION_NUMBER)-1) * 2).get(0) != "") {
				String resultString = dwt.get((getArguments().getInt(ARG_SECTION_NUMBER)-1) * 2).get(0) + "\n" + 
						dwt.get((getArguments().getInt(ARG_SECTION_NUMBER)-1) * 2 + 1).get(0);
				resultText.setText(resultString);
			} else {
				resultText.setText("There is nothing on the menu.");
			}
			*/
			
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

	                TextView headerTextView = (TextView)item.findViewById(R.id.lv_list_hdr);
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
	            header.setText(LIST.get(position % LIST.size()));

	            //Set last divider in a sublist invisible
	            View divider = item.findViewById(R.id.item_separator);
	            if(position == HDR_POS2 -1) {
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

	@Override
	public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}


