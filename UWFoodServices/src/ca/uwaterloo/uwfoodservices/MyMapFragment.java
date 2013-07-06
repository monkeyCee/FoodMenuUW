package ca.uwaterloo.uwfoodservices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MyMapFragment extends Fragment implements FragmentCommunicator{
	
	public static final String ARG_SECTION_NUMBER = "section_number";
	GoogleMap myMap = null;
	GoogleMapOptions options = new GoogleMapOptions();
	static final LatLng UW = new LatLng(43.4722, -80.5472);
	CameraPosition camera = new CameraPosition(UW, 14, 0, 0);
	int restaurant;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		  try {
		        view = inflater.inflate(
						R.layout.fragment_map, container, false);
		        		        
		          SupportMapFragment mySupportMapFragment 
		           = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
		          myMap = mySupportMapFragment.getMap();   
		             
		             if(myMap != null){
		             }
		             
		             
		    } catch (InflateException e) {}
		 return view;
	}

	@Override
	public void passDataToFragment(String someValue) {
		
		// Set all Markers here
		
		
		
	}
}
