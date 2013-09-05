package ca.uwaterloo.uwfoodservices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MenuLists extends SlidingMenus implements ActionBar.TabListener{

    ViewPager vp;
    static String restaurantSelection;
    static int positionRestaurant;
    RestaurantLocationHolder holder = RestaurantLocationHolder.getInstance();

    SharedPreferences.Editor editor;
    SharedPreferences pref;
    public String networkPref;
    private static NetworkReceiver receiver;

    public static String formattedDate;
    static int weekDay;
    static Calendar calendar;
    static SimpleDateFormat simpleDateFormat;
    static Calendar currentDate;
    IntentFilter filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lists);

        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver(this);
        this.registerReceiver(receiver, filter);
        networkPref = receiver.getNetworkPref();

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        restaurantSelection = intent.getStringExtra("Restaurant Name");
        positionRestaurant = intent.getIntExtra("Restaurant Position", 0);

        // Date handling
        calendar = Calendar.getInstance();
        currentDate = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("MMM dd", Locale.CANADA);
        formattedDate = simpleDateFormat.format(calendar.getTime());
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

        actionBar.setTitle(restaurantSelection);
        actionBar.setIcon(R.drawable.ic_drawer);
        actionBar.setDisplayUseLogoEnabled(false);

        // Set max text width based on screen resolution
        MenuUtilities.setMenuItemTextWidth(550);
        int screenWidth = MenuUtilities.getScreenWidth(this);
        MenuUtilities.setMenuItemTextWidth((screenWidth * MenuUtilities.getMenuItemTextWidth()) / 768);

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

    public static class MenuAdapter extends FragmentPagerAdapter {

        private ArrayList<MenuFragment> mFragments;

        public final static String[] days = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        public MenuAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<MenuFragment>();
            for (int i = 0; i < days.length; i++) {
                mFragments.add(new MenuFragment());
            }
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
        public int day;

        public static final int HDR_POS1 = 0;
        public int[] HDR_POS2 = new int[7];

        List<String> LIST = new ArrayList<String>();
        List<ArrayList<String>> clickableList = null;
        List<ArrayList<String>> dietTypeList = null;

        String dietType;

        private static final Integer LIST_HEADER = 0;
        private static final Integer LIST_ITEM = 1;

        public MenuFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_restaurant_menu,
                    container, false);

            if (clickableList == null) {
                clickableList = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < 7; i ++) {
                    clickableList.add(new ArrayList<String>());
                }
            }

            if (dietTypeList == null) {
                dietTypeList = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < 7; i ++) {
                    dietTypeList.add(new ArrayList<String>());
                }
            }

            day = getArguments().getInt(ARG_SECTION_NUMBER);

            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, getArguments().getInt(ARG_SECTION_NUMBER) - weekDay);

            simpleDateFormat = new SimpleDateFormat("MMM dd", Locale.CANADA);

            if (simpleDateFormat.format(calendar.getTime()).split(" ")[1].startsWith("0")) {
                formattedDate = simpleDateFormat.format(calendar.getTime()).split(" ")[0] +
                        " " + simpleDateFormat.format(calendar.getTime()).split(" ")[1].charAt(1);
            } else {
                formattedDate = simpleDateFormat.format(calendar.getTime());
            }

            if (formattedDate.endsWith("1")) { 
                if (formattedDate.endsWith("11")) {
                    formattedDate += "th"; 
                } else {
                    formattedDate += "st";
                }
            } else if (formattedDate.endsWith("2")) { 
                if (formattedDate.endsWith("12")) {
                    formattedDate+= "th";
                } else {
                    formattedDate += "nd";
                }
            } else if (formattedDate.endsWith("3")) {
                if (formattedDate.endsWith("13")) {
                    formattedDate+= "th";
                } else {
                    formattedDate += "rd";
                }
            } else { 
                formattedDate += "th"; 
            }

            RestaurantMenuHolder menuHolder = RestaurantMenuHolder.getInstance(null);

            TextView textDay = (TextView) rootView.findViewById(R.id.textDay);
            if (formattedDate.substring(0,3).equals("Sep")) {
                formattedDate = "Sept" + formattedDate.substring(3);
            }
            SpannableString content = new SpannableString(formattedDate);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            textDay.setText(content);

            ListView listView = (ListView) rootView.findViewById(R.id.list_menu);

            LIST.clear();

            LIST.add("LUNCH");
            clickableList.get(day).add("header");
            dietTypeList.get(day).add("header");

            if (menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getLunch() == null) {
                LIST.add("There is nothing on the menu");
                clickableList.get(day).add("header");
                dietTypeList.get(day).add("header");
            } else {
                for (int i = 0; i < menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getLunch().size(); i++) {
                    LIST.add(menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getLunch().get(i).getProductName());
                    if (menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getLunch().get(i).getProductID() != null) {
                        clickableList.get(day).add("true");
                    } else {
                        clickableList.get(day).add("false");
                    }
                    dietType = menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day]
                            .getLunch().get(i).getDietType();
                    if (dietType != null) {
                        dietTypeList.get(day).add(dietType);
                    } else {
                        dietTypeList.get(day).add(null);
                    }
                }
            }

            HDR_POS2[day] = LIST.size();
            LIST.add("DINNER");
            clickableList.get(day).add("header");
            dietTypeList.get(day).add("header");

            if (menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getDinner() == null) {
                LIST.add("There is nothing on the menu");
                clickableList.get(day).add("header");
                dietTypeList.get(day).add("header");
            } else {
                for (int i = 0; i < menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getDinner().size(); i++) {
                    LIST.add(menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getDinner().get(i).getProductName());
                    if (menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day].getDinner().get(i).getProductID() != null) {
                        clickableList.get(day).add("true");
                    } else {
                        clickableList.get(day).add("false");
                    }
                    dietType = menuHolder.getRestaurantMenu().get(positionRestaurant).getMenu()[day]
                            .getDinner().get(i).getDietType();
                    if (dietType != null) {
                        dietTypeList.get(day).add(dietType);
                    } else {
                        dietTypeList.get(day).add(null);
                    }
                }
            }

            MenuListAdapter menuListAdapter = new MenuListAdapter(getActivity());
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                        long arg3) {
                    if (clickableList.get(day).get(arg2).equals("true")) {
                        if (receiver.isNetwork()) {
                            final Intent intentProductInfo = new Intent(getActivity(), ProductInfoDialog.class);
                            Long productDay = currentDate.getTimeInMillis();
                            intentProductInfo.putExtra("Restaurant Position", positionRestaurant);
                            intentProductInfo.putExtra("Product Day", productDay);
                            intentProductInfo.putExtra("Selected Item", LIST.get((int)arg3));
                            int currentPosition = arg2;
                            intentProductInfo.putExtra("Current Position", currentPosition);
                            intentProductInfo.putExtra("Weekday", day);
                            startActivityForResult(intentProductInfo, 1);                

                        } else {
                            final Toast toast = Toast.makeText(getActivity(), 
                                    "Cannot display product info.\nThere is no network.", Toast.LENGTH_SHORT);
                            ((TextView)((LinearLayout)toast.getView()).getChildAt(0))
                            .setGravity(Gravity.CENTER_HORIZONTAL);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel(); 
                                }
                            }, 1000);
                        }
                    } else {
                        if (!clickableList.get(day).get(arg2).equals("header")) {
                            final Toast toast = Toast.makeText(getActivity(), 
                                    "There is no nutritional information for this product.", Toast.LENGTH_SHORT);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel(); 
                                }
                            }, 800);
                        }
                    }

                }
            });
            listView.setAdapter(menuListAdapter);
            listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            return rootView;
        }

        private class MenuListAdapter extends BaseAdapter{
            public int textWidth;

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
                    if((convertView == null) || (convertView.getTag() == LIST_ITEM)) {

                        item = LayoutInflater.from(mContext).inflate(
                                R.layout.lv_header_layout, parent, false);
                        item.setTag(LIST_HEADER);

                    }
                    Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Bold.ttf");
                    TextView headerTextView = (TextView)item.findViewById(R.id.lv_list_hdr);
                    headerTextView.setTypeface(tf);
                    headerTextView.setText(headerText);
                    return item;
                }

                View item = convertView;
                if((convertView == null) || (convertView.getTag() == LIST_HEADER)) {
                    item = LayoutInflater.from(mContext).inflate(
                            R.layout.lv_layout, parent, false);
                    item.setTag(LIST_ITEM);
                }

                ImageButton image = (ImageButton)item.findViewById(R.id.button);
                if (dietTypeList.get(day).get(position % dietTypeList.get(day).size()).equals("null")) {
                    image.setImageResource(R.drawable.ic_no_information);
                } else if (dietTypeList.get(day).get(position % dietTypeList.get(day).size()).equals("Halal")) {
                    image.setImageResource(R.drawable.ic_halal);
                } else if (dietTypeList.get(day).get(position % dietTypeList.get(day).size()).equals("Vegetarian")) {
                    image.setImageResource(R.drawable.ic_vegetarian);
                } else if (dietTypeList.get(day).get(position % dietTypeList.get(day).size()).equals("Vegan")) {
                    image.setImageResource(R.drawable.ic_vegan);
                } else if (dietTypeList.get(day).get(position % dietTypeList.get(day).size()).equals("Non Vegetarian")) {
                    image.setImageResource(R.drawable.ic_non_vegetarian);
                } /*else if (dietTypeList.get(day).get(position % dietTypeList.get(day).size()).equals("header")) {
                    image.setAlpha(0.0f);
	            }*/

                if (dietTypeList.get(day).get(position % dietTypeList.get(day).size()).equals("null")) {
                    image.setTag("No Information");
                } else {
                    image.setTag(dietTypeList.get(day).get(position % dietTypeList.get(day).size()));
                }

                image.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!v.getTag().equals("header")) {
                            final Toast toast = Toast.makeText(getActivity(), v.getTag().toString(),
                                    Toast.LENGTH_SHORT);
                            int[] location = new int[2];
                            v.getLocationOnScreen(location);
                            toast.setGravity(Gravity.TOP|Gravity.LEFT, location[0], location[1]);
                            toast.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel(); 
                                }
                            }, 800);
                        }
                    }
                });

                TextView header = (TextView)item.findViewById(R.id.lv_item_header);
                Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Light.ttf");
                header.setText(LIST.get(position % LIST.size()));
                if (clickableList.get(day).get(position % clickableList.get(day).size()).equals("true")) {
                    header.setFocusable(true);
                    header.setFocusableInTouchMode(true);
                } else {
                    header.setFocusable(false);
                    header.setFocusableInTouchMode(false);
                    header.setTextColor(getResources().getColor(R.color.gray));
                }
                header.setTypeface(tf);

                // Measures the width of the text in the textView.
                textWidth = MenuUtilities.getTextWidth(LIST.get(position % LIST.size()), header.getPaint());
                // The text size is decreased until the text is under the specified width or the textSize
                // is under a certain size, inc which case the text will wrap to the next line.
                while ((textWidth > MenuUtilities.menuItemTextWidth) && (header.getTextSize() > 33)) {
                    header.setTextSize((header.getTextSize() - 1)/2); // Black magic happening here.
                    textWidth = MenuUtilities.getTextWidth(LIST.get(position % LIST.size()), header.getPaint());
                }
                // The textView is recentered if the textView wraps to a second line.
                if (header.getTextSize() == 33.0) {
                    header.setPadding(header.getPaddingLeft(), 0, header.getPaddingRight(), 0);
                }

                //Set last divider in a sublist invisible
                View divider = item.findViewById(R.id.item_separator);
                if((position == (HDR_POS2[day] -1)) || (position == (LIST.size() - 1))) {
                    divider.setVisibility(View.INVISIBLE);
                }

                return item;
            }

            private String getHeader(int position) {

                if((position == HDR_POS1)  || (position == HDR_POS2[day])) {
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
        if (item.getTitle() == "Settings") {
            Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        } else if (itemId == android.R.id.home) {
            toggle();
            return true;
        } else if (item.getTitle() == "Refresh") {

            if(receiver.isNetwork()){
                Intent intent = new Intent(MenuLists.this, SplashScreen.class);
                editor = pref.edit();
                editor.putString("refresh", "menu");
                editor.putString("restaurant", restaurantSelection);
                editor.putInt("position", positionRestaurant);
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
    public void onPause() {
        super.onPause();
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }

    }
    
    @Override
    public void onResume() {
        super.onResume();
        this.registerReceiver(receiver, filter);      
    }
}


