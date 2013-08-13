package ca.uwaterloo.uwfoodservices;

import ca.uwaterloo.mainscreencontrols.*;
import ca.uwaterloo.mainscreencontrols.SpinningMenuAdapter.OnItemClickListener;
import ca.uwaterloo.mainscreencontrols.SpinningMenuAdapter.OnItemSelectedListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainScreen extends Activity {
    
    private int requestCode = -1;
    private String selectedTab = "Menu";
    private SpinningMenuAdapter<?> parentTracker;
    private int positionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        
        final Intent intent_restaurant = new Intent(this, RestaurantMenuList.class);
        final Intent intent_location = new Intent(this, LocationHours.class);
        final Intent intent_settings = new Intent(this, SettingsActivity.class);
        final Intent intent_about = new Intent(this, AboutPage.class);
        
        final SpinningMenu spinningMenu = (SpinningMenu)findViewById(R.id.SpinningMenu);
        
        
        spinningMenu.post(new Runnable(){

            @Override
            public void run() {
                spinningMenu.setOnItemClickListener(new OnItemClickListener(){

                    public void onItemClick(SpinningMenuAdapter<?> parent, View view,
                            int position, long id) {    
                        spinningMenu.scrollToChild(position);
                        parentTracker = parent;
                        positionTracker = position;
                        
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);

                        int yCentre = metrics.heightPixels/2;
                        int xCentre = metrics.widthPixels/2;
                        
                        if ((Math.abs(spinningMenu.clickedX - xCentre)<200)&&
                                (Math.abs(spinningMenu.clickedY - yCentre)<200)) 
                        {
                            String temp = (String)((SpinningMenuItem) parent.getChildAt(position)).getName();
                            
                            if ((selectedTab.equals(temp))||(temp.equals("Locations and\n        Hours")&&
                                    selectedTab.equals("Locations and Hours")))
                                ((SpinningMenuItem)parent.getChildAt(position)).mImage.setAlpha(100);                   

                            if (temp.equals("Menu")&&selectedTab.equals("Menu"))
                            {
                                startActivityForResult(intent_restaurant, requestCode);                       
                            }
                            else if (((temp.equals("Locations and\n        Hours"))||(temp.equals("Locations and Hours")))
                                    &&selectedTab.equals("Locations and Hours"))
                            {
                                startActivityForResult(intent_location, requestCode);
                            }
                            else if (temp.equals("Settings")&&selectedTab.equals("Settings"))
                            {
                                startActivityForResult(intent_settings, requestCode);
                            }
                            else if (temp.equals("About Us")&&selectedTab.equals("About Us"))
                            {
                                startActivityForResult(intent_about, requestCode);
                            }
                        }
                    }
                });

                spinningMenu.setOnItemSelectedListener(new OnItemSelectedListener(){

                    public void onItemSelected(SpinningMenuAdapter<?> parent, View view,
                            int position, long id) {
                        
                        switch(position)
                        {
                            case 0:
                                selectedTab = "Menu";
                                break;
                            case 1:
                                selectedTab = "Locations and Hours";
                                break;
                            case 2:
                                selectedTab = "Settings";
                                break;
                            case 3:
                                selectedTab = "About Us";
                                break;
                        }               
                    }

                    public void onNothingSelected(SpinningMenuAdapter<?> parent) {
                    }
                });                
            }           
        });
        
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        if (parentTracker != null)
            ((SpinningMenuItem)parentTracker.getChildAt(positionTracker)).mImage.setAlpha(255);
        else
            return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen2, menu);
        return true;
    }

}
