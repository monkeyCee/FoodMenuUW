package ca.uwaterloo.uwfoodservices;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragmentTest extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferencestest);
    }
}