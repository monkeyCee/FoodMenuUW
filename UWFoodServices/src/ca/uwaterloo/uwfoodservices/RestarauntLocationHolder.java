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
			R.drawable.revelation, R.drawable.subway, R.drawable.tims, R.drawable.universityclub, R.drawable.williams_0};
	
	public String[] restaurant_list = {"Bon Appetit", "Browsers Café", "Brubakers", "CEIT Café", "Eye-Opener", "Festival Fare",
			"Liquid Assets", "MLS Café", "Mudie's", "PAS", "Pastry Plus", "REVelation", "Subway", "Tim Hortons", "University Club",
			"Williams Café"};

	public String[] location_list = {"Davis Centre", "REV", "Davis Centre", "REV","Davis Centre", "REV","Davis Centre", "REV",
			"Davis Centre", "REV","Davis Centre", "REV","Davis Centre", "REV","Davis Centre", "REV"};
	
	public Map<String, String> locationMap = new HashMap<String, String>();
	public Map<String, Integer> imageMap = new HashMap<String, Integer>();
	
	for (int i = 0; i < restaurant_list.length(); i ++) {
		locationMap.put(restaurant_list[i], location_list[i]);
		imageMap.put(restaurant_list[i], image_list[i]);
	}
	
	public ArrayList<ArrayList<String>> restaurant_menu_list = new ArrayList<ArrayList<String>>(); // List of restaurants with menus for week specified (FetchRestaurants)
	public ArrayList<ArrayList<String>> restaurant_location_list = new ArrayList<ArrayList<String>>();  // List of restaurants with locations (FetchHours)
	
	@Override
    protected Void doInBackground(Void... params) {
        try {
        	restaurant_menu_list.add(DataFetcher.FetchRestaurants(27, "98bbbd30b3e4f621d9cb544a790086d6"));
        	restaurant_menu_list.add(new ArrayList<String>());
        	for (int i = 0; i < restaurant_menu_list.get(0).size(); i ++) {
        		//for ()
        	}
        	
            ArrayList<ArrayList<String>> fetchMealTimes = DataFetcher.FetchMealTimes("98bbbd30b3e4f621d9cb544a790086d6");
            ArrayList<ArrayList<String>> fetchHours = DataFetcher.FetchHours("98bbbd30b3e4f621d9cb544a790086d6");
            
            
            
            Log.d(DataFetcher.FetchRestaurants(27, "98bbbd30b3e4f621d9cb544a790086d6") + "", "restaurants");
            Log.d(DataFetcher.FetchMealTimes("98bbbd30b3e4f621d9cb544a790086d6") + "", "mealTimes");
			Log.d(DataFetcher.FetchHours("98bbbd30b3e4f621d9cb544a790086d6") + "", "hours");
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
