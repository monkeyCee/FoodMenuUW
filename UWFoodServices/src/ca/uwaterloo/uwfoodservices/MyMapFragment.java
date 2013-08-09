package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends Fragment implements FragmentCommunicator{

    public static final String ARG_SECTION_NUMBER = "section_number";
    private GoogleMap myMap = null;
    static final LatLng UW = new LatLng(43.4722, -80.5472);
    private Context context;
    private RestaurantLocationHolder holder;
    private CameraUpdate center;
    private CameraUpdate zoom;
    private String restaurant;
    private RadioButton showAll;
    private RadioButton clear;

    public MyMapFragment(){
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        ((LocationHours)context).fragmentCommunicator = this;
        holder = RestaurantLocationHolder.getInstance(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(
                    R.layout.fragment_map, container, false);


            RadioGroup options_map = (RadioGroup) view.findViewById(R.id.radioGroup);
            showAll = (RadioButton) view.findViewById(R.id.ShowAll);
            clear = (RadioButton) view.findViewById(R.id.Clear);

            SupportMapFragment mySupportMapFragment 
            = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
            myMap = mySupportMapFragment.getMap();   

            if(myMap != null){
                myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                LatLng coordinate = new LatLng(43.469828,-80.546415);
                center=CameraUpdateFactory.newLatLng(coordinate);
                zoom=CameraUpdateFactory.zoomTo(14);
                myMap.moveCamera(center);
                myMap.animateCamera(zoom);
                myMap.setMyLocationEnabled(true);
            }

            options_map.setOnCheckedChangeListener(new OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int position) {

                    if(position == R.id.ShowAll){
                        myMap.clear();
                        LatLng coordinate = new LatLng(43.469828,-80.546415);
                        CameraUpdate center=
                                CameraUpdateFactory.newLatLng(coordinate);
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);
                        myMap.moveCamera(center);
                        myMap.animateCamera(zoom);

                        for(int i = 0; i < holder.getCount(); i++){	
                            String timings = "";
                            for(int j = 0; j < holder.objects[i].getTimings().length; j++){
                                timings += holder.objects[i].getTimings()[j] + "\n";
                            }

                            restaurant = holder.objects[i].getRestaurant();
                            Marker restaurant_location = myMap.addMarker(new MarkerOptions()
                            .position(new Coordinates().map.get(holder.objects[i].getRestaurant() + " " + holder.objects[i].getLocation()))
                            .title(restaurant)
                            .snippet(timings));			
                        }	
                        myMap.setInfoWindowAdapter(new CustomInfoView(context));

                    }
                    else{
                        myMap.clear();
                        LatLng coordinate = new LatLng(43.469828,-80.546415);
                        center=CameraUpdateFactory.newLatLng(coordinate);
                        zoom=CameraUpdateFactory.zoomTo(15);
                        myMap.moveCamera(center);
                        myMap.animateCamera(zoom);
                    }

                }

            });


        } catch (InflateException e) {}
        return view;
    }

    @Override
    public void passDataToFragment(int position) {

        showAll.setChecked(false);
        clear.setChecked(false);
        LatLng coordinate = new Coordinates().map.get(holder.objects[position].getRestaurant() + " " + holder.objects[position].getLocation());
        myMap.clear();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(coordinate);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(17);
        myMap.moveCamera(center);
        myMap.animateCamera(zoom);

        String timings = "";
        for(int i = 0; i < holder.objects[position].getTimings().length; i++){
            timings += holder.objects[position].getTimings()[i] + "\n";
        }

        restaurant = holder.objects[position].getRestaurant();
        Marker restaurant_location = myMap.addMarker(new MarkerOptions()
        .position(coordinate)
        .title(restaurant)
        .snippet(timings));

        myMap.setInfoWindowAdapter(new CustomInfoView(context));
        restaurant_location.showInfoWindow();

    }

}
