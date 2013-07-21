package ca.uwaterloo.uwfoodservices;

import android.content.Context;
import android.util.Log;

public class RestarauntLocationHolder{
	
	private static RestarauntLocationHolder mInstance = null;
	public RestaurantObject[] objects;
	
	public Integer[] image_list = {R.drawable.bonappetit, R.drawable.browsers, R.drawable.brubakers, R.drawable.ceit, R.drawable.eye_opener,
			R.drawable.festivalfare, R.drawable.liquidassets, R.drawable.mls, R.drawable.mudies, R.drawable.pas, R.drawable.pastryplus,
			R.drawable.revelation, R.drawable.subway, R.drawable.tims, R.drawable.universityclub, R.drawable.foodservices, R.drawable.williams_0, R.drawable.williams_0, R.drawable.williams_0};
	
	public String[] restaurant_list;

	public String[] location_list;
	
	public static RestarauntLocationHolder getInstance(Context context, RestaurantObject[] objects){
		if(mInstance == null){
			mInstance = new RestarauntLocationHolder(context.getApplicationContext(), objects);
		}
		return mInstance;
	}
	
	public static RestarauntLocationHolder getInstance(Context context){
		return mInstance;
	}
	
	private RestarauntLocationHolder(Context context, RestaurantObject[] objects){
		this.objects = objects;			
	}
	
	public int getCount(){
		return this.objects.length;
	}
	
}
