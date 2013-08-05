package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;

import ca.uwaterloo.uwfoodservices.R;

public class RestaurantMenuHolder{

    private static RestaurantMenuHolder mInstance = null;
    public ArrayList<RestaurantMenuObject> restaurantMenu;

    public static RestaurantMenuHolder getInstance(ArrayList<RestaurantMenuObject> restaurantMenu){
        if(mInstance == null){
            mInstance = new RestaurantMenuHolder(restaurantMenu);
        }
        return mInstance;
    }

    public static RestaurantMenuHolder getInstance(){
        return mInstance;
    }

    private RestaurantMenuHolder(ArrayList<RestaurantMenuObject> restaurantMenu){
        this.restaurantMenu = restaurantMenu;
    }

    public int getCount(){
        return restaurantMenu.size();
    }

}
