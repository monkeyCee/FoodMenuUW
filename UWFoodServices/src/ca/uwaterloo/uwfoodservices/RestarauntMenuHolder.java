package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;

import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuObject;

public class RestarauntMenuHolder{
	
	private static RestarauntMenuHolder mInstance = null;
	ArrayList<RestaurantMenuObject> menuObject;
	
	public Integer[] image_list = {R.drawable.bonappetit, R.drawable.browsers, R.drawable.brubakers, R.drawable.ceit, R.drawable.eye_opener,
			R.drawable.festivalfare, R.drawable.liquidassets, R.drawable.mls, R.drawable.mudies, R.drawable.pas, R.drawable.pastryplus,
			R.drawable.revelation, R.drawable.subway, R.drawable.tims, R.drawable.universityclub, R.drawable.foodservices, R.drawable.williams_0, R.drawable.williams_0, R.drawable.williams_0};
	
	public ArrayList<RestaurantMenuObject> restaurantMenu;
	
	public static RestarauntMenuHolder getInstance(ArrayList<RestaurantMenuObject> menuObject){
		if(mInstance == null){
			mInstance = new RestarauntMenuHolder(menuObject);
		}
		return mInstance;
	}
	
	private RestarauntMenuHolder(ArrayList<RestaurantMenuObject> menuObject){
		this.menuObject = menuObject;
	}
	
	public int getCount(){
		return menuObject.size();
	}
	
}
