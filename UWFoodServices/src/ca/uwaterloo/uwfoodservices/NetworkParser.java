package ca.uwaterloo.uwfoodservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import android.util.Log;

public class NetworkParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    Document doc;

    public NetworkParser() {
    }

    public JSONObject getJSONFromUrl(String url) {
        try {

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

    public Document getHTML(String username, String password){

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        HttpEntity entity;
        HttpPost httpost = new HttpPost("https://account.watcard.uwaterloo.ca/watgopher661.asp");

        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("acnt_1", username));
        nvps.add(new BasicNameValuePair("acnt_2", password));
        nvps.add(new BasicNameValuePair("FINDATAREP", "ON"));
        nvps.add(new BasicNameValuePair("MESSAGEREP", "ON"));
        nvps.add(new BasicNameValuePair("STATUS", "STATUS"));
        nvps.add(new BasicNameValuePair("watgopher_title", "WatCard Account Status"));
        nvps.add(new BasicNameValuePair("watgopher_regex", "/<hr>([\\s\\S]*)<hr>/;"));
        nvps.add(new BasicNameValuePair("watgopher_style", "onecard_regular"));

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF_8"));
            response = httpclient.execute(httpost);
            entity = response.getEntity();
            System.out.println("Login form get: " + response.getStatusLine());
            InputStream is = entity.getContent();           
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            doc = Jsoup.parse(result);
            is.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpclient.getConnectionManager().shutdown();
        }

        return doc;
    }

}
