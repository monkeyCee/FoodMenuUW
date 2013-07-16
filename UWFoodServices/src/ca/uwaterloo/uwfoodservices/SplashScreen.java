package ca.uwaterloo.uwfoodservices;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class SplashScreen extends Activity {
	
	static ParseLocationData parser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		parser = new ParseLocationData(this);
		
		String url = "http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json";
		Intent intent = new Intent(this, MainScreen.class);
		new LocationList().execute(url);
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	private static class LocationList extends AsyncTask<String, Void, JSONObject> {


		@Override
		protected JSONObject doInBackground(String... urls) {
			Log.d("Url", urls[0]);
			JSONParser json_parse = new JSONParser();
			return (json_parse.getJSONFromUrl(urls[0]));
		}
		
		@Override
        protected void onPostExecute(JSONObject jObj) {
			if(jObj != null){
				parser.Parse(jObj);	
			}
			else{
				Log.d("Object is null", "Null");
			}
			
       }
		
	}
	
}
