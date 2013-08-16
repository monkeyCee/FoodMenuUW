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

public class VendorMapFragment extends Fragment implements FragmentCommunicator{
    
    public static final String ARG_SECTION_NUMBER = "section_number";
    private GoogleMap myMap = null;
    static final LatLng UW = new LatLng(43.4722, -80.5472);
    private Context context;
    private WatcardVendorHolder holder;
    private CameraUpdate center;
    private CameraUpdate zoom;
    private String watcardVendor;
    private RadioButton showAll;
    private RadioButton clear;
    
    public VendorMapFragment(){
    }
    
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        ((LocationHours)context).fragmentCommunicator = this;
        holder = WatcardVendorHolder.getInstance();
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
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
                        myMap.moveCamera(center);
                        myMap.animateCamera(zoom);

                        for(int i = 0; i < holder.getCount(); i++){ 
                            String telephoneNumber = holder.objects[i].getTelephone();

                            watcardVendor = holder.objects[i].getVendorName();
                            Marker restaurant_location = myMap.addMarker(new MarkerOptions()
                            .position(new Coordinates().map.get(holder.objects[i].getVendorName()))
                            .title(watcardVendor)
                            .snippet(holder.objects[i].getLocation() +"\n" + telephoneNumber));         
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
        LatLng coordinate = new Coordinates().map.get(holder.objects[position].getVendorName());
        myMap.clear();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(coordinate);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(17);
        myMap.moveCamera(center);
        myMap.animateCamera(zoom);

        String telephone = "";
        telephone = holder.objects[position].getTelephone();

        watcardVendor = holder.objects[position].getVendorName();
        Marker vendor_location = myMap.addMarker(new MarkerOptions()
        .position(coordinate)
        .title(watcardVendor)
        .snippet(telephone + "\n" + holder.objects[position].getLocation()));

        myMap.setInfoWindowAdapter(new CustomInfoView(context));
        vendor_location.showInfoWindow();
        
    }
    
    

}
