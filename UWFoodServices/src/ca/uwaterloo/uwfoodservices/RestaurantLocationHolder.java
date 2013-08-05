package ca.uwaterloo.uwfoodservices;

import android.content.Context;

public class RestaurantLocationHolder{

    private static RestaurantLocationHolder mInstance = null;
    public RestaurantObject[] objects;

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
    }

    public int getCount(){
        return this.objects.length;
    }

}
