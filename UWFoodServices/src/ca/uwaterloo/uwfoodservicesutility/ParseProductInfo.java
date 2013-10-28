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
    private static final String TAG_FAT_TRANS_G = "fat_trans_g";
    private static final String TAG_FAT_TRANS_PERCENT = "fat_trans_percent";
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
                JSONObject meta = jsonObject.getJSONObject(TAG_META);

                JSONObject data = jsonObject.getJSONObject(TAG_DATA);
                Integer product_id = MenuUtilities.getInteger(data.getString(TAG_PRODUCT_ID)); 
                String product_name = data.getString(TAG_PRODUCT_NAME);
                List<String> ingredients = Arrays.asList(data.getString(TAG_INGREDIENTS).split(", ")); //
                String serving_size = data.getString(TAG_SERVING_SIZE);
                Double serving_size_amount = MenuUtilities.getDouble(data.getString(TAG_SERVING_SIZE)
                        .replaceAll("\\D+",""));
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
                Double calories = MenuUtilities.getDouble(data.getString(TAG_CALORIES));
                Double total_fat_g = MenuUtilities.getDouble(data.getString(TAG_TOTAL_FAT_G));
                Double total_fat_percent = MenuUtilities.getDouble(data.getString(TAG_TOTAL_FAT_PERCENT));
                Double fat_saturated_g = MenuUtilities.getDouble(data.getString(TAG_FAT_SATURATED_G));
                Double fat_saturated_percent = MenuUtilities.getDouble(data.getString(TAG_FAT_SATURATED_PERCENT));
                Double fat_trans_g = MenuUtilities.getDouble(data.getString(TAG_FAT_TRANS_G));
                Double fat_trans_percent = MenuUtilities.getDouble(data.getString(TAG_FAT_TRANS_PERCENT));
                Double cholesterol_mg = MenuUtilities.getDouble(data.getString(TAG_CHOLESTEROL_MG));
                Double sodium_mg = MenuUtilities.getDouble(data.getString(TAG_SODIUM_MG));
                Double sodium_percent = MenuUtilities.getDouble(data.getString(TAG_SODIUM_PERCENT));
                Double carbo_g = MenuUtilities.getDouble(data.getString(TAG_CARBO_G));
                Double carbo_percent = MenuUtilities.getDouble(data.getString(TAG_CARBO_PERCENT));
                Double carbo_fibre_g = MenuUtilities.getDouble(data.getString(TAG_CARBO_FIBRE_G));
                Double carbo_fibre_percent = MenuUtilities.getDouble(data.getString(TAG_CARBO_FIBRE_PERCENT));
                Double carbo_sugars_g = MenuUtilities.getDouble(data.getString(TAG_CARBO_SUGARS_G));
                Double protein_g = MenuUtilities.getDouble(data.getString(TAG_PROTEIN_G));
                Double vitamin_a_percent = MenuUtilities.getDouble(data.getString(TAG_VITAMIN_A_PERCENT));
                Double vitamin_c_percent = MenuUtilities.getDouble(data.getString(TAG_VITAMIN_C_PERCENT));
                Double calcium_percent = MenuUtilities.getDouble(data.getString(TAG_CALCIUM_PERCENT));
                Double iron_percent = MenuUtilities.getDouble(data.getString(TAG_IRON_PERCENT));
                List<String> micro_nutrients = Arrays.asList(data.getString(TAG_MICRO_NUTRIENTS).split(", "));
                String tips = data.getString(TAG_TIPS);
                Double diet_id = MenuUtilities.getDouble(data.getString(TAG_DIET_ID));
                String diet_type = data.getString(TAG_DIET_TYPE);
                productInfoList.add(new ProductInfoObject(product_id, product_name, ingredients, serving_size, 
                        serving_size_amount, serving_size_unit, calories, total_fat_g, total_fat_percent, 
                        fat_saturated_g, fat_saturated_percent, fat_trans_g, fat_trans_percent, cholesterol_mg,
                        sodium_mg, sodium_percent, carbo_g, carbo_percent, carbo_fibre_g, carbo_fibre_percent,
                        carbo_sugars_g, protein_g, vitamin_a_percent, vitamin_c_percent, calcium_percent, iron_percent, 
                        micro_nutrients, tips, diet_id, diet_type));
            }

            holder = ProductInfoHolder.getInstance(productInfoList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}