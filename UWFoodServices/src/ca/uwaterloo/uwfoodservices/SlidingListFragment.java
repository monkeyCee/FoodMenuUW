package ca.uwaterloo.uwfoodservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SlidingListFragment extends ListFragment {
	
	ListView slidingList;
	Intent intent;
	boolean main_sliding_menu = true;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sliding_list, null);
		slidingList = (ListView) view.findViewById(android.R.id.list);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ImageAdapter sampleAdapter = new ImageAdapter(getActivity(), "sliding");
		main_sliding_menu = true;
		slidingList.setAdapter(sampleAdapter);
	}	
	
	 @Override
	    public void onListItemClick(ListView l, View v, int position, long id) {
	       
			 switch(position){
			 
			 	case 0:
			 		intent = new Intent(getActivity(), MainScreen.class);
			 		startActivity(intent);
			 		break;
			 	
			 	case 1:
					intent = new Intent(getActivity(), RestaurantMenuList.class);
			 		startActivity(intent);
			 		break;
		
			 	case 2:
			 		intent = new Intent(getActivity(), LocationHours.class);
			 		startActivity(intent);
			 		break;
			 		
			 	default:
			 		Log.d("Clicked", Integer.toString(position+1));
			 		
			 }
		 }
		
}
