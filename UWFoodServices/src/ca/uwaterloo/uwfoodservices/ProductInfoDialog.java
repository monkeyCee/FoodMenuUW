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
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;
import ca.uwaterloo.uwfoodservicesutility.ParseProductInfo;
import ca.uwaterloo.uwfoodservicesutility.ProductInfoHolder;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuItem;

public class ProductInfoDialog extends FragmentActivity implements
		ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener,
		TabContentFactory {

    int restaurantPosition;
    long productDay;
    String selectedItem;
    int currentPosition;
    int weekDay;
    ArrayList<Integer> productIds;
    
    int tabPosition;
    ArrayList<RestaurantMenuItem> productList;
    static List<String> productInfoUrls;
    static List<String> productNames;
    
    RestaurantMenuHolder menuHolder;
    
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    NetworkReceiver receiver;
    
    static ParseProductInfo productInfoParser;
    
    static boolean loaded = false;
    
	private ViewPager mViewPager;
	private TabHost mTabHost;
	private ProductInfoAdapter mPagerContent;

	@SuppressWarnings("unchecked")
    @Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_product_info_dialog);
		
		//Linearlayout layout = (LinearLayout) findViewById(R.id.)
		
		receiver = new NetworkReceiver(this);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		

        Intent intent = getIntent();
        restaurantPosition = intent.getIntExtra("Restaurant Position", -1);
        productDay = intent.getLongExtra("Product Day", -1);
        currentPosition = intent.getIntExtra("Current Position", -1);
        weekDay = intent.getIntExtra("Weekday", -1);
        selectedItem = intent.getStringExtra("Selected Item");
        productIds = new ArrayList<Integer>();
        productNames = new ArrayList<String>();
        
        menuHolder = RestaurantMenuHolder.getInstance();
        Log.d((menuHolder.getRestaurantMenu() == null) +"", "mInstance null? 1");
        if (menuHolder.getRestaurantMenu().get(restaurantPosition).getMenu()[weekDay].getLunch() != null) {
            if (currentPosition > menuHolder.getRestaurantMenu()
                    .get(restaurantPosition).getMenu()[weekDay].getLunch().size()) {
                productList = menuHolder.getRestaurantMenu().get(restaurantPosition).getMenu()[weekDay].getDinner();
            } else {
                productList = menuHolder.getRestaurantMenu().get(restaurantPosition).getMenu()[weekDay].getLunch();
            }
            for (int i = 0; i < productList.size(); i++) {
                Log.d(productList.get(i).getProductID() + "", "PRODUCT ID");
                if (productList.get(i).getProductID() != null) {
                    productIds.add(productList.get(i).getProductID());
                    productNames.add(productList.get(i).getProductName());
                }
            }
        }
        
        productInfoUrls = new ArrayList<String>();
        for (Integer id:productIds) {
            if (id != null) {
                productInfoUrls.add("http://api.uwaterloo.ca/public/v2/foodservices/product/" + id
                + ".json?key=98bbbd30b3e4f621d9cb544a790086d6");
            }
        }
        
        productInfoParser = new ParseProductInfo();
        new AsyncDataFetcher().execute(productInfoUrls);
		
        tabPosition = 0;
        for (int i = 0; i < productNames.size(); i ++) {
            Log.d(productNames.get(i), "TABPOSITION - PRODUCT NAMES");
            Log.d(selectedItem, "TABPOSITION - PRODUCT NAMES");
            if (productNames.get(i).equals(selectedItem)) {
                tabPosition = i;
            }
        }
        
        Log.d(tabPosition + "", "TABPOSITION");
        
		mViewPager = (ViewPager) findViewById(R.id.pager);
		// TabHost initialization
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		for (int i = 0; i < productNames.size(); i++) {
			mTabHost.addTab(mTabHost.newTabSpec("tab" + i).setContent(this).setIndicator(productNames.get(i)));
		}
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(tabPosition);
		// ViewPager initialization
		mPagerContent = new ProductInfoAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerContent);
		mViewPager.setCurrentItem(tabPosition);
		mViewPager.setOnPageChangeListener(this);
	}

	private static class AsyncDataFetcher extends AsyncTask<List<String>, Void, Void> {

        public AsyncDataFetcher() {
            
        }

        @Override
        protected Void doInBackground(List<String>... urls) {
            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            JSONParser json_parse = new JSONParser();
            for (int i = 0; i < urls[0].size(); i ++) {
                jsonList.add(json_parse.getJSONFromUrl(urls[0].get(i)));
                Log.d("added " + urls[0].get(i), "LOADED");
            }
            
            if(jsonList != null){
                productInfoParser.Parse(jsonList); 
                loaded = true;
            }
            return null;
        }
	}
	
	public static class ProductInfoAdapter extends FragmentPagerAdapter {

        private ArrayList<ProductInfoFragment> mFragments;

        public ProductInfoAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<ProductInfoFragment>();
            for (int i = 0; i < productInfoUrls.size(); i++)
                mFragments.add(new ProductInfoFragment());
        }

        @Override
        public int getCount() {
            return productInfoUrls.size();
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
        List<String> left_list = new ArrayList<String>();
        List<String> right_list = new ArrayList<String>();
        String serving;
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_product_info,
                    container, false);
            
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
            
            position = getArguments().getInt(ARG_SECTION_NUMBER);
            
            ListView listView = (ListView) rootView.findViewById(R.id.list_menu);
            TextView textIngredients = (TextView) rootView.findViewById(R.id.textIngredients);
            textIngredients.setTypeface(tf);
            
            while(loaded == false) {
                //Log.d("WAITING", "LOADED");
            }
            
            ProductInfoHolder productInfoHolder = ProductInfoHolder.getInstance();
            
            left_list.clear();
            right_list.clear();
            Log.d(position + "", "POSITION1");
            Log.d(productInfoHolder.productInfo.get(position) + "", "POSITION1");
            serving = "Per " + productInfoHolder.productInfo.get(position).get_serving_size();
            Log.d(position + "", "POSITION1.1");
            if (productInfoHolder.productInfo.get(position).get_serving_size_unit().equals("g")) {
                serving += " grams";
            } else {
                serving += " ml";
            }
            Log.d(position + "", "POSITION1.9");
            Log.d(position + "", "POSITION2");
            left_list.add(serving);
            right_list.add("");
            left_list.add("Amount");
            right_list.add("% Daily Value");
            left_list.add("Calories " + productInfoHolder.productInfo.get(position).get_calories());
            right_list.add("");
            left_list.add("Fat " + productInfoHolder.productInfo.get(position).get_total_fat_g() + " g");
            right_list.add(productInfoHolder.productInfo.get(position).get_total_fat_percent() + " %");
            left_list.add("  Saturated " + productInfoHolder.productInfo.get(position).get_fat_saturated_g() + " g");
            right_list.add(productInfoHolder.productInfo.get(position).get_fat_saturated_percent() + " %");
            if (productInfoHolder.productInfo.get(position).get_fat_trans_g() != null) {
                left_list.add("  Trans " + productInfoHolder.productInfo.get(position).get_fat_trans_g() + "");
                right_list.add(productInfoHolder.productInfo.get(position).get_fat_trans_percent() + " %");
            }
            left_list.add("Cholesterol " + productInfoHolder.productInfo.get(position).get_cholesterol_mg() + " mg");
            right_list.add("");
            left_list.add("Sodium " + productInfoHolder.productInfo.get(position).get_sodium_mg() + " mg");
            right_list.add(productInfoHolder.productInfo.get(position).get_sodium_percent() + " %");
            if (productInfoHolder.productInfo.get(position).get_carbo_g() != null) {
                left_list.add("Carbohydrate " + productInfoHolder.productInfo.get(position).get_carbo_g() + " g");
                right_list.add(productInfoHolder.productInfo.get(position).get_carbo_percent() + " %");
                if (productInfoHolder.productInfo.get(position).get_carbo_fibre_g() != null) {
                    left_list.add("  Fibre " + productInfoHolder.productInfo.get(position).get_carbo_fibre_g() + " g");
                    right_list.add("");
                }
                if (productInfoHolder.productInfo.get(position).get_carbo_fibre_percent() != null) {
                    left_list.add("  Sugars " + productInfoHolder.productInfo.get(position).get_carbo_fibre_percent() + " g");
                    right_list.add("");
                }
            }
            left_list.add("Protein " + productInfoHolder.productInfo.get(position).get_protein_g() + " g");
            right_list.add("");
            
            if (productInfoHolder.productInfo.get(position).get_vitamin_a_percent() != null) {
                left_list.add("Vitamin A"); 
                right_list.add(productInfoHolder.productInfo.get(position).get_vitamin_a_percent() + " %");
            }
            if (productInfoHolder.productInfo.get(position).get_vitamin_c_percent() != null) { 
                left_list.add("Vitamin C"); 
                right_list.add(productInfoHolder.productInfo.get(position).get_vitamin_c_percent() + " %");
            }
            if (productInfoHolder.productInfo.get(position).get_calcium_percent() != null) {
                left_list.add("Calcium");
                right_list.add(productInfoHolder.productInfo.get(position).get_calcium_percent() + " %");
            }
            if (productInfoHolder.productInfo.get(position).get_iron_percent() != null) {
                left_list.add("Iron"); 
                right_list.add(productInfoHolder.productInfo.get(position).get_iron_percent() + " %");
            }
            ProductInfoAdapter productInfoAdapter = new ProductInfoAdapter(getActivity());
            listView.setAdapter(productInfoAdapter);
            
            if (productInfoHolder.productInfo.get(position).get_ingredients() != null) {
                String ingredients = "Ingredients: ";
                List<String> ingredientList = productInfoHolder.productInfo.get(position).get_ingredients();
                ingredients += ingredientList.get(0);
                for (int i = 1; i < ingredientList.size(); i++) {
                    ingredients += ", " + ingredientList.get(i);
                }
                textIngredients.setText(ingredients);
            }
            
            return rootView;
        }
        
        private class ProductInfoAdapter extends BaseAdapter {
            
            private final Context mContext;
            
            public ProductInfoAdapter(Context context) {
                mContext = context;
            }

            @Override
            public int getCount() {
                return left_list.size();
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
                item_nutrition.setText(left_list.get(position % left_list.size()));
                item_nutrition.setTypeface(tf);
                
                TextView item_percent = (TextView)item.findViewById(R.id.lv_item_percent);
                item_percent.setText(right_list.get(position % right_list.size()));
                item_percent.setTypeface(tf);

                return item;
            }
        }
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		mTabHost.setCurrentTab(position);
	}

	@Override
	public void onTabChanged(String tabId) {
		mViewPager.setCurrentItem(Integer.parseInt(tabId.substring(tabId
				.length() - 1)), true);
	}

	@Override
	public View createTabContent(String tag) {
		View dummyContent = new View(this);
		dummyContent.setMinimumHeight(0);
		return dummyContent;
	}

}