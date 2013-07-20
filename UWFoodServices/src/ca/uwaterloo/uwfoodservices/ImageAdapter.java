package ca.uwaterloo.uwfoodservices;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter{
	
	private static LayoutInflater inflater = null;
	private ViewHolder holder;
	private int id;
	private RestarauntMenuHolder menuHolder;
	private Context context;
	
	private String[] sliding_list = {"Home", "Restaurant List", "Location & Hours", "About Us"};
	Typeface tf;
	
	public ImageAdapter(Context context, int id){
		this.context = context;
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.id = id;
		tf = Typeface.createFromAsset(context.getAssets(),
	            "Roboto-Regular.ttf");
		Log.d("Created RLH Object", "Inside IA");
		menuHolder = RestarauntMenuHolder.getInstance(null);
	}
	
	@Override
	public int getCount() {
		if(id==-1){
			return menuHolder.getCount();
		}	
		else
			return sliding_list.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
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
//				holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			
			holder.restaraunt_name.setText(menuHolder.menuObject.get(position).getRestaurant());
			holder.location.setText(menuHolder.menuObject.get(position).getLocation());
//			holder.thumbnail.setImageResource(new RestarauntLocationHolder().image_list[position]);
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

