package ca.uwaterloo.uwfoodservicesutility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ParseProductInfo {

    private static final String TAG_META = "meta";

    private static final String TAG_DATA = "data";
    
    private static final String TAG_PRODUCT_ID = "product_id";
    private static final String TAG_PRODUCT_NAME = "product_name";
    private static final String TAG_INGREDIENTS = "ingredients";
    private static final String TAG_SERVING_SIZE = "serving_size";
    private static final String TAG_SERVING_SIZE_ML = "serving_size_ml";
    private static final String TAG_SERVING_SIZE_G = "serving_size_g";
    private static final String TAG_CALORIES = "calories";
    private static final String TAG_TOTAL_FAT_G = "total_fat_g";
    private static final String TAG_TOTAL_FAT_PERCENT = "total_fat_percent";
    private static final String TAG_FAT_SATURATED_G = "fat_saturated_g";
    private static final String TAG_FAT_SATURATED_PERCENT = "fat_saturated_percent";
    private static final String TAG_FAT_TRANS_G = "fat_saturated_g";
    private static final String TAG_FAT_TRANS_PERCENT = "fat_saturated_percent";
    private static final String TAG_CHOLESTEROL_MG = "cholesterol_mg";
    private static final String TAG_SODIUM_MG = "sodium_mg";
    private static final String TAG_SODIUM_PERCENT = "sodium_percent";
    private static final String TAG_CARBO_G = "carbo_g";
    private static final String TAG_CARBO_PERCENT = "carbo_percent";
    private static final String TAG_CARBO_FIBRE_G = "carbo_fibre_g";
    private static final String TAG_CARBO_FIBRE_PERCENT = "carbo_fibre_percent";
    private static final String TAG_CARBO_SUGARS_G = "carbo_sugars_g";
    private static final String TAG_PROTEIN_G = "protein_g";
    private static final String TAG_VITAMIN_A_PERCENT = "vitamin_a_percent";
    private static final String TAG_VITAMIN_C_PERCENT = "vitamin_c_percent";
    private static final String TAG_CALCIUM_PERCENT = "calcium_percent";
    private static final String TAG_IRON_PERCENT = "iron_percent";
    private static final String TAG_MICRO_NUTRIENTS = "micro_nutrients";
    private static final String TAG_TIPS = "tips";
    private static final String TAG_DIET_ID = "diet_id";
    private static final String TAG_DIET_TYPE = "diet_type";

    private static ProductInfoHolder holder;
    
    public ParseProductInfo(){
    }

    public void Parse(List<JSONObject> json){

        List<ProductInfoObject> productInfoList = new ArrayList<ProductInfoObject>();
        try {
            for (JSONObject jsonObject : json) {
                Log.d("YES1", "LOADED");
                JSONObject meta = jsonObject.getJSONObject(TAG_META);
                
                JSONObject data = jsonObject.getJSONObject(TAG_DATA);
                
                Integer product_id = Integer.getInteger(data.getString(TAG_PRODUCT_ID)); 
                String product_name = data.getString(TAG_PRODUCT_NAME);
                List<String> ingredients = Arrays.asList(data.getString(TAG_INGREDIENTS).split(", ")); //
                Integer serving_size = Integer.getInteger(data.getString(TAG_SERVING_SIZE).replaceAll("\\D+",""));
                String serving_size_unit;
                if (data.getString(TAG_SERVING_SIZE).contains("grams")) {
                    serving_size_unit = "g";
                } else if (data.getString(TAG_SERVING_SIZE_G) != null) {
                    serving_size_unit = "g";
                } else if (data.getString(TAG_SERVING_SIZE_ML) != null) {
                    serving_size_unit = "ml";
                } else {
                    serving_size_unit = "ml";
                }
                Log.d("YES2", "LOADED");
                Integer calories = Integer.getInteger(data.getString(TAG_CALORIES));
                Integer total_fat_g = Integer.getInteger(data.getString(TAG_TOTAL_FAT_G));
                Integer total_fat_percent = Integer.getInteger(data.getString(TAG_TOTAL_FAT_PERCENT));
                Integer fat_saturated_g = Integer.getInteger(data.getString(TAG_FAT_SATURATED_G));
                Integer fat_saturated_percent = Integer.getInteger(data.getString(TAG_FAT_SATURATED_PERCENT));
                Integer fat_trans_g = Integer.getInteger(data.getString(TAG_FAT_TRANS_G));
                Integer fat_trans_percent = Integer.getInteger(data.getString(TAG_FAT_TRANS_PERCENT));
                Integer cholesterol_mg = Integer.getInteger(data.getString(TAG_CHOLESTEROL_MG));
                Integer sodium_mg = Integer.getInteger(data.getString(TAG_SODIUM_MG));
                Integer sodium_percent = Integer.getInteger(data.getString(TAG_SODIUM_PERCENT));
                Integer carbo_g = Integer.getInteger(data.getString(TAG_CARBO_G));
                Integer carbo_percent = Integer.getInteger(data.getString(TAG_CARBO_PERCENT));
                Integer carbo_fibre_g = Integer.getInteger(data.getString(TAG_CARBO_FIBRE_G));
                Integer carbo_fibre_percent = Integer.getInteger(data.getString(TAG_CARBO_FIBRE_PERCENT));
                Integer carbo_sugars_g = Integer.getInteger(data.getString(TAG_CARBO_SUGARS_G));
                Integer protein_g = Integer.getInteger(data.getString(TAG_PROTEIN_G));
                Integer vitamin_a_percent = Integer.getInteger(data.getString(TAG_VITAMIN_A_PERCENT));
                Integer vitamin_c_percent = Integer.getInteger(data.getString(TAG_VITAMIN_C_PERCENT));
                Integer calcium_percent = Integer.getInteger(data.getString(TAG_CALCIUM_PERCENT));
                Integer iron_percent = Integer.getInteger(data.getString(TAG_IRON_PERCENT));
                List<String> micro_nutrients = Arrays.asList(data.getString(TAG_MICRO_NUTRIENTS).split(", "));
                String tips = data.getString(TAG_TIPS);
                Integer diet_id = Integer.parseInt(data.getString(TAG_DIET_ID));
                String diet_type = data.getString(TAG_DIET_TYPE);
                Log.d("YES4", "LOADED");
                productInfoList.add(new ProductInfoObject(product_id, product_name, ingredients, serving_size, serving_size_unit, 
                        calories, total_fat_g, total_fat_percent, fat_saturated_g, fat_saturated_percent, fat_trans_g,
                        fat_trans_percent, cholesterol_mg, sodium_mg, sodium_percent, carbo_g, carbo_percent, carbo_fibre_g,
                        carbo_fibre_percent, carbo_sugars_g, protein_g, vitamin_a_percent, vitamin_c_percent, calcium_percent,
                        iron_percent, micro_nutrients, tips, diet_id, diet_type));
                Log.d("YES5", "LOADED");
            }
            
            holder = ProductInfoHolder.getInstance(productInfoList);
            
            Log.d("LOAD SOMETHING", "LOADED");
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
    }

}