package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;

import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuObject;
import android.app.Application;

public class InitialiseSingleton extends Application{

    public void initLocations(RestaurantObject objects[]){
        RestaurantLocationHolder.getInstance(objects);
    }

    public void initWatcardLocations(WatcardVendorObject[] objects){
        WatcardVendorHolder.getInstance(objects);
    }

    public void initMenus(ArrayList<RestaurantMenuObject> restaurantMenu){
        RestaurantMenuHolder.getInstance(restaurantMenu);
    }

}
