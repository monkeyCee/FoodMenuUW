package ca.uwaterloo.uwfoodservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;

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
    private RestaurantLocationHolder holder;
    private RestaurantObject[] location_objects;
    private String[] details;

    public ParseLocationData(Context context){
        this.context = context;
    }

    public void Parse(JSONObject json){

        try {
            JSONObject response_object = json.getJSONObject(response);
            JSONObject data_array = response_object.getJSONObject(data);
            JSONArray result_array = data_array.getJSONArray(result);
            location_objects = new RestaurantObject[result_array.length()];

            for(int i=0; i < result_array.length(); i++){

                JSONObject restaurant_details = result_array.getJSONObject(i);
                String restaurant_name = MenuUtilities.checkName(restaurant_details.getString(name));
                Log.d(restaurant_name, "RESTUARANT NAME CHECK");
                String location_name = restaurant_details.getString(location);
                JSONObject hour_details = restaurant_details.getJSONObject(hours);
                JSONArray result_array1 = hour_details.getJSONArray(result);
                details = new String[result_array1.length()];

                for(int j = 0; j < result_array1.length(); j++){
                    details[j] = result_array1.getString(j);
                }

                result_array = data_array.getJSONArray(result);				
                location_objects[i] = new RestaurantObject(i, restaurant_name, location_name, details);

            }

            holder = RestaurantLocationHolder.getInstance(context, location_objects);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}