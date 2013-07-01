package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainScreen extends Activity {
	
	private int requestCode = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		ImageView image_menu = (ImageView) findViewById(R.id.menuImage);
		ImageView image_location = (ImageView) findViewById(R.id.locationImage);	
		
		final Intent intent_restaurant = new Intent(this, RestaurantMenuList.class);
		final Intent intent_location = new Intent(this, LocationHours.class);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "Roboto-Medium.ttf");
		
		TextView text_menu = (TextView) findViewById(R.id.menuText);
		TextView text_location = (TextView) findViewById(R.id.locationText);
		text_menu.setTypeface(tf);
		text_location.setTypeface(tf);
		
		image_menu.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivityForResult(intent_restaurant, requestCode);
			}
			
		});
		
		image_location.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivityForResult(intent_location, requestCode);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}

}
