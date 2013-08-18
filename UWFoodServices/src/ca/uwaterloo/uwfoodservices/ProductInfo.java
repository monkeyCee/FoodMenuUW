package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;
import ca.uwaterloo.uwfoodservicesutility.ParseProductInfo;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class ProductInfo extends SlidingMenus implements ActionBar.TabListener{

    ViewPager vp;
    String restaurantSelection;
    long productDay;
    int currentPosition;
    ArrayList<Integer> productIds;
    
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    NetworkReceiver receiver;
    
    static ParseProductInfo productInfoParser;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        
        receiver = new NetworkReceiver(this);
        
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        restaurantSelection = intent.getStringExtra("Restaurant Name");
        productDay = intent.getLongExtra("Product Day", -1);
        currentPosition = intent.getIntExtra("Current Position", -1);
        productIds = intent.getIntegerArrayListExtra("Product Ids");

        List<String> productInfoUrls = new ArrayList<String>();
        for (int id:productIds) {
                productInfoUrls.add("http://api.uwaterloo.ca/public/v2/foodservices/product/" + id
                + ".json?key=98bbbd30b3e4f621d9cb544a790086d6");
        }
        
        productInfoParser = new ParseProductInfo();
        new AsyncDataFetcher(this).execute(productInfoUrls);
        
        
        
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setTitle(restaurantSelection);
        actionBar.setIcon(R.drawable.ic_drawer);
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
        
        for (int i = 0; i < MenuAdapter.days.length; i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(MenuAdapter.days[i])
                    .setTabListener(this));
        }

        //vp.setCurrentItem(weekDay);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }
    
    private static class AsyncDataFetcher extends AsyncTask<List<String>, Void, List<JSONObject>> {

        Context context;

        public AsyncDataFetcher(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {             
        }; 

        @Override
        protected List<JSONObject> doInBackground(List<String>... urls) {
            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            JSONParser json_parse = new JSONParser();
            for (String url : urls[0]) {
                jsonList.add(json_parse.getJSONFromUrl(url));
            }
            return jsonList;
        }

        @Override
        protected void onPostExecute(List<JSONObject> jsonList) {
            if(jsonList != null){
                productInfoParser.Parse(jsonList); 
            }
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
    
    public static class MenuFragment extends Fragment {
        public static final String ARG_SECTION_NUMBER = "section_number";
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_restaurant_menu,
                    container, false);
            
            ListView listView = (ListView) rootView.findViewById(R.id.list_menu);
            
            listView.setAdapter(new MenuListAdapter(getActivity()));
            return rootView;
        }
        
        private class MenuListAdapter extends BaseAdapter {
            public int textWidth;
            
            private final Context mContext;
            
            public MenuListAdapter(Context context) {
                mContext = context;
            }

            @Override
            public int getCount() {
                return 0;
                //return LIST.size();
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
                
                View item = convertView;
                
                return item;
            }
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
        if (item.getTitle() == "Settings") {
            Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        } else if (itemId == android.R.id.home) {
            toggle();
            return true;
        } else if (item.getTitle() == "Refresh") {
            /*
            if(receiver.isNetwork()){
                Intent intent = new Intent(MenuLists.this, SplashScreen.class);
                editor = pref.edit();
                editor.putString("refresh", "menu");
                editor.putString("restaurant", restaurant_selection);
                editor.putInt("position", positionRestaurant);
                editor.commit();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Cannot refresh because either there is no network or the network does not match your preferences", Toast.LENGTH_SHORT).show();
            }*/
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
