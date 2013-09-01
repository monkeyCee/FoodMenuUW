package ca.uwaterloo.uwfoodservicesutility;

import java.io.Serializable;
import java.util.ArrayList;

public class DailyMenu implements Serializable {

    private static final long serialVersionUID = -5844629072176676304L;
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