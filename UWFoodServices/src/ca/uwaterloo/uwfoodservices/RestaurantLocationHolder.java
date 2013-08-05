package ca.uwaterloo.uwfoodservices;

import java.util.HashMap;

import android.content.Context;

public class RestaurantLocationHolder{

    private static RestaurantLocationHolder mInstance = null;
    public RestaurantObject[] objects;
    //public HashMap<String, Integer> image_map;

    public static RestaurantLocationHolder getInstance(Context context, RestaurantObject[] objects){
        if(mInstance == null){
            mInstance = new RestaurantLocationHolder(context.getApplicationContext(), objects);
        }
        return mInstance;
    }

    public static RestaurantLocationHolder getInstance(Context context){
        return mInstance;
    }

    private RestaurantLocationHolder(Context context, RestaurantObject[] objects){
        this.objects = objects;			
        /*
        image_map = new HashMap<String, Integer>();

        image_map.put("Bon Appetit", R.drawable.bonappetit);
        image_map.put("Browsers Cafe", R.drawable.browsers);
        image_map.put("Brubakers", R.drawable.brubakers);
        image_map.put("CEIT Cafe", R.drawable.ceit);
        image_map.put("Eye Opener Cafe", R.drawable.eye_opener);
        image_map.put("Festival Fare", R.drawable.festivalfare);
        image_map.put("LA Cafe", R.drawable.liquidassets);
        image_map.put("ML's Coffee Shop", R.drawable.mls);
        image_map.put("Mudie's", R.drawable.mudies);
        image_map.put("PAS Lounge", R.drawable.pas);
        image_map.put("Pastry Plus", R.drawable.pastryplus);
        image_map.put("REVelation", R.drawable.revelation);
        image_map.put("Subway", R.drawable.subway);
        image_map.put("Tim Hortons", R.drawable.tims);
        image_map.put("University Club", R.drawable.universityclub);
        image_map.put("UW Food Services Administrative Office", R.drawable.foodservices);
        image_map.put("Williams Fresh Cafe", R.drawable.williams_0);
    */
    }

    public int getCount(){
        return this.objects.length;
    }

}
