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
import android.widget.ListView;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FilterFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private Context context;
    private ActivityCommunicator activityCommunicator;
    private RadioGroup radioGroup;
    private RadioButton button1, button2;

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
        radioGroup.setOnCheckedChangeListener (new OnCheckedChangeListener(){
        public void onCheckedChanged(RadioGroup group, int checkedId) 
        {
          if (checkedId == R.id.radioButton1)
          {
              Intent intent=new Intent();
              intent.putExtra("locations", "loc");
              intent.setClass(FilterFragment.this.getActivity().getApplicationContext(), LocationHours.class); 
              startActivity(intent);
          }
          else
          {              
              Intent intent=new Intent();
              intent.putExtra("vendors", "vend");
              intent.setClass(FilterFragment.this.getActivity().getApplicationContext(), LocationHours.class); 
              startActivity(intent);
          }
        }
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(
                    R.layout.filter_view, container, false);
            radioGroup = (RadioGroup) view.findViewById(R.id.radio_group); 
            button1 = (RadioButton)view.findViewById(R.id.radioButton1);
            button2 = (RadioButton)view.findViewById(R.id.radioButton2);

        } catch (InflateException e) {}
        return view;
    }
}
