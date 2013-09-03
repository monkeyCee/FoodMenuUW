package ca.uwaterloo.uwfoodservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;

public class ParseWatcardVendorData {
    
    private String data = "data";
    private String result = "result";
    private String name = "Name";
    private String location = "Location";
    private String telephone = "Telephone";
    private String response = "response";
    private Context context;
    private WatcardVendorHolder holder;
    private WatcardVendorObject[] watcard_vendor_objects;

    public ParseWatcardVendorData(Context context){
        this.context = context;
    }

    public void Parse(JSONObject json){

        try {
            JSONObject response_object = json.getJSONObject(response);
            JSONObject data_array = response_object.getJSONObject(data);
            JSONArray result_array = data_array.getJSONArray(result);
            watcard_vendor_objects = new WatcardVendorObject[result_array.length()];
            
            for(int i=0; i < result_array.length(); i++){

                JSONObject vendor_details = result_array.getJSONObject(i);
                String vendor_name = MenuUtilities.checkName(vendor_details.getString(name));
                String location_name = vendor_details.getString(location);
                String telephone_number = vendor_details.getString(telephone);

                result_array = data_array.getJSONArray(result);             
                watcard_vendor_objects[i] = new WatcardVendorObject(vendor_name, location_name, telephone_number);

            }

            holder = WatcardVendorHolder.getInstance(watcard_vendor_objects);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
