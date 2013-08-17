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
    private RestaurantLocationHolder holderLoc;
    private WatcardVendorHolder holderVend;
    private CameraUpdate center;
    private CameraUpdate zoom;
    private String restaurant;
    private RadioButton showAll;
    private RadioButton clear;
    private String watcardVendor;
    private String filterType = "all";
    private int zoomValue = 13;

    public MyMapFragment(){
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        ((LocationHours)context).fragmentCommunicator = this;
        holderLoc = RestaurantLocationHolder.getInstance();
        holderVend = WatcardVendorHolder.getInstance();
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
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(zoomValue);
                        myMap.moveCamera(center);
                        myMap.animateCamera(zoom);
                        
                        if (filterType.equals("all"))
                        {
                            for(int i = 0; i < holderLoc.getCount(); i++){ 
                                String timings = "";
                                for(int j = 0; j < holderLoc.objects[i].getTimings().length; j++){
                                    timings += holderLoc.objects[i].getTimings()[j] + "\n";
                                }

                                restaurant = holderLoc.objects[i].getRestaurant();
                                Marker restaurant_location = myMap.addMarker(new MarkerOptions()
                                .position(new Coordinates().map.get(holderLoc.objects[i].getRestaurant() + " " + holderLoc.objects[i].getLocation()))
                                .title(restaurant)
                                .snippet(timings));         
                            }   
                            for(int i = 0; i < holderVend.getCount(); i++){ 
                                String telephoneNumber = holderVend.objects[i].getTelephone();

                                watcardVendor = holderVend.objects[i].getVendorName();
                                Marker restaurant_location = myMap.addMarker(new MarkerOptions()
                                .position(new Coordinates().map.get(holderVend.objects[i].getVendorName()))
                                .title(watcardVendor)
                                .snippet(holderVend.objects[i].getLocation() +"\n" + telephoneNumber));         
                            }   
                        }
                        else if (filterType.equals("location"))
                        {
                            for(int i = 0; i < holderLoc.getCount(); i++){ 
                                String timings = "";
                                for(int j = 0; j < holderLoc.objects[i].getTimings().length; j++){
                                    timings += holderLoc.objects[i].getTimings()[j] + "\n";
                                }

                                restaurant = holderLoc.objects[i].getRestaurant();
                                Marker restaurant_location = myMap.addMarker(new MarkerOptions()
                                .position(new Coordinates().map.get(holderLoc.objects[i].getRestaurant() + " " + holderLoc.objects[i].getLocation()))
                                .title(restaurant)
                                .snippet(timings));         
                            }   
                        }
                        else if (filterType.equals("watcardVendors"))
                        {
                            for(int i = 0; i < holderVend.getCount(); i++){ 
                                String telephoneNumber = holderVend.objects[i].getTelephone();

                                watcardVendor = holderVend.objects[i].getVendorName();
                                Marker restaurant_location = myMap.addMarker(new MarkerOptions()
                                .position(new Coordinates().map.get(holderVend.objects[i].getVendorName()))
                                .title(watcardVendor)
                                .snippet(holderVend.objects[i].getLocation() +"\n" + telephoneNumber));         
                            }   
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
    public void passDataToFragment(int position, String filterType) {
        this.filterType = filterType;
        showAll.setChecked(false);
        clear.setChecked(false); 
        
        if (filterType.equals("all"))
        {
            if (position>=0 && position < holderLoc.getCount())
                setupLocationMarker(position);
            else
                setupVendorMarker(position);
        }
        else if (filterType.equals("location"))
            setupLocationMarker(position);
        else if (filterType.equals("watcardVendors"))
            setupVendorMarker(position);        
    }
    public void setupLocationMarker(int position)
    {
        zoomValue = 14;
        LatLng coordinate = new Coordinates().map.get(holderLoc.objects[position].getRestaurant() + " " + holderLoc.objects[position].getLocation());
        myMap.clear();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(coordinate);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(17);
        myMap.moveCamera(center);
        myMap.animateCamera(zoom);

        String timings = "";
        for(int i = 0; i < holderLoc.objects[position].getTimings().length; i++){
            timings += holderLoc.objects[position].getTimings()[i] + "\n";
        }

        restaurant = holderLoc.objects[position].getRestaurant();
        Marker restaurant_location = myMap.addMarker(new MarkerOptions()
        .position(coordinate)
        .title(restaurant)
        .snippet(timings));

        myMap.setInfoWindowAdapter(new CustomInfoView(context));
        restaurant_location.showInfoWindow();
    }
    public void setupVendorMarker(int position)
    {
        zoomValue = 13;
        LatLng coordinate = new Coordinates().map.get(holderVend.objects[position].getVendorName());
        myMap.clear();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(coordinate);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(17);
        myMap.moveCamera(center);
        myMap.animateCamera(zoom);

        String telephone = "";
        telephone = holderVend.objects[position].getTelephone();

        watcardVendor = holderVend.objects[position].getVendorName();
        Marker vendor_location = myMap.addMarker(new MarkerOptions()
        .position(coordinate)
        .title(watcardVendor)
        .snippet(telephone + "\n" + holderVend.objects[position].getLocation()));

        myMap.setInfoWindowAdapter(new CustomInfoView(context));
        vendor_location.showInfoWindow();
    }

}
