package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

public class RestarauntLocationHolder extends AsyncTask<Void, Void, Void>{
	
	public Integer[] image_list = {R.drawable.bonappetit, R.drawable.browsers, R.drawable.brubakers, R.drawable.ceit, R.drawable.eye_opener,
			R.drawable.festivalfare, R.drawable.liquidassets, R.drawable.mls, R.drawable.mudies, R.drawable.pas, R.drawable.pastryplus,
			R.drawable.revelation, R.drawable.subway, R.drawable.tims, R.drawable.universityclub, R.drawable.foodservices, R.drawable.williams_0};
	
	public String[] restaurant_list = {"Bon Appetit", "Browsers Café", "Brubakers", "CEIT Café", "Eye-Opener Café", "Festival Fare",
			"Liquid Assets Café", "ML's Coffee Shop", "Mudie's", "PAS Lounge", "Pastry Plus", "REVelation", "Subway", "Tim Hortons", "University Club",
			"UW Food Admin Office", "Williams Fresh Café"};

	public String[] location_list = {"Davis Centre", "Dana Porter Library", "Student Life Centre", "CEIT Building","Optometry Building",
			"Above the Bookstore in SCH", "Hagey Hall", "Modern Languages", "Village 1", "PAS Building","Various", "Ron Eydt Village",
			"Student Life Centre", "Various", " ", " ", "Environment 3"};
	
	public Map<String, String> locationMap = new HashMap<String, String>();
	public Map<String, Integer> imageMap = new HashMap<String, Integer>();
	
	public ArrayList<ArrayList<String>> restaurant_menu_list = new ArrayList<ArrayList<String>>(); // List of restaurants with menus for week specified (FetchRestaurants)
	public ArrayList<ArrayList<String>> restaurant_location_list = new ArrayList<ArrayList<String>>();  // List of restaurants with locations (FetchHours)
	
	public String checkName(String name) {
		if (name.equals("Browsers Cafe")) { return "Browsers Café"; }
		if (name.equals("CEIT Cafe")) { return "CEIT Café"; }
		if (name.equals("Eye Opener Cafe")) { return "Eye-Opener Café"; }
		if (name.equals("LA Cafe")) { return "Liquid Assets Café"; }
		if (name.equals("UW Food Services Administrative Office")) { return "UW Food Admin Office"; }
		if (name.equals("Williams Fresh Cafe")) { return "Williams Fresh Café"; }
		Log.d("YES", "restaurant YES3");
		return name;
	}
	
	@Override
    protected Void doInBackground(Void... params) {
        try {
        	
        	for (int i = 0; i < restaurant_list.length; i ++) {
        		locationMap.put(restaurant_list[i], location_list[i]);
        		imageMap.put(restaurant_list[i], image_list[i]);
        	}
        	
        	restaurant_menu_list.add(DataFetcher.FetchRestaurants(27, "98bbbd30b3e4f621d9cb544a790086d6"));
        	restaurant_menu_list.add(new ArrayList<String>());
        	restaurant_menu_list.add(new ArrayList<String>());
        	for (int i = 0; i < restaurant_menu_list.get(0).size(); i ++) {
        		restaurant_menu_list.get(0).set(i, checkName(restaurant_menu_list.get(0).get(i)));
        		restaurant_menu_list.get(1).add(locationMap.get(restaurant_menu_list.get(0).get(i)));
        		restaurant_menu_list.get(2).add(imageMap.get(restaurant_menu_list.get(0).get(i)).toString());
        	}
        	
        	Log.d(restaurant_menu_list + "", "restaurant menu list");
        	
        	restaurant_location_list = DataFetcher.FetchHours("98bbbd30b3e4f621d9cb544a790086d6");
        	
        	Log.d(DataFetcher.FetchRestaurants(27, "98bbbd30b3e4f621d9cb544a790086d6") + "", "restaurants");
            Log.d(DataFetcher.FetchMealTimes("98bbbd30b3e4f621d9cb544a790086d6") + "", "mealTimes");
			Log.d(DataFetcher.FetchHours("98bbbd30b3e4f621d9cb544a790086d6") + "", "hours");
        	
        	for (int i = 0; i < restaurant_location_list.size(); i ++) {
        		Log.d(restaurant_location_list.get(i) + "", "restaurant imageMap - get(i)");
        		Log.d(restaurant_location_list.get(i).get(0) + "", "restaurant imageMap - get(i).get(0)");
        		restaurant_location_list.get(i).set(0, checkName(restaurant_location_list.get(i).get(0)));
        		Log.d(imageMap.get(restaurant_location_list.get(i).get(0)).toString(), "restaurant imageMap");
        		Log.d(i+ "", "restaurant imageMap - i1");
        		restaurant_location_list.get(i).add(imageMap.get(restaurant_location_list.get(i).get(0)).toString()); // BUGGY?
        		Log.d(i+ "", "restaurant imageMap - i2");
        	}
        	
        	Log.d(restaurant_location_list + "", "restaurant location list");
        	
            ArrayList<ArrayList<String>> fetchMealTimes = DataFetcher.FetchMealTimes("98bbbd30b3e4f621d9cb544a790086d6");
            ArrayList<ArrayList<String>> fetchHours = DataFetcher.FetchHours("98bbbd30b3e4f621d9cb544a790086d6");
            
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
