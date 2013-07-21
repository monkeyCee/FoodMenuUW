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
	private RestarauntLocationHolder rholder;
	private Context context;
	
	private String[] sliding_list = {"Home", "Restaurant List", "Location & Hours", "About Us"};
	Typeface tf;
	
	public ImageAdapter(Context context, int id){
		this.context = context;
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.id = id;
		tf = Typeface.createFromAsset(context.getAssets(),
	            "Roboto-Regular.ttf");
		rholder = RestarauntLocationHolder.getInstance(context);
	}
	
	@Override
	public int getCount() {
		if(id==-1){
			return rholder.getCount();
		}	
		else
			return sliding_list.length;
	}

	@Override
	public Object getItem(int position) {
		return rholder.objects[position].getRestaurant();
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
			
			holder.restaraunt_name.setText(rholder.objects[position].getRestaurant());
			holder.location.setText(rholder.objects[position].getLocation());
			holder.thumbnail.setImageResource(rholder.image_map.get(rholder.objects[position].getRestaurant()));
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

