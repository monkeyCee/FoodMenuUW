package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ca.uwaterloo.uwfoodservicesutility.DailyMenu;
import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuItem;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuObject;

public class ParseMenuData {
	
	private static final String TAG_META = "meta";
	private static final String TAG_MESSAGE = "message";
	
	private static final String TAG_DATA = "data";
	private static final String TAG_OUTLETS = "outlets";
	//private static final String TAG_BONAPPETIT = "BonAppetit";
	private static final String TAG_MENU = "menu";
	
	private static final String[] TAG_DAYS = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	private static final String TAG_OUTLET_ID = "outlet_id";
	private static final String TAG_OUTLET_NAME = "outlet_name";
	
	private static final String TAG_DAY = "day";
	
	private static final String TAG_MEALS = "meals";
	private static final String TAG_LUNCH = "lunch";
	private static final String TAG_DINNER = "dinner";
	
	private static final String TAG_PRODUCT_NAME = "product_name";
	private static final String TAG_PRODUCT_ID = "product_id";
	private static final String TAG_DIET_TYPE = "diet_type";
	
	private static RestarauntMenuHolder holder;
	private static ArrayList<RestaurantMenuObject> restaurantMenu;
	
	public ParseMenuData(){
		
	}
	
	public static void Parse(JSONObject json){
		
		restaurantMenu = new ArrayList<RestaurantMenuObject>();
		
		try {
			JSONObject meta = json.getJSONObject(TAG_META);
			Log.d(meta.getString("message"), "message");
			
			JSONObject data = json.getJSONObject(TAG_DATA);
			JSONArray outlets = data.getJSONArray(TAG_OUTLETS);
			JSONObject restaurant;
			JSONArray menu;
			
			JSONObject day;
			JSONObject meals;
			JSONArray lunch;
			JSONArray dinner;
			String product_name;
			int product_id;
			String diet_type;
			
			String weekDay;
			
			Integer outlet_id;
			String outlet_name;
			String location_name;
			Integer image;
			
			ArrayList<RestaurantMenuItem> lunchList = new ArrayList<RestaurantMenuItem>();
			ArrayList<RestaurantMenuItem> dinnerList = new ArrayList<RestaurantMenuItem>();
			
			int position = 0;
			DailyMenu[] menuArray = new DailyMenu[7];
			
			for (int i = 0; i < 7; i++) {
				menuArray[i] = new DailyMenu(null, null);
			}
			Log.d(outlets.length() + "", "outletsLength");
			
			for(int i = 0; i < outlets.length(); i++){
				
				lunchList = new ArrayList<RestaurantMenuItem>();
				dinnerList = new ArrayList<RestaurantMenuItem>();
				
				restaurant = outlets.getJSONObject(i);
				outlet_id = Integer.parseInt(restaurant.getString(TAG_OUTLET_ID)); 
				outlet_name = MenuUtilities.checkName(restaurant.getString(TAG_OUTLET_NAME)); 
				
				location_name = "";
				image = MenuUtilities.getImageHash().get(outlet_name);
				
				menu = restaurant.getJSONArray(TAG_MENU);
				
				for (int j = 0; j < menu.length(); j++) {
					
					lunchList = new ArrayList<RestaurantMenuItem>();
					dinnerList = new ArrayList<RestaurantMenuItem>();
					
					day = menu.getJSONObject(j);
					meals = day.getJSONObject(TAG_MEALS);
					weekDay = day.getString(TAG_DAY);
					
					if (weekDay.equals("Monday")) { position = 0; }
					else if (weekDay.equals("Tuesday")) { position = 1; }
					else if (weekDay.equals("Wednesday")) { position = 2; }
					else if (weekDay.equals("Thursday")) { position = 3; }
					else if (weekDay.equals("Friday")) { position = 4; }
					else if (weekDay.equals("Saturday")) { position = 5; }
					else if (weekDay.equals("Sunday")) { position = 6; }
					
					// Lunch
					if (meals.has(TAG_LUNCH) && meals.getJSONArray(TAG_LUNCH).length() > 0) {
						lunch = meals.getJSONArray(TAG_LUNCH);
						for (int k = 0; k < lunch.length(); k ++) {
							product_name = lunch.getJSONObject(k).getString(TAG_PRODUCT_NAME);
							
							Log.d(lunch.getJSONObject(k).getString(TAG_PRODUCT_ID), "PRODUCT_ID");
							
							if (lunch.getJSONObject(k).getString(TAG_PRODUCT_ID).equals("null")) {
								product_id = -1;
							} else {
								product_id = Integer.parseInt(lunch.getJSONObject(k).getString(TAG_PRODUCT_ID));
							}
							
							diet_type = lunch.getJSONObject(k).getString(TAG_DIET_TYPE);
							
							lunchList.add(new RestaurantMenuItem(product_name, product_id, diet_type));
							Log.d(lunchList.get(k).getProductName(), "result product name - lunch list");
						}
					}
					
					// Dinner
					if (meals.has(TAG_DINNER) && meals.getJSONArray(TAG_DINNER).length() > 0) {
						dinner = meals.getJSONArray(TAG_DINNER);
						for (int k = 0; k < dinner.length(); k ++) {
							product_name = dinner.getJSONObject(k).getString(TAG_PRODUCT_NAME);
							
							if (dinner.getJSONObject(k).getString(TAG_PRODUCT_ID).equals("null")) {
								product_id = -1;
							} else {
								product_id = Integer.parseInt(dinner.getJSONObject(k).getString(TAG_PRODUCT_ID));
							}
							
							diet_type = dinner.getJSONObject(k).getString(TAG_DIET_TYPE);
							
							dinnerList.add(new RestaurantMenuItem(product_name, product_id, diet_type));
						}
					}
					menuArray[position] = new DailyMenu(lunchList, dinnerList);
				}
				
				
				restaurantMenu.add(new RestaurantMenuObject(outlet_id, outlet_name, location_name, image, menuArray));
			}
			holder = RestarauntMenuHolder.getInstance(restaurantMenu);
				
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
}