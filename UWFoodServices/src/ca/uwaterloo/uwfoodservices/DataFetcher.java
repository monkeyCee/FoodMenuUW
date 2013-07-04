package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DataFetcher {
	
	public static String url = "http://api.uwaterloo.ca/public/v2/foodservices/2013/26/menu.json?key=98bbbd30b3e4f621d9cb544a790086d6";
	
	private static final String TAG_META = "meta";
	private static final String TAG_MESSAGE = "message";
	
	private static final String TAG_DATA = "data";
	private static final String TAG_OUTLETS = "outlets";
	
	private static final String TAG_OUTLET_NAME = "outlet_name";
	
	private static final String TAG_OUTLET_ID = "outlet_id";
	private static final String TAG_HAS_BREAKFAST = "has_breakfast";
	private static final String TAG_HAS_LUNCH = "has_lunch";
	private static final String TAG_HAS_DINNER = "has_dinner";
	
	private static final String TAG_RESPONSE = "response";
	private static final String TAG_RESULT = "result";
	private static final String TAG_HOURS = "Hours";
	
	private static final String TAG_NAME = "Name";
	private static final String TAG_ID = "ID";
	private static final String TAG_LOCATION = "Location";
	
	
	private DataFetcher() {
		throw new AssertionError();
	}
	
	public static ArrayList<String> FetchRestaurants(int week, String key) throws JSONException {
		
		ArrayList<String> restaurants = new ArrayList<String>();
		
		url = "http://api.uwaterloo.ca/public/v2/foodservices/2013/" + week + "/menu.json?key=" + key;
		
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();
				
		// Getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		JSONObject meta = json.getJSONObject(TAG_META);
		String status = meta.getString("status");
		if (status.equals("200")) {
			JSONObject data = json.getJSONObject(TAG_DATA);
			JSONArray outlets = data.getJSONArray(TAG_OUTLETS);
			for (int i = 0; i < outlets.length(); i ++) {
				restaurants.add(outlets.getJSONObject(i).getString(TAG_OUTLET_NAME));
			}
		}
		
		return restaurants;
	}
	
	public static ArrayList<ArrayList<String>> FetchMealTimes(String key) throws JSONException {
		
		ArrayList<ArrayList<String>> mealTimes = new ArrayList<ArrayList<String>>();
		
		url = "http://api.uwaterloo.ca/public/v2/foodservices/outlets.json?key=" + key;
		
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();
				
		// Getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		JSONObject meta = json.getJSONObject(TAG_META);
		String status = meta.getString("status");
		if (status.equals("200")) {
			JSONArray data = json.getJSONArray(TAG_DATA);
			for (int i = 0; i < data.length(); i ++) {
				mealTimes.add(new ArrayList<String>());
				mealTimes.get(i).add(data.getJSONObject(i).getString(TAG_OUTLET_NAME));
				mealTimes.get(i).add(data.getJSONObject(i).getString(TAG_OUTLET_ID));
				mealTimes.get(i).add(data.getJSONObject(i).getString(TAG_HAS_BREAKFAST));
				mealTimes.get(i).add(data.getJSONObject(i).getString(TAG_HAS_LUNCH));
				mealTimes.get(i).add(data.getJSONObject(i).getString(TAG_HAS_DINNER));
			}
		}
		
		return mealTimes;
	}
	
	public static ArrayList<ArrayList<String>> FetchHours(String key) throws JSONException {
		
		ArrayList<ArrayList<String>> hoursResult = new ArrayList<ArrayList<String>>();
		
		url = "http://api.uwaterloo.ca/public/v1/?key=" + key + "&service=FoodServices&output=json";
		
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();
				
		// Getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		JSONObject response = json.getJSONObject(TAG_RESPONSE);
		JSONObject meta = response.getJSONObject(TAG_META);
		String status = meta.getString("Status");
		if (status.equals("200")) {
			JSONObject data = response.getJSONObject(TAG_DATA);
			JSONArray result = data.getJSONArray(TAG_RESULT);
			for (int i = 0; i < result.length(); i ++) {
				hoursResult.add(new ArrayList<String>());
				hoursResult.get(i).add(result.getJSONObject(i).getString(TAG_NAME));
				hoursResult.get(i).add(result.getJSONObject(i).getString(TAG_ID));
				hoursResult.get(i).add(result.getJSONObject(i).getString(TAG_LOCATION));
				JSONObject hours = result.getJSONObject(i).getJSONObject(TAG_HOURS);
				JSONArray result2 = hours.getJSONArray(TAG_RESULT);
				for (int j = 0; j < result2.length(); j ++) {
					hoursResult.get(i).add(result2.get(j).toString());
				}
			}
		}
		
		return hoursResult;
	}
}
