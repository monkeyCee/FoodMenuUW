package ca.uwaterloo.uwfoodservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static final int database_version = 1;
	private static final String database_name = "RestaurantDB";
	private static final String table_name = "RestaurantTable";
	private static final String key_id = "ID";
	private static final String restaurant_id = "Restaurant";
	private static final String location_id = "Location";
	private static final String timings_id = "Timings";
	private static DatabaseHandler mInstance = null;
	int j = 0;
	
	public static DatabaseHandler getInstance(Context context){
		if(mInstance == null){
			mInstance = new DatabaseHandler(context.getApplicationContext());
		}
		return mInstance;
	}
	
	public DatabaseHandler(Context context) {
		super(context, database_name, null, database_version);
	}

	public void truncate(){
		SQLiteDatabase db;
		Log.d("Dropping table", "Ok");
		db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + table_name);
		onCreate(db);
		db.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("Inside On create ", Integer.toString(j+1));
		j++;
		String create_table = "CREATE TABLE " + table_name + "(" + key_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
						restaurant_id + " TEXT," +
						location_id + " TEXT," +
						timings_id + " TEXT" + ");";
		db.execSQL(create_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
		
	}
	
	public void addRestaurant(RestaurantObject restaurantObject){
		SQLiteDatabase db;
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(restaurant_id, restaurantObject.getRestaurant());
		values.put(location_id, restaurantObject.getLocation());
		values.put(timings_id, restaurantObject.getTimings());
		
		db.insert(table_name, null, values);
		db.close();

	}
	
	public RestaurantObject getRestaurant(int id){
		SQLiteDatabase db;
		db = this.getReadableDatabase();
	
		Cursor cursor = db.query(table_name, new String[] { key_id,
	            restaurant_id, location_id, timings_id }, key_id + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    RestaurantObject restaurant_object = new RestaurantObject(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2), cursor.getString(3));
	    cursor.close();
	    db.close();
	    // return restaurant
	    return restaurant_object;
		
	}
	
	 public int getCount() { 
		 	String countQuery = "SELECT  * FROM " + table_name;
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        int count = cursor.getCount();
	        cursor.close();
	 
	        // return count
	        return count;
	    }
	
	

}
