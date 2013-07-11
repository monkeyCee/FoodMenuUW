package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListViewFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private ListView listView;
	private Context context;
	private ActivityCommunicator activityCommunicator;
	private Button button_all;
	private Button button_clear;
	
	public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        activityCommunicator = (ActivityCommunicator) context;
      }

	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	     super.onActivityCreated(savedInstanceState);
	     init();
	    }
	 
	 public void init() {
		 RestaurantLocationHolder restaurantLocationHolder = new RestaurantLocationHolder();
		 restaurantLocationHolder.execute();
	     listView.setAdapter(new ImageAdapter(context, -1, restaurantLocationHolder));
	     
	     listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					activityCommunicator.passDataToActivity(position);
					getActivity().getActionBar().setSelectedNavigationItem(1);
				}
			});
	        
	     button_all.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					activityCommunicator.passDataToActivity(-1);
					getActivity().getActionBar().setSelectedNavigationItem(1);
				}
			});
	        
	     button_clear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				activityCommunicator.passDataToActivity(-2);
				getActivity().getActionBar().setSelectedNavigationItem(1);
			}
	    	 
	     });
	   }
	 
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		  try {
		        view = inflater.inflate(
						R.layout.locationlist, container, false);
		        listView = (ListView) view.findViewById(R.id.list_restaurant);
		        button_all = (Button) view.findViewById(R.id.button_all);
		        button_clear = (Button) view.findViewById(R.id.button_clear);
		                
		    } catch (InflateException e) {}
		 return view;
	}
}
