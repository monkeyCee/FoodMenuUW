package ca.uwaterloo.uwfoodservices;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter{
	
	private static LayoutInflater inflater = null;
	private ViewHolder holder;
	private int id;

	private Context context;
	
	private String[] sliding_list = {"Home", "Restaurant List", "Location & Hours", "About Us"};
	Typeface tf;
	
	RestaurantLocationHolder restaurantLocationHolder;
	
	public ImageAdapter(Context context, int id, RestaurantLocationHolder restaurantLocationHolder){
		this.context = context;
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.id = id;
		tf = Typeface.createFromAsset(context.getAssets(),
	            "Roboto-Regular.ttf");
		
		// Terrible code here
		this.restaurantLocationHolder = restaurantLocationHolder;
		while (restaurantLocationHolder.running) {
			this.restaurantLocationHolder = restaurantLocationHolder;
		}
	}
	
	@Override
	public int getCount() {
		if(id==-1) {
			Log.d(restaurantLocationHolder.restaurant_menu_list.get(0).size() + "", "restaurant menu list size");
			return restaurantLocationHolder.restaurant_menu_list.get(0).size();
			//return new RestarauntLocationHolder().image_list.length;
		}
		else {
			return sliding_list.length;
		}
	}

	@Override
	public Object getItem(int position) {
		if(id==-1){
			return restaurantLocationHolder.restaurant_menu_list.get(position).get(0);
			//return new RestarauntLocationHolder().restaurant_list[position];
		}
		else {
			return sliding_list[position];
		}
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(id == -1){
			if(convertView == null){
				convertView = inflater.inflate(R.layout.list_row, null);
				holder = new ViewHolder();
				holder.restaraunt_name = (TextView) convertView.findViewById(R.id.restaurant_name);
				holder.location = (TextView) convertView.findViewById(R.id.location);
				holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			//Log.d(restaurantLocationHolder.restaurant_menu_list.get(0).get(position) + "", "restaurant menu list get0");
			//Log.d(restaurantLocationHolder.restaurant_menu_list.get(1).get(position) + "", "restaurant menu list get1");
			//Log.d(restaurantLocationHolder.restaurant_menu_list.get(2).get(position) + "", "restaurant menu list get2");
			holder.restaraunt_name.setText(restaurantLocationHolder.restaurant_menu_list.get(0).get(position));
			holder.location.setText(restaurantLocationHolder.restaurant_menu_list.get(1).get(position));
			holder.thumbnail.setImageResource(Integer.parseInt(restaurantLocationHolder.restaurant_menu_list.get(2).get(position)));
			//holder.restaraunt_name.setText(new RestarauntLocationHolder().restaurant_list[position]);
			//holder.location.setText(new RestarauntLocationHolder().location_list[position]);
			//holder.thumbnail.setImageResource(new RestarauntLocationHolder().image_list[position]);
			holder.restaraunt_name.setTypeface(tf);
			holder.location.setTypeface(tf);
				
			return convertView;
		}
		else{
			if(convertView == null){
				convertView = inflater.inflate(R.layout.sliding_row, null);
			}
			
			TextView slidingText = (TextView) convertView.findViewById(R.id.text);
			slidingText.setTextSize(20);
			slidingText.setTypeface(tf);
			slidingText.setText(sliding_list[position]);
			return convertView;
			
		}
	}

}

