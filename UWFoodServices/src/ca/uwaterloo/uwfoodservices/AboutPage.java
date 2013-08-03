package ca.uwaterloo.uwfoodservices;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class AboutPage extends SlidingMenus {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.aboutus_icon);
        
        Typeface robotoBold = Typeface.createFromAsset(this.getAssets(),
                "Roboto-Bold.ttf");
        
        Typeface robotoRegular = Typeface.createFromAsset(this.getAssets(),
                "Roboto-Regular.ttf");
        
        Typeface robotoLight = Typeface.createFromAsset(this.getAssets(),
                "Roboto-Light.ttf");
        
        TextView title = (TextView) findViewById(R.id.TextViewTitle);
        title.setTypeface(robotoBold);
        
        TextView version = (TextView) findViewById(R.id.TextViewVersion);
        try {
            version.setText("Version Code: " + getPackageManager().getPackageInfo(getPackageName(), 0).versionCode
                    + "     Version Name: " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        version.setTypeface(robotoLight);
        
        TextView createdBy = (TextView) findViewById(R.id.TextViewCreatedBy);
        createdBy.setText(Html.fromHtml("Created By <font color='#3992B5'>Shamak Dutta</font>, " +
        		"<font color='#3992B5'>Corey Wu</font> and <font color='#3992B5'>Johan Augustine</font>."));
        createdBy.setTypeface(robotoRegular);
        
        TextView githubLink = (TextView) findViewById(R.id.TextViewGithub);
        githubLink.setText(Html.fromHtml("This open source application is available on <a href='https://github.com/shamak/FoodMenuUW'><font color='#3992B5'>GitHub</font></a>."));
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());
        githubLink.setTypeface(robotoLight);
        
        TextView openDataLicense = (TextView) findViewById(R.id.TextViewOpenData);
        openDataLicense.setText(Html.fromHtml("Contains information provided by the University of Waterloo under " +
        		"<a href='https://uwaterloo.ca/open-data/university-waterloo-open-data-license-agreement-v1'><font color='#3992B5'>license</a> on an 'as is' basis."));
        openDataLicense.setMovementMethod(LinkMovementMethod.getInstance());
        openDataLicense.setTypeface(robotoLight);
        
        TextView licenses = (TextView) findViewById(R.id.textViewLicenses);
        licenses.setTypeface(robotoBold);
        
        TextView slidingMenu = (TextView) findViewById(R.id.TextViewSlidingMenu);
        slidingMenu.setText(Html.fromHtml("<a href='https://github.com/jfeinstein10/SlidingMenu'><font color='#3992B5'>Sliding Menu</font></a> - by Jeremy Feinstein"));
        slidingMenu.setMovementMethod(LinkMovementMethod.getInstance());
        slidingMenu.setTypeface(robotoLight);
        
        TextView slidingMenulicense = (TextView) findViewById(R.id.TextViewSlidingMenuLicense);
        slidingMenulicense.setTypeface(robotoLight);
        
        TextView googlePlayServices = (TextView) findViewById(R.id.TextViewGooglePlay);
        googlePlayServices.setText(Html.fromHtml("<a href='http://developer.android.com/google/play-services/index.html'" +
        		"<font color='#3992B5'>Google Play Services</font></a> - Google Inc."));
        googlePlayServices.setMovementMethod(LinkMovementMethod.getInstance());
        googlePlayServices.setTypeface(robotoLight);
        
        TextView googlePlaylicense = (TextView) findViewById(R.id.textViewGooglePlayLicense);
        googlePlaylicense.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this));
        googlePlaylicense.setTypeface(robotoLight);
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
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
