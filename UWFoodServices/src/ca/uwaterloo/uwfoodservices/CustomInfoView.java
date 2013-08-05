package ca.uwaterloo.uwfoodservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.uwaterloo.uwfoodservicesutility.MenuUtilities;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoView implements GoogleMap.InfoWindowAdapter {

    private static LayoutInflater inflater = null;
    private RestaurantLocationHolder holder;
    private Context context;

    public CustomInfoView(Context context){
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        holder = RestaurantLocationHolder.getInstance(context);
    }

    @Override
    public View getInfoContents(Marker marker) {
        View popup= inflater.inflate(R.layout.infopopover, null);

        TextView tv=(TextView)popup.findViewById(R.id.title);
        tv.setText(marker.getTitle());
        tv=(TextView)popup.findViewById(R.id.snippet);
        tv.setText(marker.getSnippet());
        ImageView icon = (ImageView) popup.findViewById(R.id.icon);
        icon.setImageResource(MenuUtilities.getImageHash().get(marker.getTitle()));
        return popup;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }



}
