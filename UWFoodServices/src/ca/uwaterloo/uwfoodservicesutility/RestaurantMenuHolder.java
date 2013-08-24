package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;

import android.app.Application;
import android.util.Log;

public class RestaurantMenuHolder extends Application{

    private static RestaurantMenuHolder mInstance = null;
    private static ArrayList<RestaurantMenuObject> restaurantMenu;

    public synchronized static RestaurantMenuHolder getInstance(ArrayList<RestaurantMenuObject> restaurantMenu){
        if(mInstance == null){
            mInstance = new RestaurantMenuHolder(restaurantMenu);
        }
        Log.d(restaurantMenu.size() +"", "mInstance - size getInstance 1 ");
        return mInstance;
    }

    public static RestaurantMenuHolder getInstance(){
        if(mInstance == null){
            mInstance = new RestaurantMenuHolder(restaurantMenu);
        }
        Log.d(restaurantMenu.size() +"", "mInstance - size getInstance 2");
        return mInstance;
    }

    private RestaurantMenuHolder(ArrayList<RestaurantMenuObject> restaurantMenu){
        Log.d(restaurantMenu.size() +"", "mInstance - size constructor");
        RestaurantMenuHolder.restaurantMenu = restaurantMenu;
    }

    public int getCount(){
        Log.d((mInstance == null) +"", "mInstance");
        Log.d(restaurantMenu.size() +"", "mInstance - size");
        return restaurantMenu.size();
    }
    
    public ArrayList<RestaurantMenuObject> getRestaurantMenu() {
        return restaurantMenu;
    }

}
