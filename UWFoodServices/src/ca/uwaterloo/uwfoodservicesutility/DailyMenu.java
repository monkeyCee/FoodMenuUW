package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;

public class DailyMenu {
	
	ArrayList<MenuItem> lunch;
	ArrayList<MenuItem> dinner;
	
	public DailyMenu(ArrayList<MenuItem> lunch, ArrayList<MenuItem> dinner) {
		this.lunch = lunch;
		this.dinner = dinner;
	}
	
	public ArrayList<MenuItem> getLunch() {
		return lunch;
	}
	
	public ArrayList<MenuItem> getDinner() {
		return dinner;
	}
}