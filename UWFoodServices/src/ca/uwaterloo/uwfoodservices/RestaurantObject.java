package ca.uwaterloo.uwfoodservices;

import java.io.Serializable;

public class RestaurantObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -272946356689909410L;
	int id;
	String restaurant_name;
	String location;
	String[] timings;
	
	public RestaurantObject(int id, String restaurant_name, String location, String[] timings){
		this.id = id;
		this.restaurant_name = restaurant_name;
		this.location = location;
		this.timings = timings;
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getRestaurant(){
		return this.restaurant_name;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String[] getTimings(){
		return this.timings;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setRestaurant(String restaurant_name){
		this.restaurant_name = restaurant_name;
	}

	public void setLocation(String location){
		this.location = location;
	}
	
	public void setTimings(String[] timings){
		this.timings = timings;
	}

}
