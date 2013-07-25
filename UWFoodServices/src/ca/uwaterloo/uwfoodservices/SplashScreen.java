package ca.uwaterloo.uwfoodservices;

import org.json.JSONObject;

import ca.uwaterloo.uwfoodservicesutility.ParseMenuData;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class SplashScreen extends Activity {
	
	static ParseLocationData locationParser;
	static ParseMenuData menuParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		menuParser = new ParseMenuData();
		locationParser = new ParseLocationData(this);
		
		String urlLocations = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
		String urlMenu = "http://api.uwaterloo.ca/public/v2/foodservices/2013/29/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
		Intent intent = new Intent(this, MainScreen.class);
		new LocationList().execute(urlMenu, urlLocations);
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	private static class LocationList extends AsyncTask<String, Void, JSONObject[]> {


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
			}
			else{
				Log.d("Object is null", "Null");
			}
			
       }
		
	}
	
}
