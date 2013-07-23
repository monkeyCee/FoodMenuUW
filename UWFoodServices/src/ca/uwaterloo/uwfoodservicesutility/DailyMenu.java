package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;

public class DailyMenu {
	
	ArrayList<RestaurantMenuItem> lunch;
	ArrayList<RestaurantMenuItem> dinner;
	
	public DailyMenu(ArrayList<RestaurantMenuItem> lunch, ArrayList<RestaurantMenuItem> dinner) {
		this.lunch = lunch;
		this.dinner = dinner;
	}
	
	public ArrayList<RestaurantMenuItem> getLunch() {
		return lunch;
	}
	
	public ArrayList<RestaurantMenuItem> getDinner() {
		return dinner;
	}
}