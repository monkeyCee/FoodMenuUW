package ca.uwaterloo.uwfoodservices;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_cache);
        
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "Roboto-Light.ttf");
        TextView text = (TextView) findViewById(R.id.errortext);
        text.setTypeface(tf);

        Button refresh = (Button) findViewById(R.id.ready);
        refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ErrorPage.this, SplashScreen.class);
                startActivity(intent);
            }
        });

        Button settings = (Button) findViewById(R.id.settingsPhone);
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);  
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.error_page, menu);
        return true;
    }

}
