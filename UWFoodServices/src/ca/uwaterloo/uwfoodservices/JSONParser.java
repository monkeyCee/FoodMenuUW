package ca.uwaterloo.uwfoodservices;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONParser() {
    }

    public JSONObject getJSONFromUrl(String url) {
        try {

            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);

            // Setting up a default client to get the data
            DefaultHttpClient httpClient = new DefaultHttpClient();
            // HttpPost is a request to the web server
            HttpGet httpGet = new HttpGet(url);

            // Client executes the request
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // The 'response' from the server feeds back data stored in the httpEntity
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();			

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // Assigning that string to the JSON Object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON object which carries the JSON data
        return jObj;

    }
}
