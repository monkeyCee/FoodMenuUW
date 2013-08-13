package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class BalanceWatcard extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_watcard);
        
        WatcardHolder holder = WatcardHolder.getInstance();
        
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "Roboto-Light.ttf");
        
        TextView mealplan_id = (TextView) findViewById(R.id.meal_text);
        TextView mealplan_value = (TextView) findViewById(R.id.meal_value);
        TextView flex1_id = (TextView) findViewById(R.id.flex1);
        TextView flex1_value = (TextView) findViewById(R.id.flex_value1);
        TextView flex2_id = (TextView) findViewById(R.id.flex2);
        TextView flex2_value = (TextView) findViewById(R.id.flex_value2);
        TextView flex3_id = (TextView) findViewById(R.id.flex3);
        TextView flex3_value = (TextView) findViewById(R.id.flex_value3);
           
        mealplan_id.setTypeface(tf);
        mealplan_value.setTypeface(tf);
        flex1_id.setTypeface(tf);
        flex1_value.setTypeface(tf);
        flex2_id.setTypeface(tf);
        flex2_value.setTypeface(tf);
        flex3_id.setTypeface(tf);
        flex3_value.setTypeface(tf);
        
        mealplan_id.setText("MealPlan $");
        flex1_id.setText("Flexible $");
        flex2_id.setText("Flexible $");
        flex3_id.setText("Flexible $");
        
        for(int i = 0; i < holder.objects.length; i++){
            if(holder.objects[i].getID().equals("1")){
                mealplan_value.setText(holder.objects[i].getAmount());
            }
            if(holder.objects[i].getID().equals("4")){
                flex1_value.setText(holder.objects[i].getAmount());
            }
            if(holder.objects[i].getID().equals("5")){
               flex2_value.setText(holder.objects[i].getAmount());
            }
            if(holder.objects[i].getID().equals("6")){
                flex3_value.setText(holder.objects[i].getAmount());
            }
        }
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.balance_watcard, menu);
        return true;
    }

}
