package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginWatcard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_watcard);

        TextView login = (TextView) findViewById(R.id.login);
        TextView pinReset = (TextView) findViewById(R.id.resetPin);
        TextView newStudents = (TextView) findViewById(R.id.newStudent);
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "Roboto-Light.ttf");
        login.setTypeface(tf);
        pinReset.setTypeface(tf);
        newStudents.setTypeface(tf);
        pinReset.setText(Html.fromHtml("Click <a href='https://account.watcard.uwaterloo.ca/pinreset.asp'>here</a> to reset your PIN"));
        pinReset.setMovementMethod(LinkMovementMethod.getInstance());
        newStudents.setText(Html.fromHtml("<a href='http://www.watcard.uwaterloo.ca/newstudents.html'>Information for new students</a>"));
        newStudents.setMovementMethod(LinkMovementMethod.getInstance());
        login.setText("WatCard Login");

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        Button go = (Button) findViewById(R.id.go);
        go.setTypeface(tf);

        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {      
                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    Intent intent = new Intent (LoginWatcard.this, DataWatcard.class);
                    intent.putExtra("Username", username.getText().toString());
                    intent.putExtra("Password", password.getText().toString());
                    startActivityForResult(intent, 1);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(LoginWatcard.this, BalanceWatcard.class);
                startActivity(intent);
            }
            else{
                int code = data.getIntExtra("Code", -2);
                if(code == 0){
                    Toast.makeText(getApplicationContext(), "Server Error.", Toast.LENGTH_SHORT).show();
                }
                else if(code == 1){
                    Toast.makeText(getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                }
                else if(code == -1){
                    Toast.makeText(getApplicationContext(), "No network or the network does not match your preference.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_watcard, menu);
        return true;
    }

}
