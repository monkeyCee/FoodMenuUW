package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import ca.uwaterloo.uwfoodservices.InitialiseSingleton;

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

    private ArrayList<RestaurantMenuObject> restaurantMenu;
    protected InitialiseSingleton init;

    public ParseMenuData(Activity activity){
        init = (InitialiseSingleton)activity.getApplication();
    }

    public String capitalize(String product_name) {
        String[] tokens = product_name.split("\\s");
        product_name = "";

        for(int i = 0; i < tokens.length; i++){
            if (tokens[i].length() > 0) {
                if (!(tokens[i].equals("w/") || tokens[i].equals("and") || tokens[i].equals("on") 
                        || tokens[i].equals("with") || tokens[i].equals("de") || tokens[i].equals("a"))
                        || tokens[i].equals("served") || tokens[i].equals("in") || tokens[i].equals("of")) {
                    char capLetter = Character.toUpperCase(tokens[i].charAt(0));
                    product_name +=  " " + capLetter + tokens[i].substring(1, tokens[i].length());
                } else {
                    product_name +=  " " + tokens[i];
                }
            }
        }
        return product_name;
    }

    public String checkProductName(String product_name, String outlet_name) {
        product_name = product_name.trim();
        if (outlet_name.equals("Bon Appetit")) {
            if (product_name.matches("^BA\\..*")) {
                product_name = product_name.replace("BA.", "");
            } else if (product_name.matches("^BA,.*")) {
                product_name = product_name.replace("BA,", "");
            } else if (product_name.matches("^BA -.*")) {
                product_name = product_name.replace("BA", "");
            } else if (product_name.matches("^BA.*")) {
                product_name = product_name.replace("BA", "");
            }
        }
        else if (outlet_name.equals("Festival Fare")) {
            if (product_name.matches("^FF\\..*")) {
                product_name = product_name.replace("FF.", "");
            } else if (product_name.matches("^FF,.*")) {
                product_name = product_name.replace("FF,", "");
            } else if (product_name.matches("^FF -.*")) {
                product_name = product_name.replace("FF,", "");
            } else if (product_name.matches("^FF.*")) {
                product_name = product_name.replace("FF", "");
            }
        }
        else if (outlet_name.equals("Liquid Assets Caf�")) {
            if (product_name.matches("^LA\\..*")) {
                product_name = product_name.replace("LA.", "");
            } else if (product_name.matches("^LA,.*")) {
                product_name = product_name.replace("LA,", "");
            } else if (product_name.matches("^LA -.*")) {
                product_name = product_name.replace("LA,", "");
            } else if (product_name.matches("^LA.*")) {
                product_name = product_name.replace("LA", "");
            }
        }
        product_name = product_name.trim();

        if (product_name.matches(".*,V$")) {
            product_name = product_name.replace(",V", "");
        } else if(product_name.matches(".* V$")) {
            product_name = product_name.replace(" V", "");
        }
        product_name = product_name.trim();

        if (product_name.contains(" and ")) {
            product_name = product_name.replace(" and ", " & ");
        }
        product_name = product_name.trim();

        if (product_name.contains("w/")) {
            if(product_name.charAt(product_name.indexOf("w/") + 2) != ' ') {
                product_name = product_name.substring(0, product_name.indexOf("w/") + 2) + " " + product_name.substring(product_name.indexOf("w/") + 2);
            }
        }
        product_name = product_name.trim();

        if (product_name.endsWith(",") || product_name.endsWith(".")) {
            product_name = product_name.substring(0, product_name.length() - 1);
        }

        product_name = capitalize(product_name);

        product_name = product_name.trim();
        return product_name;
    }

    public void Parse(JSONObject json){

        restaurantMenu = new ArrayList<RestaurantMenuObject>();

        try {
            JSONObject meta = json.getJSONObject(TAG_META);

            if (meta.getString("status").equals("200")){
                JSONObject data = json.getJSONObject(TAG_DATA);
                JSONArray outlets = data.getJSONArray(TAG_OUTLETS);
                JSONObject restaurant;
                JSONArray menu;

                JSONObject day;
                JSONObject meals;
                JSONArray lunch;
                JSONArray dinner;
                String product_name;
                Integer product_id = null;
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
                                product_name = checkProductName(lunch.getJSONObject(k).getString(TAG_PRODUCT_NAME), outlet_name);

                                product_id = MenuUtilities.getInteger(lunch.getJSONObject(k).getString(TAG_PRODUCT_ID));
                                diet_type = lunch.getJSONObject(k).getString(TAG_DIET_TYPE);

                                // Check for 'Chef Special' which has a product id but does not contain any product info
                                if (product_id != null) {
                                    if (product_id == 2439) {
                                        product_id = null;
                                    // Check for a product_if of '2041' which is incorrectly given to menu items which
                                    // are not 'Falafel Marinara'
                                    } else if (product_id == 2041 && product_name != "Falafel Marinara") {
                                        product_id = null;
                                        diet_type = "null";
                                    }
                                }

                                lunchList.add(new RestaurantMenuItem(product_name, product_id, diet_type));

                            }
                        }
                        // Dinner
                        if (meals.has(TAG_DINNER) && (meals.getJSONArray(TAG_DINNER).length() > 0)) {
                            dinner = meals.getJSONArray(TAG_DINNER);
                            for (int k = 0; k < dinner.length(); k ++) {
                                product_name = checkProductName(dinner.getJSONObject(k).getString(TAG_PRODUCT_NAME), outlet_name);

                                product_id = MenuUtilities.getInteger(dinner.getJSONObject(k).getString(TAG_PRODUCT_ID));
                                diet_type = dinner.getJSONObject(k).getString(TAG_DIET_TYPE);

                                // Check for 'Chef Special' which has a product id but does not contain any product info
                                if (product_id != null) {
                                    if (product_id == 2439) {
                                        product_id = null;
                                    // Check for a product_if of '2041' which is incorrectly given to menu items which
                                    // are not 'Falafel Marinara'
                                    } else if (product_id == 2041 && product_name != "Falafel Marinara") {
                                        product_id = null;
                                        diet_type = "null";
                                    }
                                }
                                
                                

                                dinnerList.add(new RestaurantMenuItem(product_name, product_id, diet_type));
                            }
                        }

                        if (lunchList.size() == 0) { lunchList = null; }
                        if (dinnerList.size() == 0) { dinnerList = null; }
                        menuArray[position] = new DailyMenu(lunchList, dinnerList); // THIS ONE
                    }

                    if (lunchList != null) {
                        if (lunchList.size() == 0) { lunchList = null; }
                    }
                    if (dinnerList != null) {
                            if (dinnerList.size() == 0) { dinnerList = null; }
                    }
                    menuArray[position] = new DailyMenu(lunchList, dinnerList);
                    restaurantMenu.add(new RestaurantMenuObject(outlet_id, outlet_name, location_name, image, menuArray));
                }
                //holder = RestaurantMenuHolder.getInstance(restaurantMenu);
                init.initMenus(restaurantMenu);
            }

            init.initMenus(restaurantMenu);
            //holder = RestaurantMenuHolder.getInstance(restaurantMenu);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}