package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ParseMenuData {

    private static final String TAG_META = "meta";

    private static final String TAG_DATA = "data";
    private static final String TAG_OUTLETS = "outlets";
    //private static final String TAG_BONAPPETIT = "BonAppetit";
    private static final String TAG_MENU = "menu";

    private static final String TAG_OUTLET_ID = "outlet_id";
    private static final String TAG_OUTLET_NAME = "outlet_name";

    private static final String TAG_DAY = "day";

    private static final String TAG_MEALS = "meals";
    private static final String TAG_LUNCH = "lunch";
    private static final String TAG_DINNER = "dinner";

    private static final String TAG_PRODUCT_NAME = "product_name";
    private static final String TAG_PRODUCT_ID = "product_id";
    private static final String TAG_DIET_TYPE = "diet_type";

    @SuppressWarnings("unused")
    private static RestaurantMenuHolder holder;
    private ArrayList<RestaurantMenuObject> restaurantMenu;

    public ParseMenuData(){
    }

    public void Parse(JSONObject json){

        restaurantMenu = new ArrayList<RestaurantMenuObject>();

        try {
            JSONObject meta = json.getJSONObject(TAG_META);

            JSONObject data = json.getJSONObject(TAG_DATA);
            JSONArray outlets = data.getJSONArray(TAG_OUTLETS);
            JSONObject restaurant;
            JSONArray menu;

            JSONObject day;
            JSONObject meals;
            JSONArray lunch;
            JSONArray dinner;
            String product_name;
            Integer product_id;
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

            for(int i = 0; i < outlets.length(); i++){

                menuArray = new DailyMenu[7];
                for (int j = 0; j < 7; j++) {
                    menuArray[j] = new DailyMenu(null, null);
                }

                lunchList = new ArrayList<RestaurantMenuItem>();
                dinnerList = new ArrayList<RestaurantMenuItem>();

                restaurant = outlets.getJSONObject(i);
                outlet_id = Integer.parseInt(restaurant.getString(TAG_OUTLET_ID)); 
                outlet_name = MenuUtilities.checkName(restaurant.getString(TAG_OUTLET_NAME)); 

                location_name = "";
                image = MenuUtilities.setImageHash().get(outlet_name);

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
                    if (meals.has(TAG_LUNCH) && (meals.getJSONArray(TAG_LUNCH).length() > 0)) {
                        lunch = meals.getJSONArray(TAG_LUNCH);
                        for (int k = 0; k < lunch.length(); k ++) {
                            product_name = lunch.getJSONObject(k).getString(TAG_PRODUCT_NAME);
                            
                            product_id = MenuUtilities.getInteger(lunch.getJSONObject(k).getString(TAG_PRODUCT_ID));
                            Log.d(lunch.getJSONObject(k).getString(TAG_PRODUCT_ID), "GETINTEGER PRODUCT GET STRING");
                            Log.d(product_id + "", "GETINTEGER PRODUCT ID");
                            diet_type = lunch.getJSONObject(k).getString(TAG_DIET_TYPE);

                            lunchList.add(new RestaurantMenuItem(product_name, product_id, diet_type));

                        }
                    }
                    // Dinner
                    if (meals.has(TAG_DINNER) && (meals.getJSONArray(TAG_DINNER).length() > 0)) {
                        dinner = meals.getJSONArray(TAG_DINNER);
                        for (int k = 0; k < dinner.length(); k ++) {
                            product_name = dinner.getJSONObject(k).getString(TAG_PRODUCT_NAME);

                            product_id = MenuUtilities.getInteger(dinner.getJSONObject(k).getString(TAG_PRODUCT_ID));
                            diet_type = dinner.getJSONObject(k).getString(TAG_DIET_TYPE);

                            dinnerList.add(new RestaurantMenuItem(product_name, product_id, diet_type));
                        }
                    }

                    if (lunchList.size() == 0) { lunchList = null; }
                    if (dinnerList.size() == 0) { dinnerList = null; }
                    menuArray[position] = new DailyMenu(lunchList, dinnerList); // THIS ONE
                }
                restaurantMenu.add(new RestaurantMenuObject(outlet_id, outlet_name, location_name, image, menuArray));
            }
            holder = RestaurantMenuHolder.getInstance(restaurantMenu);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}