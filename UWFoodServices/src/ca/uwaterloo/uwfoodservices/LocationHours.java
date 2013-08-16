package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;

import ca.uwaterloo.uwfoodservices.MenuLists.MenuFragment;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.widget.Toast;

public class LocationHours extends SlidingMenus implements ActionBar.TabListener, ActivityCommunicator {
    
    ViewPager vp;
    ActionBar actionBar;
    public FragmentCommunicator fragmentCommunicator;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    NetworkReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcard_vendors);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();
        
        receiver = new NetworkReceiver(this);
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setIcon(R.drawable.clock_icon);
        actionBar.setDisplayUseLogoEnabled(false);

        vp = (ViewPager) findViewById(R.id.pager2);
        if (intent.hasExtra("locations"))
        {
            vp.setAdapter(new MenuAdapterLoc(getSupportFragmentManager()));
        }
        else if (intent.hasExtra("vendors"))
        {
            vp.setAdapter(new MenuAdapterVend(getSupportFragmentManager()));
        }
        else
        {
            vp.setAdapter(new MenuAdapterAll(getSupportFragmentManager()));
        }

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
        
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(MenuAdapterVend.location_tabs[i])
                    .setTabListener(this));
        }

        vp.setCurrentItem(0);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }
    
    public static class MenuAdapterVend extends FragmentPagerAdapter {

        private ArrayList<MenuFragment> mFragments;

        public final static String[] location_tabs = new String[] {"ListView", "MapView", "Filter"};

        public MenuAdapterVend(FragmentManager fm) {
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
                args.putString ("type", "watcardVendors");
                fragment.setArguments(args);
                return fragment;        
            }
            else if (position ==1){                
                Fragment fragment = new VendorMapFragment();
                Bundle args = new Bundle();
                args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
                fragment.setArguments(args);
                return fragment;
            }
            else
            {
                Fragment fragment = new FilterFragment();
                Bundle args = new Bundle();
                args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
                fragment.setArguments(args);
                return fragment;   
            }
            
    }
    }
    
    public static class MenuAdapterLoc extends FragmentPagerAdapter {

        private ArrayList<MenuFragment> mFragments;

        public final static String[] location_tabs = new String[] {"ListView", "MapView", "Filter"};

        public MenuAdapterLoc(FragmentManager fm) {
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
                args.putString ("type", "location");
                fragment.setArguments(args);
                return fragment;        
            }
            else if (position ==1){                
                Fragment fragment = new MyMapFragment();
                Bundle args = new Bundle();
                args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
                fragment.setArguments(args);
                return fragment;
            }
            else
            {
                Fragment fragment = new FilterFragment();
                Bundle args = new Bundle();
                args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
                fragment.setArguments(args);
                return fragment;   
            }
            
    }
    }
    
    public static class MenuAdapterAll extends FragmentPagerAdapter {

        private ArrayList<MenuFragment> mFragments;

        public final static String[] location_tabs = new String[] {"ListView", "MapView", "Filter"};

        public MenuAdapterAll(FragmentManager fm) {
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
                args.putString ("type", "all");
                fragment.setArguments(args);
                return fragment;        
            }
            else if (position ==1){                
                Fragment fragment = new AllMapFragment();
                Bundle args = new Bundle();
                args.putInt(MenuFragment.ARG_SECTION_NUMBER, position);
                fragment.setArguments(args);
                return fragment;
            }
            else
            {
                Fragment fragment = new FilterFragment();
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

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        vp.setCurrentItem(tab.getPosition());
        
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
        
    }

}
