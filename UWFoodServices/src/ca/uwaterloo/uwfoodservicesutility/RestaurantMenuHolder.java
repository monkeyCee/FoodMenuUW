package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;

import ca.uwaterloo.uwfoodservices.R;

public class RestaurantMenuHolder{
	
	private static RestaurantMenuHolder mInstance = null;
	public ArrayList<RestaurantMenuObject> restaurantMenu;
	
	public Integer[] image_list = {R.drawable.bonappetit, R.drawable.browsers, R.drawable.brubakers, R.drawable.ceit, R.drawable.eye_opener,
			R.drawable.festivalfare, R.drawable.liquidassets, R.drawable.mls, R.drawable.mudies, R.drawable.pas, R.drawable.pastryplus,
			R.drawable.revelation, R.drawable.subway, R.drawable.tims, R.drawable.universityclub, R.drawable.foodservices, R.drawable.williams_0, R.drawable.williams_0, R.drawable.williams_0};
	
	
	
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
