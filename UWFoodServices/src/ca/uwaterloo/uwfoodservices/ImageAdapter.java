package ca.uwaterloo.uwfoodservices;
import android.content.Context;
import android.graphics.Typeface;
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

	private Context context;;
	private Integer[] image_list = {R.drawable.bonappetit, R.drawable.browsers, R.drawable.brubakers, R.drawable.ceit, R.drawable.eye_opener,
			R.drawable.festivalfare, R.drawable.liquidassets, R.drawable.mls, R.drawable.mudies, R.drawable.pas, R.drawable.pastryplus,
			R.drawable.revelation, R.drawable.subway, R.drawable.tims, R.drawable.universityclub, R.drawable.williams_0};
	//Default Data. Will have to generate dynamically.
	private String[] restaurant_list = {"Bon Appetit", "Browsers Café", "Brubakers", "CEIT Café", "Eye-Opener", "Festival Fare",
			"Liquid Assets", "MLS Café", "Mudie's", "PAS", "Pastry Plus", "REVelation", "Subway", "Tim Hortons", "University Club",
			"Williams Café"};

	private String[] location_list = {"Davis Centre", "REV", "Davis Centre", "REV","Davis Centre", "REV","Davis Centre", "REV",
			"Davis Centre", "REV","Davis Centre", "REV","Davis Centre", "REV","Davis Centre", "REV"};
	
	private String[] sliding_list = {"Home", "Restaurant List", "Location & Hours", "About Us"};
	
	public ImageAdapter(Context context, int id){
		this.context = context;
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.id = id;
	}
	
	@Override
	public int getCount() {
		if(id==-1)
			return image_list.length;
		else
			return sliding_list.length;
	}

	@Override
	public Object getItem(int position) {
		if(id==-1)
			return restaurant_list[position];
		else
			return sliding_list[position];
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
			
			holder.restaraunt_name.setText(restaurant_list[position]);
			holder.location.setText(location_list[position]);
			holder.thumbnail.setImageResource(image_list[position]);
			
			return convertView;
		}
		else{
			if(convertView == null){
				convertView = inflater.inflate(R.layout.sliding_row, null);
			}
			
			TextView slidingText = (TextView) convertView.findViewById(R.id.text);
			slidingText.setTextSize(20);
			slidingText.setText(sliding_list[position]);
			
			return convertView;
			
		}
	}

}

