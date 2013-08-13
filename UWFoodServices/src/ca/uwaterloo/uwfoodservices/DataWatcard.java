package ca.uwaterloo.uwfoodservices;

import org.jsoup.nodes.Document;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import ca.uwaterloo.uwfoodservicesutility.NetworkReceiver;

public class DataWatcard extends Activity {
    
    private static ProgressDialog progressDialog;
    private NetworkReceiver receiver;
    private static int code = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_watcard);
          
        progressDialog = new ProgressDialog(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver(this);
        this.registerReceiver(receiver, filter);
        retrieveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_watcard, menu);
        return true;
    }
    
    private void retrieveData(){
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        String password = intent.getStringExtra("Password");
        final AsyncDataFetcher dataFetcher = new AsyncDataFetcher(this);

        if(receiver.isNetwork()){
            dataFetcher.execute(username, password);
            progressDialog.setCancelable(true);
            progressDialog.setOnDismissListener(new OnDismissListener(){

                @Override
                public void onDismiss(DialogInterface dialog) {   
                    if(!dataFetcher.isCancelled()){
                        noError();
                    }
                    Log.d("Dismissed", "Not Cancelled");
                }
                
            });
            
            progressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) { 
                    dataFetcher.cancel(true);
                    progressDialog.dismiss();
                    errorAndCancel();
                }
            });  
            
        }
        else{
            errorAndCancel();
        }
       
    }
    
    private static class AsyncDataFetcher extends AsyncTask<String, Integer, Integer> {
        
        private Context context;
        
        public AsyncDataFetcher(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute()
        {             
            progressDialog.show();
        }; 
        
        @Override
        protected Integer doInBackground(String... details) {
            Document doc = new NetworkParser().getHTML(details[0], details[1]);
            if(doc == null){
                code = 0;
                progressDialog.cancel();
            }
            else{
                boolean result = new ParseWatcardData(doc, context).parse();
                if(!result){
                    code = 1;
                    progressDialog.cancel();
                }
            }
            
            return null;
        }
        
        @Override
        protected void onPostExecute(Integer position) {
            progressDialog.dismiss();
        }
               
    }
    
    private void errorAndCancel(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Code", code);
        setResult(RESULT_CANCELED, returnIntent);        
        finish();
    }
    
    private void noError(){
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);     
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }

    }
}
