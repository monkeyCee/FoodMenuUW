package ca.uwaterloo.uwfoodservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;

public class ParseLocationData {

    private String data = "data";
    private String result = "result";
    private String name = "Name";
    private String location = "Location";
    private String hours = "Hours";
    private String response = "response";
    private RestaurantObject[] location_objects;
    private String[] details;
    protected InitialiseSingleton init;

    public ParseLocationData(Activity activity){
        init = (InitialiseSingleton)activity.getApplication();
    }

    public void Parse(JSONObject json){

        try {
            JSONObject response_object = json.getJSONObject(response);
            JSONObject data_array = response_object.getJSONObject(data);
            JSONArray result_array = data_array.getJSONArray(result);
            location_objects = new RestaurantObject[result_array.length()];

            RestaurantMenuHolder menuHolder = RestaurantMenuHolder.getInstance();
            
            for(int i=0; i < result_array.length(); i++){

                JSONObject restaurant_details = result_array.getJSONObject(i);
                String restaurant_name = MenuUtilities.checkName(restaurant_details.getString(name));
                String location_name = restaurant_details.getString(location);
                JSONObject hour_details = restaurant_details.getJSONObject(hours);
                JSONArray result_array1 = hour_details.getJSONArray(result);
                details = new String[result_array1.length()];

                for(int j = 0; j < result_array1.length(); j++){
                    details[j] = result_array1.getString(j);
                }

                result_array = data_array.getJSONArray(result);             
                location_objects[i] = new RestaurantObject(i, restaurant_name, location_name, details);
                
                for (int j = 0; j < menuHolder.getCount(); j++) {
                    if (menuHolder.getRestaurantMenu().get(j).getRestaurant().equals(restaurant_name)) {
                        menuHolder.getRestaurantMenu().get(j).setLocation(location_name);
                    }
                }

            }

            init.initLocations(location_objects);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}