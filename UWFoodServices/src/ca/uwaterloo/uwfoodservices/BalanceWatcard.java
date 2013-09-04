package ca.uwaterloo.uwfoodservices;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class BalanceWatcard extends Activity {

    private WatcardHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_watcard);

        holder = WatcardHolder.getInstance();

        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "Roboto-Light.ttf");

        TextView name = (TextView) findViewById(R.id.name);
        TextView mealplan_id = (TextView) findViewById(R.id.meal_text);
        TextView mealplan_value = (TextView) findViewById(R.id.meal_value);
        TextView flex1_id = (TextView) findViewById(R.id.flex_text);
        TextView flex1_value = (TextView) findViewById(R.id.flex_value);
        TextView total_id = (TextView) findViewById(R.id.total_text);
        TextView total_value = (TextView) findViewById(R.id.total_value);

        name.setTypeface(tf);
        mealplan_id.setTypeface(tf);
        mealplan_value.setTypeface(tf);
        flex1_id.setTypeface(tf);
        flex1_value.setTypeface(tf);
        total_id.setTypeface(tf);
        total_value.setTypeface(tf);

        mealplan_id.setText("MealPlan $");
        flex1_id.setText("Flexible $");
        total_id.setText("Total");

        name.setText(holder.getName());
        mealplan_value.setText(holder.getMealplan());
        flex1_value.setText(holder.getFlex());
        total_value.setText(holder.getTotal());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        holder.reset();
        finish();
    }

    @Override
    public void onStop() {  
        super.onStop();
        holder.reset();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.balance_watcard, menu);
        return true;
    }

}
