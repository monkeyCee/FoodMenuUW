package ca.uwaterloo.uwfoodservices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import ca.uwaterloo.uwfoodservicesutility.InternalStorage;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;
import ca.uwaterloo.uwfoodservicesutility.ParseMenuData;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuObject;

public class SplashScreen extends Activity {

    static ParseLocationData locationParser;
    static ParseMenuData menuParser;
    static ParseWatcardVendorData watcardVendorParser;
    SharedPreferences.Editor prefEditor;

    public static final String WIFI = "Wi-Fi Only";
    public static final String BOTH = "Both Wi-Fi and Data";
    public static final String DATA = "Data Only";    

    public String networkPref;
    SharedPreferences sharedPrefs;
    public static boolean cachePref;
    public static String refreshPref = null;
    public static int weekStored;
    public static boolean cacheLoad = false;

    public static String formattedDate;
    static int weekDay;
    static Calendar calendar;
    static SimpleDateFormat simpleDateFormat;

    private NetworkReceiver receiver;
    AsyncDataFetcher fetcher = new AsyncDataFetcher(SplashScreen.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver(this);
        this.registerReceiver(receiver, filter);

        networkPref = receiver.getNetworkPref();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        cachePref = sharedPrefs.getBoolean("save_data_preference", true);
        weekStored = sharedPrefs.getInt("storedWeek", -1);
        refreshPref = sharedPrefs.getString("refresh", "");
        receiver.updateConnectedFlags();

        loadData();
    }

    private void StartSplashScreen()
    {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.setFillAfter(true);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.uw_logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

        Animation finalAnim = AnimationUtils.loadAnimation(this, R.anim.translate2);
        finalAnim.setFillAfter(true);
        finalAnim.reset();
        ImageView iv2 = (ImageView) findViewById(R.id.foodmenu_logo);
        iv2.clearAnimation();
        iv2.startAnimation(finalAnim);

        finalAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
  
            }   

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                final Handler handler = new Handler();
                final Runnable r = new Runnable()
                {
                    @Override
                    public void run() 
                    {
                        if(fetcher.getStatus().equals(AsyncTask.Status.FINISHED) || (cacheLoad)){
                            Intent intent = new Intent(SplashScreen.this, MainScreen.class);
                            startActivity(intent);
                        }
                        else{
                            handler.postDelayed(this,1000);
                        }        
                    }
                };

                handler.postDelayed(r, 2000);

                   
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }

    }

    private static class AsyncDataFetcher extends AsyncTask<String, Void, JSONObject[]> {

        Context context;

        public AsyncDataFetcher(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {             
        }; 

        @Override
        protected JSONObject[] doInBackground(String... urls) {
            NetworkParser json_parse = new NetworkParser();
            JSONObject[] jsonObjectArray = new JSONObject[3];

            jsonObjectArray[0] = json_parse.getJSONFromUrl(urls[0]);
            jsonObjectArray[1] = json_parse.getJSONFromUrl(urls[1]);
            jsonObjectArray[2] = json_parse.getJSONFromUrl(urls[2]);
            return jsonObjectArray;
        }

        @Override
        protected void onPostExecute(JSONObject[] jObjArray) {
            if(jObjArray != null){
                menuParser.Parse(jObjArray[0]);	
                locationParser.Parse(jObjArray[1]);	
                watcardVendorParser.Parse(jObjArray[2]);

                try {
                    InternalStorage.writeObject(context, "menu", RestaurantMenuHolder.getInstance().getRestaurantMenu());
                    InternalStorage.writeObject(context, "location", RestaurantLocationHolder.getInstance().objects);
                    InternalStorage.writeObject (context, "watcard_vendor", WatcardVendorHolder.getInstance().objects);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
            }
        }
    }

    public String getDatedMenuUrl() {
        prefEditor = sharedPrefs.edit();
        calendar = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("MMMMMMMMM dd", Locale.CANADA);
        formattedDate = simpleDateFormat.format(calendar.getTime());

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(1);
        if (new SimpleDateFormat("EEE", Locale.CANADA).format(calendar.getTime()).equals("Sun")) {
            calendar.add(Calendar.DATE, -1);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("w", Locale.CANADA);
        simpleDateFormat.setCalendar(calendar);
        String weekInYear = simpleDateFormat.format(calendar.getTime());

        weekDay = 0;
        if (calendar.getTime().toString().split(" ")[0].equals("Mon")) { weekDay = 0; }
        if (calendar.getTime().toString().split(" ")[0].equals("Tue")) { weekDay = 1; }
        if (calendar.getTime().toString().split(" ")[0].equals("Wed")) { weekDay = 2; }
        if (calendar.getTime().toString().split(" ")[0].equals("Thu")) { weekDay = 3; }
        if (calendar.getTime().toString().split(" ")[0].equals("Fri")) { weekDay = 4; }
        if (calendar.getTime().toString().split(" ")[0].equals("Sat")) { weekDay = 5; }
        if (calendar.getTime().toString().split(" ")[0].equals("Sun")) { weekDay = 6; }

        prefEditor.putInt("storedWeek", Integer.parseInt(weekInYear));
        prefEditor.commit();

        return "https://api.uwaterloo.ca/v2/foodservices/2013/" + Integer.parseInt(weekInYear) 
                + "/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
        //return "http://api.uwaterloo.ca/public/v2/foodservices/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
    }

    public int getCurrentWeek(){

        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(1);
        if (new SimpleDateFormat("EEE", Locale.CANADA).format(calendar.getTime()).equals("Sun")) {
            calendar.add(Calendar.DATE, -1);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("w", Locale.CANADA);
        simpleDateFormat.setCalendar(calendar);
        String weekInYear = simpleDateFormat.format(calendar.getTime());

        return Integer.parseInt(weekInYear);
    }

    @SuppressWarnings("unchecked")
    private void loadData() {

        menuParser = new ParseMenuData(SplashScreen.this);
        locationParser = new ParseLocationData(SplashScreen.this);
        watcardVendorParser = new ParseWatcardVendorData(SplashScreen.this);

        if(refreshPref.equals("locations") || refreshPref.equals("menu")){

            if (receiver.isNetwork()) {

                Toast.makeText(getApplicationContext(), "Refreshing..", Toast.LENGTH_SHORT).show();

                String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
                String urlMenu = getDatedMenuUrl();
                String urlWatcardVendors = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=WatcardVendors&output=json";
                fetcher.execute(urlMenu, urlLocations, urlWatcardVendors);	
                
                final Handler handler = new Handler();
                final Runnable r = new Runnable()
                {
                    @Override
                    public void run() 
                    {
                        if(fetcher.getStatus().equals(AsyncTask.Status.FINISHED) || (cacheLoad)){
                            if(refreshPref.equals("locations")){
                                Intent intent = new Intent(SplashScreen.this, LocationHours.class);
                                prefEditor.remove("refresh");
                                prefEditor.commit();
                                startActivity(intent);
                            }
                            else{
                                if(refreshPref.equals("menu")){
                                    String restaurant = sharedPrefs.getString("restaurant", "");
                                    int position = sharedPrefs.getInt("position", -1);
                                    Intent intent = new Intent(SplashScreen.this, MenuLists.class);
                                    prefEditor.remove("refresh");
                                    prefEditor.remove("restaurant");
                                    prefEditor.remove("position");
                                    prefEditor.commit();
                                    intent.putExtra("Restaurant Name", restaurant);
                                    intent.putExtra("Restaurant Position", position);
                                    startActivity(intent);
                                }
                            }
                        }
                        else{
                            handler.postDelayed(this,1000);
                        }        
                    }
                };

                handler.postDelayed(r, 2000);

            }

        }

        else{
            cacheLoad = false;
            StartSplashScreen();

            if (!cachePref) {
                InternalStorage.deleteObject(SplashScreen.this, "menu");
                InternalStorage.deleteObject(SplashScreen.this, "location");
                InternalStorage.deleteObject(SplashScreen.this, "watcard_vendor");

                if (receiver.isNetwork()) {

                    String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
                    String urlMenu = getDatedMenuUrl();
                    String urlWatcardVendors = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=WatcardVendors&output=json";
                    fetcher.execute(urlMenu, urlLocations, urlWatcardVendors);	

                }

                else{ 
                    Toast.makeText(getApplicationContext(), "There is no stored data. There is either no network or the network does not match your preference.", Toast.LENGTH_SHORT).show();
                    showErrorPage(); }			
            }

            else{

                try {
                    if(!(InternalStorage.cacheExists(SplashScreen.this, "menu")) || !(InternalStorage.cacheExists(SplashScreen.this, "location"))||
                            !(InternalStorage.cacheExists(SplashScreen.this, "watcard_vendor"))){

                        if (receiver.isNetwork()) {				        
                            String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
                            String urlMenu = getDatedMenuUrl();
                            String urlWatcardVendors = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=WatcardVendors&output=json";
                            fetcher.execute(urlMenu, urlLocations, urlWatcardVendors);			
                        }
                        else{ 
                            Toast.makeText(getApplicationContext(), "There is no stored data and either there is no network or the network does not match your preference", Toast.LENGTH_SHORT).show();
                            showErrorPage(); }
                    }

                    else{

                        if((getCurrentWeek() != weekStored) && !receiver.isNetwork()){
                            Toast.makeText(getApplicationContext(), "The data you are viewing is old. We recommend you switch on your network to get the new data", Toast.LENGTH_SHORT).show();
                        }

                        if( (getCurrentWeek() != weekStored) && receiver.isNetwork()){
                            Toast.makeText(getApplicationContext(), "Fetching the new data for the new week!", Toast.LENGTH_SHORT).show();
                            String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
                            String urlMenu = getDatedMenuUrl();
                            String urlWatcardVendors = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=WatcardVendors&output=json";
                            new AsyncDataFetcher(SplashScreen.this).execute(urlMenu, urlLocations, urlWatcardVendors); 
                        }

                        else{
                            
                            ArrayList<RestaurantMenuObject> restaurantMenu = null;
                            RestaurantObject[] restaurantLocations = null;
                            WatcardVendorObject[] watcardVendors = null;
                            try {
                                restaurantMenu = (ArrayList<RestaurantMenuObject>) InternalStorage.readObject(SplashScreen.this, "menu");
                                restaurantLocations = (RestaurantObject[]) InternalStorage.readObject(SplashScreen.this, "location");
                                watcardVendors = (WatcardVendorObject[]) InternalStorage.readObject(SplashScreen.this, "watcard_vendor");

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                            if((restaurantMenu != null) && (restaurantLocations != null)){
                                RestaurantLocationHolder.getInstance(restaurantLocations);
                                RestaurantMenuHolder.getInstance(restaurantMenu);
                                WatcardVendorHolder.getInstance(watcardVendors);
                                cacheLoad = true;
                            }
                                                        
                        }


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }


    }

    private void showErrorPage() {

        Intent intent = new Intent(this, ErrorPage.class);
        startActivity(intent);
        finish();

    }

}
