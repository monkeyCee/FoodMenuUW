package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;
import ca.uwaterloo.uwfoodservicesutility.ParseProductInfo;
import ca.uwaterloo.uwfoodservicesutility.ProductInfoHolder;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuItem;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class ProductInfo extends SlidingMenus implements ActionBar.TabListener{

    ViewPager vp;
    int restaurantPosition;
    long productDay;
    int currentPosition;
    int weekDay;
    ArrayList<Integer> productIds;
    
    ArrayList<RestaurantMenuItem> productList;
    
    RestaurantMenuHolder menuHolder;
    
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    NetworkReceiver receiver;
    
    static ParseProductInfo productInfoParser;
    
    static boolean loaded = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        
        receiver = new NetworkReceiver(this);
        
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        restaurantPosition = intent.getIntExtra("Restaurant Position", -1);
        productDay = intent.getLongExtra("Product Day", -1);
        currentPosition = intent.getIntExtra("Current Position", -1);
        weekDay = intent.getIntExtra("Weekday", -1);
        productIds = new ArrayList<Integer>();
        //productIds = intent.getIntegerArrayListExtra("Product Ids");
        
        Log.d(weekDay+"", "weekday");
        
        menuHolder = RestaurantMenuHolder.getInstance();
        if (menuHolder.restaurantMenu.get(restaurantPosition).getMenu()[weekDay].getLunch() != null) {
            Log.d((currentPosition > (menuHolder.restaurantMenu.get(restaurantPosition).getMenu()[weekDay].getLunch().size() - 1)) + "", "PRODUCT - DINNER");
            if (currentPosition >
                    (menuHolder.restaurantMenu.get(restaurantPosition).getMenu()[weekDay].getLunch().size() - 1)) {
                productList = menuHolder.restaurantMenu.get(restaurantPosition).getMenu()[weekDay].getDinner();
            } else {
                productList = menuHolder.restaurantMenu.get(restaurantPosition).getMenu()[weekDay].getLunch();
            }
            for (int i = 0; i < productList.size(); i++) {
                productIds.add(productList.get(i).getProductID());
            }
        }

        Log.d(productIds + "", "PRODUCTIDS");
        
        List<String> productInfoUrls = new ArrayList<String>();
        for (int id:productIds) {
                productInfoUrls.add("http://api.uwaterloo.ca/public/v2/foodservices/product/" + id
                + ".json?key=98bbbd30b3e4f621d9cb544a790086d6");
        }
        
        productInfoParser = new ParseProductInfo();
        new AsyncDataFetcher(this).execute(productInfoUrls);
        
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setTitle(menuHolder.restaurantMenu.get(restaurantPosition).getRestaurant());
        actionBar.setIcon(R.drawable.ic_drawer);
        actionBar.setDisplayUseLogoEnabled(false);
        
        vp = (ViewPager) findViewById(R.id.pager);
        vp.setAdapter(new ProductInfoAdapter(getSupportFragmentManager()));

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
        
        for (int i = 0; i < ProductInfoAdapter.days.length; i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(ProductInfoAdapter.days[i])
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
        protected List<JSONObject> doInBackground(List<String>... urls) {
            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            JSONParser json_parse = new JSONParser();
            Log.d("PREPARE TO LOAD - BEFORE", "LOADED");
            for (int i = 0; i < urls[0].size(); i ++) {
                jsonList.add(json_parse.getJSONFromUrl(urls[0].get(i)));
                Log.d("added " + i, "LOADED");
            }
            Log.d("" + (jsonList != null), "LOADED");
            Log.d("returning jsonList", "LOADED");
            return jsonList;
        }

        @Override
        protected void onPostExecute(List<JSONObject> jsonList) {
            Log.d("ONPOST", "LOADED");
            Log.d("" + (jsonList != null), "LOADED");
            if(jsonList != null){
                Log.d("PREPARE TO LOAD", "LOADED");
                productInfoParser.Parse(jsonList); 
                loaded = true;
            } else {
                Log.d("NOT LOADED?", "LOADED");
            }
        }
    }
    
    public static class ProductInfoAdapter extends FragmentPagerAdapter {

        private ArrayList<ProductInfoFragment> mFragments;

        public final static String[] days = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        public ProductInfoAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<ProductInfoFragment>();
            for (int i = 0; i < 3; i++)
                mFragments.add(new ProductInfoFragment());
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ProductInfoFragment();
            Bundle args = new Bundle();
            args.putInt(ProductInfoFragment.ARG_SECTION_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

    }
    
    public static class ProductInfoFragment extends Fragment {
        
        public static final String ARG_SECTION_NUMBER = "section_number";
        int position;
        List<String> LIST = new ArrayList<String>();
        String serving;
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_restaurant_menu,
                    container, false);
            
            ProductInfoHolder productInfoHolder = ProductInfoHolder.getInstance(null);
            
            position = getArguments().getInt(ARG_SECTION_NUMBER);
            
            ListView listView = (ListView) rootView.findViewById(R.id.list_menu);
            
            while(loaded == false) {
                //Log.d("WAITING", "LOADED");
            }
            
            LIST.clear();
            Log.d(position + "", "POSITION");
            serving = "Per" + productInfoHolder.productInfo.get(position).get_serving_size();
            if (productInfoHolder.productInfo.get(position).get_serving_size_unit().equals("g")) {
                serving += " g";
            } else {
                serving += " ml";
            }
            LIST.add(serving);
            LIST.add(productInfoHolder.productInfo.get(position).get_calories() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_total_fat_g() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_fat_saturated_g() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_fat_trans_g() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_cholesterol_mg() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_sodium_mg() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_carbo_g() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_carbo_sugars_g() + "");
            LIST.add(productInfoHolder.productInfo.get(position).get_protein_g() + "");
            
            if (productInfoHolder.productInfo.get(position).get_vitamin_a_percent() != null) { LIST.add("Vitamin A"); }
            if (productInfoHolder.productInfo.get(position).get_vitamin_c_percent() != null) { LIST.add("Vitamin C"); }
            if (productInfoHolder.productInfo.get(position).get_calcium_percent() != null) { LIST.add("Calcium"); }
            if (productInfoHolder.productInfo.get(position).get_iron_percent() != null) { LIST.add("Iron"); }
            
            ProductInfoAdapter menuListAdapter = new ProductInfoAdapter(getActivity());
            listView.setAdapter(menuListAdapter);
            return rootView;
        }
        
        private class ProductInfoAdapter extends BaseAdapter {
            public int textWidth;
            
            private final Context mContext;
            
            public ProductInfoAdapter(Context context) {
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
                
                View item = convertView;
                
                item = LayoutInflater.from(mContext).inflate(R.layout.lv_nutrition, parent, false);
                Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Light.ttf");

                TextView item_nutrition = (TextView)item.findViewById(R.id.lv_item_nutrition);
                item_nutrition.setText("NUTRITION");
                item_nutrition.setTypeface(tf);
                
                TextView item_percent = (TextView)item.findViewById(R.id.lv_item_percent);
                item_percent.setText("NUTRITION");
                item_percent.setTypeface(tf);

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
