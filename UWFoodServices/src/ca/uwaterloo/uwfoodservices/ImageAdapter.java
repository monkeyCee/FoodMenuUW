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
import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;
import ca.uwaterloo.uwfoodservicesutility.RestaurantMenuHolder;


public class ImageAdapter extends BaseAdapter{

    private static LayoutInflater inflater = null;
    private ViewHolder holder;
    private String id;
    private RestaurantMenuHolder menuHolder;
    private RestaurantLocationHolder locationHolder;
    private WatcardVendorHolder  watcardVendorHolder;
    private Context context;
    private int counter = 0;

    private String[] sliding_list = {"Home", "Skip to Menu", "Location & Hours", "WatCard Balance", "About Us"};
    Typeface tf;

    public ImageAdapter(Context context, String id){
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.id = id;
        tf = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Light.ttf");
        menuHolder = RestaurantMenuHolder.getInstance();
        locationHolder = RestaurantLocationHolder.getInstance();
        watcardVendorHolder = WatcardVendorHolder.getInstance();
        MenuUtilities.setImageHash();
    }

    @Override
    public int getCount() {
        if(id.equals("menu")){
            return menuHolder.getCount();
        } else if (id.equals("location")) {
            return locationHolder.getCount();
        } else if (id.equals("watcardVendors")){
            return watcardVendorHolder.getCount();
        } else if (id.equals("all")){
            return watcardVendorHolder.getCount()+locationHolder.getCount();
        }
        else {
            return sliding_list.length;
        }
    }

    @Override
    public Object getItem(int position) {
        return menuHolder.getRestaurantMenu().get(position).getRestaurant();
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(id == "menu"){
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row, null);
                holder = new ViewHolder();
                holder.restaraunt_name = (TextView) convertView.findViewById(R.id.restaurant_name);
                holder.location = (TextView) convertView.findViewById(R.id.location);
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.restaraunt_name.setText(menuHolder.getRestaurantMenu().get(position).getRestaurant());
            holder.location.setText(menuHolder.getRestaurantMenu().get(position).getLocation());
            holder.thumbnail.setImageResource(menuHolder.getRestaurantMenu().get(position).getImage());
            holder.restaraunt_name.setTypeface(tf);
            holder.location.setTypeface(tf);

            return convertView;

        } 
        else if (id == "location") {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row, null);
                holder = new ViewHolder();
                holder.restaraunt_name = (TextView) convertView.findViewById(R.id.restaurant_name);
                holder.location = (TextView) convertView.findViewById(R.id.location);
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.restaraunt_name.setText(locationHolder.objects[position].getRestaurant());
            holder.location.setText(locationHolder.objects[position].getLocation());
            holder.thumbnail.setImageResource(MenuUtilities.getImageHash().get(locationHolder.objects[position].getRestaurant()));
            holder.restaraunt_name.setTypeface(tf);
            holder.location.setTypeface(tf);

            return convertView;
        } 
        else if (id == "watcardVendors"){
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row, null);
                holder = new ViewHolder();
                holder.restaraunt_name = (TextView) convertView.findViewById(R.id.restaurant_name);
                holder.location = (TextView) convertView.findViewById(R.id.location);
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.restaraunt_name.setText(watcardVendorHolder.objects[position].getVendorName());
            holder.location.setText(watcardVendorHolder.objects[position].getTelephone());
            holder.thumbnail.setImageResource(MenuUtilities.getImageHash().get(watcardVendorHolder.objects[position].getVendorName()));
            holder.restaraunt_name.setTypeface(tf);
            holder.location.setTypeface(tf);

            return convertView;
        }
        else if (id == "all"){
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row, null);
                holder = new ViewHolder();
                holder.restaraunt_name = (TextView) convertView.findViewById(R.id.restaurant_name);
                holder.location = (TextView) convertView.findViewById(R.id.location);
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position < locationHolder.getCount() && position >=0)
            {
                holder.restaraunt_name.setText(locationHolder.objects[position].getRestaurant());
                holder.location.setText(locationHolder.objects[position].getLocation());
                holder.thumbnail.setImageResource(MenuUtilities.getImageHash().get(locationHolder.objects[position].getRestaurant()));
                holder.restaraunt_name.setTypeface(tf);
                holder.location.setTypeface(tf);
            }
            else
            {
                position -= locationHolder.getCount();
                holder.restaraunt_name.setText(watcardVendorHolder.objects[position].getVendorName());
                holder.location.setText(watcardVendorHolder.objects[position].getTelephone());
                holder.thumbnail.setImageResource(MenuUtilities.getImageHash().get(watcardVendorHolder.objects[position].getVendorName()));
                holder.restaraunt_name.setTypeface(tf);
                holder.location.setTypeface(tf);
            }
            
            

            return convertView;
        }
        else {
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

