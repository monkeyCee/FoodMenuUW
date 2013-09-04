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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;

public class ListViewFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listView;
    private Spinner spinner;
    private Context context;
    private ActivityCommunicator activityCommunicator;

    private String type;
    private int positionTracker;

    @Override
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
        listView.setAdapter(new ImageAdapter(context, type));
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                positionTracker = position;
                activityCommunicator.passDataToActivity(position, type);
                getActivity().getActionBar().setSelectedNavigationItem(1);
            }
        });
        spinner.post(new Runnable() {

            @Override
            public void run() {
                spinner.setOnItemSelectedListener(new OnItemSelectedListener()
                {           
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View view1, int pos, long id)
                    {
                        switch (pos)
                        {
                        case 0:   
                            type = "all";
                            listView.setAdapter(new ImageAdapter(context, "all"));
                            activityCommunicator.passDataToActivity(-1, "all");                                    
                            break;
                        case 1:
                            type = "location";
                            listView.setAdapter(new ImageAdapter(context, "location"));
                            activityCommunicator.passDataToActivity(-1, "location");
                            break;
                        case 2:
                            type = "watcardVendors";
                            listView.setAdapter(new ImageAdapter(context, "watcardVendors"));
                            activityCommunicator.passDataToActivity(-1, "watcardVendors");
                            break;
                        }                                                            
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub 
                    }    

                });

            }});

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        type = getArguments().getString("type");
        View view = null;
        try {
            view = inflater.inflate(
                    R.layout.locationlist, container, false);
            listView = (ListView) view.findViewById(R.id.list_restaurant);
            spinner = (Spinner) view.findViewById(R.id.spinner);

            if (type.equals("location")) {
                spinner.setSelection(1, false);
            } else if (type.equals("watcardVendors")) {
                spinner.setSelection(2, false);
            } else {
                spinner.setSelection(0, false);
            }

        } catch (InflateException e) {}
        return view;
    }
}