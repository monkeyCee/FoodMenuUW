package ca.uwaterloo.uwfoodservices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONObject;

import android.app.Activity;
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
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.uwaterloo.uwfoodservicesutility.InternalStorage;
import ca.uwaterloo.uwfoodservicesutility.ParseMenuData;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuObject;

public class SplashScreen extends Activity {

    static ParseLocationData locationParser;
    static ParseMenuData menuParser;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor prefEditor;

    public static final String WIFI = "Wi-Fi Only";
    public static final String BOTH = "Both Wi-Fi and Data";
    public static final String DATA = "Data Only";

    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;
    private static boolean dataConnected = false;
    public static boolean refreshDisplay = true;

    public static String networkPref = null;
    public static boolean cachePref;
    public static int weekStored;

    public static String refreshPref = null;
    
    public static String formattedDate;
    static int weekDay;
    static Calendar calendar;
    static SimpleDateFormat simpleDateFormat;

    private NetworkReceiver receiver = new NetworkReceiver();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);	

		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkReceiver();
		this.registerReceiver(receiver, filter);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        networkPref = sharedPrefs.getString("connection_type_preference", "Both Wi-Fi and Data");
        cachePref = sharedPrefs.getBoolean("save_data_preference", true);
        weekStored = sharedPrefs.getInt("storedWeek", -1);
        refreshPref = sharedPrefs.getString("refresh", "");
        Log.d(networkPref, "NETWORK PREF 1");

        updateConnectedFlags();

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
                        if((RestaurantLocationHolder.getInstance(SplashScreen.this) == null) || (RestaurantMenuHolder.getInstance() == null)){
                            handler.postDelayed(this, 1000);
                        }
                    }
                };

                handler.postDelayed(r, 2000);

                Intent intent = new Intent(SplashScreen.this, MainScreen2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
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
            Log.d("Url", urls[0]);
            JSONParser json_parse = new JSONParser();
            JSONObject[] jsonObjectArray = new JSONObject[2];
            jsonObjectArray[0] = json_parse.getJSONFromUrl(urls[0]);
            jsonObjectArray[1] = json_parse.getJSONFromUrl(urls[1]);
            return jsonObjectArray;
        }

        @Override
        protected void onPostExecute(JSONObject[] jObjArray) {
            if(jObjArray != null){
                menuParser.Parse(jObjArray[0]);	
                locationParser.Parse(jObjArray[1]);	

                try {
                    InternalStorage.writeObject(context, "menu", RestaurantMenuHolder.getInstance().restaurantMenu);
                    InternalStorage.writeObject(context, "location", RestaurantLocationHolder.getInstance(context).objects);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.d("Object is null", "Null");
            }

        }

    }

    public String getDatedMenuUrl() {
        // Date handling
        prefEditor = sharedPrefs.edit();
        calendar = Calendar.getInstance();
        Log.d(calendar.getTime() + "", "current time");

        simpleDateFormat = new SimpleDateFormat("MMMMMMMMM dd", Locale.CANADA);
        formattedDate = simpleDateFormat.format(calendar.getTime());

        String weekInYear = (new SimpleDateFormat("w", Locale.CANADA)).format(calendar.getTime());

        Log.d(formattedDate + "", "current time - formmated");

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

        Log.d(Integer.parseInt(weekInYear) + "", "current time - weekInYear");

        return "http://api.uwaterloo.ca/public/v2/foodservices/2013/" + Integer.parseInt(weekInYear) + "/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
    }

    public int getCurrentWeek(){

        calendar = Calendar.getInstance();
        String weekInYear = (new SimpleDateFormat("w", Locale.CANADA)).format(calendar.getTime());
        Log.d("Current week - Called from getCurrentWeek", weekInYear);
        return Integer.parseInt(weekInYear);

    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // Wifi or Data Connected
            if (BOTH.equals(networkPref) && (networkInfo != null) && ((networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                    || (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE))) {
                refreshDisplay = true;
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.data_connected, Toast.LENGTH_SHORT).show();
                }

                // Wifi Connected
            } else if (WIFI.equals(networkPref) && (networkInfo != null) && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
                refreshDisplay = true;
                Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();

                // Data Connected
            } else if (DATA.equals(networkPref) && (networkInfo != null) && (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)) {
                refreshDisplay = true;
                Toast.makeText(context, R.string.data_connected, Toast.LENGTH_SHORT).show();

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

    private void updateConnectedFlags() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null) {
            Log.d(activeInfo.isConnected() + "", "network connected");
        } else {
            Log.d(null + "", "network connected");
        }
        if ((activeInfo != null) && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }

	@SuppressWarnings("unchecked")
    private void loadData() {
    	
    	menuParser = new ParseMenuData();
		locationParser = new ParseLocationData(this);
        
		// If the refreshPref comes from locations, menu, you load from network, then open either menu, locations
		//		If there is no network, show error page
		// Else it means it is a start up call and you can resume normally
		
		
		if(refreshPref.equals("locations") || refreshPref.equals("menu")){
			
			if (((networkPref.equals(BOTH)) && (wifiConnected || mobileConnected))
	                || ((networkPref.equals(WIFI)) && (wifiConnected)) || ((networkPref.equals(DATA)) && (mobileConnected))) {
				
					Toast.makeText(getApplicationContext(), "Refreshing using network", Toast.LENGTH_SHORT).show();
	        
	    			String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
	    			String urlMenu = getDatedMenuUrl();
	    			new AsyncDataFetcher(SplashScreen.this).execute(urlMenu, urlLocations);	
	    			
	    			
	    			final Handler handler = new Handler();
		    		final Runnable r = new Runnable()
		    		{
		    		    public void run() 
		    		    {
		    		    	if(RestaurantLocationHolder.getInstance(SplashScreen.this) == null || RestaurantMenuHolder.getInstance() == null){
		    		    		handler.postDelayed(this, 1000);
		    		    	}
		    		    }
		    		};

		    		handler.postDelayed(r, 2000);
	    			if(refreshPref.equals("locations")){
	    				Intent intent = new Intent(this, LocationHours.class);
	    				prefEditor.putString("refresh", "locations");
	    	            prefEditor.commit();
	    				startActivity(intent);
	    			}
	    			else{
	    				if(refreshPref.equals("menu")){
	    					String restaurant = sharedPrefs.getString("restaurant", "");
	    					int position = sharedPrefs.getInt("position", -1);
		    				Intent intent = new Intent(this, MenuLists.class);
		    		        prefEditor.putString("refresh", "");
		    		        prefEditor.putString("restaurant", "");
		    		        prefEditor.putInt("position", -1);
		    		        prefEditor.commit();
		    				intent.putExtra("Restaurant Name", restaurant);
		    				intent.putExtra("Restaurant Position", position);
		    				startActivity(intent);
		    			}
	    			}
	        }
			
			else{ 
				Toast.makeText(getApplicationContext(), "Cannot refresh because there is no network", Toast.LENGTH_SHORT).show();
				showErrorPage(); }	
			
		}
		
		else{
			
			StartSplashScreen();
			
	    	if (!cachePref) {
	    		
	    		InternalStorage.deleteObject(SplashScreen.this, "menu");
				InternalStorage.deleteObject(SplashScreen.this, "location");
				
				//If network, get new data else error page				
				if (((networkPref.equals(BOTH)) && (wifiConnected || mobileConnected))
		                || ((networkPref.equals(WIFI)) && (wifiConnected)) || ((networkPref.equals(DATA)) && (mobileConnected))) {
					
						Toast.makeText(getApplicationContext(), "Cache Pref off, Loading from network", Toast.LENGTH_SHORT).show();
		        
		    			String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
		    			String urlMenu = getDatedMenuUrl();
		    			new AsyncDataFetcher(SplashScreen.this).execute(urlMenu, urlLocations);	
		    			
		        }
				
				else{ 
					Toast.makeText(getApplicationContext(), "Cache Pref off, No network", Toast.LENGTH_SHORT).show();
					showErrorPage(); }			
			}
	    	
	    	//Else use cache to get data 
	    		//Within here, check if cache has data
	    			//if yes, load it... before loading cached data, check if it is old
	    			//else, check if network exists
	    				//if yes, load data
	    				// else error page
	    	
	    	else{

	    		try {
					if(!(InternalStorage.cacheExists(SplashScreen.this, "menu")) || !(InternalStorage.cacheExists(SplashScreen.this, "location"))){
						
						if (((networkPref.equals(BOTH)) && (wifiConnected || mobileConnected))
				                || ((networkPref.equals(WIFI)) && (wifiConnected)) || ((networkPref.equals(DATA)) && (mobileConnected))) {
														
								Toast.makeText(getApplicationContext(), "Cache Pref On. No Cached data. Getting data from network", Toast.LENGTH_SHORT).show();
				        
				    			String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
				    			String urlMenu = getDatedMenuUrl();
				    			new AsyncDataFetcher(SplashScreen.this).execute(urlMenu, urlLocations);			
				        }
						else{ 
							Toast.makeText(getApplicationContext(), "Cache Pref On. No Cache. No Network", Toast.LENGTH_SHORT).show();
							showErrorPage(); }
					}
					
					else{
						
						Log.d("Week stored", Integer.toString(weekStored));
						if( getCurrentWeek() != weekStored){
							Toast.makeText(getApplicationContext(), "The data you are viewing is old", Toast.LENGTH_SHORT).show();
						}
						
						Toast.makeText(getApplicationContext(), "Cache Pref On. Cache available", Toast.LENGTH_SHORT).show();
						
						ArrayList<RestaurantMenuObject> restaurantMenu = null;
				    	RestaurantObject[] restaurantLocations = null;
				    	try {
							restaurantMenu = (ArrayList<RestaurantMenuObject>) InternalStorage.readObject(SplashScreen.this, "menu");
							restaurantLocations = (RestaurantObject[]) InternalStorage.readObject(SplashScreen.this, "location");
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
				    			
						if(restaurantMenu != null && restaurantLocations != null){
							RestaurantLocationHolder.getInstance(SplashScreen.this, restaurantLocations);
							RestaurantMenuHolder.getInstance(restaurantMenu);
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
        setContentView(R.layout.activity_connection_cache);
        Typeface tf = Typeface.createFromAsset(SplashScreen.this.getAssets(),
                "Roboto-Light.ttf");
        TextView text = (TextView) findViewById(R.id.errortext);
        text.setTypeface(tf);

        Button refresh = (Button) findViewById(R.id.ready);
        refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        Button settings = (Button) findViewById(R.id.settingsPhone);
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);	
            }
        });

    }

}
