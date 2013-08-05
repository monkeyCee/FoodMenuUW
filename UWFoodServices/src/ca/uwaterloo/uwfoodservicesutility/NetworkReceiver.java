package ca.uwaterloo.uwfoodservicesutility;

import ca.uwaterloo.uwfoodservices.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {

    public final String WIFI = "Wi-Fi Only";
    public final String BOTH = "Both Wi-Fi and Data";
    public final String DATA = "Data Only";    
    
    Context context;
    
    public boolean wifiConnected = false;
    public boolean mobileConnected = false;
    public boolean dataConnected = false;
    
    public String networkPref = null;
    public boolean cachePref;
    public String refreshPref = null;
    SharedPreferences sharedPrefs;
    
    public NetworkReceiver(Context context) {
        this.context = context;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        networkPref = sharedPrefs.getString("connection_type_preference", "Both Wi-Fi and Data");
    }
    
    public String getNetworkPref() {
        return networkPref;
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {

    }
    
    public void updateConnectedFlags() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if ((activeInfo != null) && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }
    
    public boolean isNetwork(){
        
        updateConnectedFlags();
        networkPref = sharedPrefs.getString("connection_type_preference", "Both Wi-Fi and Data");
        if(((networkPref.equals(BOTH)) && (wifiConnected || mobileConnected))
                || ((networkPref.equals(WIFI)) && (wifiConnected)) || ((networkPref.equals(DATA)) && (mobileConnected))){
            return true;
        }
        else
            return false;
    }

}