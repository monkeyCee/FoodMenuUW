package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivityTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragmentTest()).commit();
    }

}