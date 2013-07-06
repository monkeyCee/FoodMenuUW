package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private ListView listView;
	private Context context;
	private ActivityCommunicator activityCommunicator;
	
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
	     listView.setAdapter(new ImageAdapter(context, -1));
	   }
	 
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		  try {
		        view = inflater.inflate(
						R.layout.activity_restaurant_menu_list, container, false);
		        listView = (ListView) view.findViewById(R.id.list_restaurant);
		        
		        listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
						
//						activityCommunicator.passDataToActivity("Hi from Custom Fragment");
						getActivity().getActionBar().setSelectedNavigationItem(1);
						
					}
				});
		    } catch (InflateException e) {}
		 return view;
	}
}
