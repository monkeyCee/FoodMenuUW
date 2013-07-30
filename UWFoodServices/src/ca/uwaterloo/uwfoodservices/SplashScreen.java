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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		StartSplashScreen();		

		// Register BroadcastReceiver to track connection changes.
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkReceiver();
		this.registerReceiver(receiver, filter);
		
		// Gets the user's network preference settings
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieves a string value for the preferences. The second parameter
        // is the default value to use if a preference value is not found.
        networkPref = sharedPrefs.getString("connection_type_preference", "Both Wi-Fi and Data");
        cachePref = sharedPrefs.getBoolean("save_data_preference", true);
        
        Log.d(networkPref, "NETWORK PREF 1");
        
        updateConnectedFlags();

        // Only loads the page if refreshDisplay is true. Otherwise, keeps previous
        // display. For example, if the user has set "Wi-Fi only" in prefs and the
        // device loses its Wi-Fi connection midway through the user using the app,
        // you don't want to refresh the display--this would force the display of
        // an error page instead of the menu content.
        /*
        if (refreshDisplay) {
            loadPage();
        }*/
        
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
	    		    public void run() 
	    		    {
	    		    	if(RestaurantLocationHolder.getInstance(SplashScreen.this) == null || RestaurantMenuHolder.getInstance() == null){
	    		    		handler.postDelayed(this, 1000);
	    		    	}
	    		    }
	    		};

	    		handler.postDelayed(r, 2000);
	    		
	    		Intent intent = new Intent(SplashScreen.this, MainScreen.class);
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

    // Writing to files
    //======================================================
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
	

	// Date Handling
	//======================================================
	public static String formattedDate;
    static int weekDay;
    static Calendar calendar;
    static SimpleDateFormat simpleDateFormat;
    
    public String getDatedMenuUrl() {
    	// Date handling
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
		
		Log.d(Integer.parseInt(weekInYear) + "", "current time - weekInYear");
		
		return "http://api.uwaterloo.ca/public/v2/foodservices/2013/" + Integer.parseInt(weekInYear) + "/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
    }
	
	// Network Verification
	//======================================================
	public static final String WIFI = "Wi-Fi Only";
    public static final String BOTH = "Both Wi-Fi and Data";
    public static final String DATA = "Data Only";
	
    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
 // Whether there is a data connection.
    private static boolean dataConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;
    
    // The user's current network preference setting.
    public static String networkPref = null;
    public static boolean cachePref;
    
    // The BroadcastReceiver that tracks network connectivity changes.
    private NetworkReceiver receiver = new NetworkReceiver();
    
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // Wifi or Data Connected
            if (BOTH.equals(networkPref) && networkInfo != null && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
            		|| networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)) {
                refreshDisplay = true;
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                	Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(context, R.string.data_connected, Toast.LENGTH_SHORT).show();
                }
                
            // Wifi Connected
            } else if (WIFI.equals(networkPref) && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            	refreshDisplay = true;
                Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();
                
            // Data Connected
            } else if (DATA.equals(networkPref) && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
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
    
    // Checks the network connection and sets the wifiConnected and mobileConnected
    // variables accordingly.
    private void updateConnectedFlags() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null) {
        	Log.d(activeInfo.isConnected() + "", "network connected");
        } else {
        	Log.d(null + "", "network connected");
        }
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }
    
    // Uses AsyncTask subclass to download the JSON data. This avoids UI lock up. 
    // To prevent network operations from causing a delay that results in a poor 
    // user experience, always perform network operations on a separate thread from the UI.
    private void loadData() {
        
    	if (!cachePref) {
    		InternalStorage.deleteObject(SplashScreen.this, "menu");
			InternalStorage.deleteObject(SplashScreen.this, "location");
		}
    	
    	if (((networkPref.equals(BOTH)) && (wifiConnected || mobileConnected))
                || ((networkPref.equals(WIFI)) && (wifiConnected))) {
        	
        	// Load Data
        	menuParser = new ParseMenuData();
    		locationParser = new ParseLocationData(this);
    		
    		String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
    		String urlMenu = getDatedMenuUrl();
    		
    		new AsyncDataFetcher(SplashScreen.this).execute(urlMenu, urlLocations);
    		
    		    		
    		
    	

        } else {

        	loadCachedData();
        }
    }
    
    @SuppressWarnings("unchecked")
	private void loadCachedData() {
    	Log.d("Getting cached data", "+");
    	    	
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
		
		else{
			showErrorPage();
		}
    }
    
    // Displays an error if the app is unable to load content.
    private void showErrorPage() {
        setContentView(R.layout.activity_connection_cache);
        Typeface tf = Typeface.createFromAsset(SplashScreen.this.getAssets(),
	            "Roboto-Light.ttf");
        TextView text = (TextView) findViewById(R.id.errortext);
        text.setTypeface(tf);
        
        Button button = (Button) findViewById(R.id.ready);
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = getIntent();
			    finish();
			    startActivity(intent);
			}
		});
        // The specified network connection is not available. Displays error message.
        // Show: "Unable to load content. Check your network connection."
    }
    
}
