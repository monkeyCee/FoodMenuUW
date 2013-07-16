package ca.uwaterloo.uwfoodservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class ParseLocationData {
	
	private String data = "data";
	private String result = "result";
	private String name = "Name";
	private String location = "Location";
	private String hours = "Hours";
	private String response = "response";
	private Context context;
	private DatabaseHandler db; 
	private RestarauntLocationHolder holder;
	
	public ParseLocationData(Context context){
		this.context = context;
		db = DatabaseHandler.getInstance(context);
	}
	
	public void Parse(JSONObject json){
		
		try {
			JSONObject response_object = json.getJSONObject(response);
			JSONObject data_array = response_object.getJSONObject(data);
			JSONArray result_array = data_array.getJSONArray(result);
			db.truncate();
			
			for(int i=0; i < result_array.length(); i++){
				
				JSONObject restaurant_details = result_array.getJSONObject(i);
				String restaurant_name = restaurant_details.getString(name);
				String location_name = restaurant_details.getString(location);
				JSONObject hour_details = restaurant_details.getJSONObject(hours);
				result_array = hour_details.getJSONArray(result);
				String details = result_array.getString(0);
				result_array = data_array.getJSONArray(result);
				
				Log.d(restaurant_name, location_name);
				Log.d(restaurant_name, details);
								
				if(db != null){
					Log.d("Insert: ", "Inserting .."); 
					db.addRestaurant(new RestaurantObject(i, restaurant_name, location_name, details));
				}
				
				Log.d("Reading: ", "Reading all contacts..");
				RestaurantObject object_read = db.getRestaurant(i+1);
				Log.d(object_read.getRestaurant(), object_read.getLocation());
				Log.d(object_read.getRestaurant(), object_read.getTimings());
				Log.d(object_read.getRestaurant(), Integer.toString(object_read.getID()));
				
				Log.d("Count", Integer.toString(db.getCount()));
				
				
			}
			
			holder = RestarauntLocationHolder.getInstance(context);
				
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
}