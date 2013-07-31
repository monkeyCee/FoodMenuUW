package ca.uwaterloo.uwfoodservicesutility;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import ca.uwaterloo.uwfoodservices.R;

public class MenuUtilities {
	
	public MenuUtilities() {
		
	}
	
	public static Map<String, Integer> imageHash = new HashMap<String, Integer>();
	
	public static Map<String, Integer> getImageHash() {
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
		imageHash.put("Williams Fresh Café", R.drawable.williams_0);
		return imageHash;
	}
	
	public static String checkName(String name) {
		if (name.equals("Browsers Cafe")) { return "Browsers Café"; }
		else if (name.equals("CEIT Cafe")) { return "CEIT Café"; }
		else if (name.equals("Eye Opener Cafe")) { return "Eye-Opener Café"; }
		else if (name.equals("LA Cafe")) { return "Liquid Assets Café"; }
		else if (name.equals("UW Food Services Administrative Office")) { return "UW Food Admin Office"; }
		else if (name.equals("Williams Fresh Cafe")) { return "Williams Fresh Café"; }
		return name;
	}
	
	public static int menuItemTextWidth = 550; // 579? Number that works reasonably well.
	
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
	
	
}
