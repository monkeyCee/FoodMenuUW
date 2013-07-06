package ca.uwaterloo.uwfoodservices;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RestaurantMenuList extends Activity {
	
	private int requestCode = -1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_menu_list);
		
		new RestarauntLocationHolder().execute();
		/*
		Thread trd = new Thread(new Runnable(){
			  @Override
			  public void run(){
				  try {
					Log.d(DataFetcher.FetchRestaurants(27, "98bbbd30b3e4f621d9cb544a790086d6") + "", "restaurants");
					Log.d(DataFetcher.FetchMealTimes("98bbbd30b3e4f621d9cb544a790086d6") + "", "mealTimes");
					Log.d(DataFetcher.FetchHours("98bbbd30b3e4f621d9cb544a790086d6") + "", "hours");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		});
		trd.start();
		*/
		
		final Intent intent_menu = new Intent(this, MenuLists.class);
		final ImageAdapter imageAdapter = new ImageAdapter(this, -1);
		
		ListView listView = (ListView) findViewById(R.id.list_restaurant);
		listView.setAdapter(imageAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				intent_menu.putExtra("Restaurant Name", (String) imageAdapter.getItem(position));
				startActivityForResult(intent_menu, requestCode);
				
			}
		});

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_restaurant_menu_list, menu);
		return true;
	}
	
}
