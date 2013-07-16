package ca.uwaterloo.uwfoodservices;

import android.content.Context;
import android.util.Log;

public class RestarauntLocationHolder{
	
	private DatabaseHandler db;
	private static RestarauntLocationHolder mInstance = null;
	
	public Integer[] image_list = {R.drawable.bonappetit, R.drawable.browsers, R.drawable.brubakers, R.drawable.ceit, R.drawable.eye_opener,
			R.drawable.festivalfare, R.drawable.liquidassets, R.drawable.mls, R.drawable.mudies, R.drawable.pas, R.drawable.pastryplus,
			R.drawable.revelation, R.drawable.subway, R.drawable.tims, R.drawable.universityclub, R.drawable.foodservices, R.drawable.williams_0, R.drawable.williams_0, R.drawable.williams_0};
	
	public String[] restaurant_list;

	public String[] location_list;
	
	public static RestarauntLocationHolder getInstance(Context context){
		if(mInstance == null){
			mInstance = new RestarauntLocationHolder(context.getApplicationContext());
		}
		return mInstance;
	}
	
	private RestarauntLocationHolder(Context context){
		RestaurantObject object;
		db = DatabaseHandler.getInstance(context);
		int length = db.getCount();
		restaurant_list = new String[length];
		location_list = new String[length];
		for(int i = 0; i < length; i++){
			Log.d("Initialising lists", "Inside RLH");
			object = db.getRestaurant(i+1);
			restaurant_list[i] = object.getRestaurant();
			location_list[i] = object.getLocation();
		}
		
	}
	
	public int getCount(){
		return db.getCount();
	}
	
}
