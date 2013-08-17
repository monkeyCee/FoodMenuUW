package ca.uwaterloo.uwfoodservicesutility;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.Display;
import ca.uwaterloo.uwfoodservices.R;

public class MenuUtilities {

    public MenuUtilities() {

    }

    public static Map<String, Integer> imageHash;

    public static Map<String, Integer> setImageHash() {
        imageHash = new HashMap<String, Integer>();
        imageHash.put("Bon Appetit", R.drawable.bonappetit);
        imageHash.put("Browsers Café", R.drawable.browsers);
        imageHash.put("Brubakers", R.drawable.brubakers);
        imageHash.put("CEIT Café", R.drawable.ceit);
        imageHash.put("Eye-Opener Café", R.drawable.eye_opener);
        imageHash.put("Festival Fare", R.drawable.festivalfare);
        imageHash.put("Liquid Assets Café", R.drawable.liquidassets);
        imageHash.put("ML's Coffee Shop", R.drawable.mls);
        imageHash.put("Mudie's", R.drawable.mudies);
        imageHash.put("PAS Lounge", R.drawable.pas);
        imageHash.put("Pastry Plus", R.drawable.pastryplus);
        imageHash.put("REVelation", R.drawable.revelation);
        imageHash.put("Subway", R.drawable.subway);
        imageHash.put("Tim Hortons", R.drawable.tims);
        imageHash.put("University Club", R.drawable.universityclub);
        imageHash.put("UW Food Services Admin Office", R.drawable.foodservices);
        imageHash.put("Williams Fresh Café", R.drawable.williams_0);
        
        imageHash.put("Apple Two Hairstylists", R.drawable.appletwo);
        imageHash.put("Campus Pizza", R.drawable.campuspizza);
        imageHash.put("Curry in a Hurry", R.drawable.curryinahurry);
        imageHash.put("Domino's Pizza", R.drawable.dominospizza);
        imageHash.put("East Side Mario's", R.drawable.eastsidemarios);
        imageHash.put("Farah Foods", R.drawable.farahfoods);
        imageHash.put("Grab a Greek", R.drawable.grabagreek);
        imageHash.put("McGinnis Front Row", R.drawable.frontrow);
        imageHash.put("Meet Point", R.drawable.meetpoint);
        imageHash.put("Pita Pit", R.drawable.pitapit);
        imageHash.put("Pizza Pizza", R.drawable.pizzapizza);
        imageHash.put("Sobeys", R.drawable.sobeys);
        imageHash.put("Student Health Pharmacy", R.drawable.studenthealthpharmacy);
        imageHash.put("Swiss Chalet", R.drawable.swisschalet);
        imageHash.put("The Grill", R.drawable.thegrill);
        imageHash.put("Waterloo Taxi", R.drawable.waterlootaxi);
        imageHash.put("Williams Coffee Pub", R.drawable.williams_1);
        
        return imageHash;
    }
    
    public static Map<String, Integer> getImageHash() {
        return imageHash;
    }

    public static String checkName(String name) {
        if (name.equals("Browsers Cafe")) { return "Browsers Café"; }
        else if (name.equals("CEIT Cafe")) { return "CEIT Café"; }
        else if (name.equals("Eye Opener Cafe")) { return "Eye-Opener Café"; }
        else if (name.equals("LA Cafe")) { return "Liquid Assets Café"; }
        else if (name.equals("UW Food Services Administrative Office")) { return "UW Food Services Admin Office"; }
        else if (name.equals("Williams Fresh Cafe")) { return "Williams Fresh Café"; }
        else if (name.equals("East Side Mario&apos;s")){return "East Side Mario's";}
        else if (name.equals("Domino&apos;s Pizza")){return "Domino's Pizza";}
        return name;
    }

    public static int menuItemTextWidth = 550; // 579? Number that works reasonably well.

    public static void setMenuItemTextWidth(int width) {
        menuItemTextWidth = width;
    }
    
    public static int getMenuItemTextWidth() {
        return menuItemTextWidth;
    }
    
    public static int getTextWidth(String text, TextPaint textpaint) {
        Rect bounds = new Rect();
        textpaint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }

    public static int getTextHeight(String text, TextPaint textpaint) {
        Rect bounds = new Rect();
        textpaint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.bottom + bounds.height();
        return height;
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    
    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
}
