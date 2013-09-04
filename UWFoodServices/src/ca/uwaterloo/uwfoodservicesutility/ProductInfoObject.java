package ca.uwaterloo.uwfoodservicesutility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductInfoObject implements Serializable {

    private static final long serialVersionUID = -3378840073417894876L;
    Integer product_id;
    String product_name;
    List<String> ingredients;
    String serving_size;
    Integer serving_size_amount;
    String serving_size_unit;
    Integer calories;
    Integer total_fat_g;
    Integer total_fat_percent;
    Integer fat_saturated_g;
    Integer fat_saturated_percent;
    Integer fat_trans_g;
    Integer fat_trans_percent;
    Integer cholesterol_mg;
    Integer sodium_mg;
    Integer sodium_percent;
    Integer carbo_g;
    Integer carbo_percent;
    Integer carbo_fibre_g;
    Integer carbo_fibre_percent;
    Integer carbo_sugars_g;
    Integer protein_g;
    Integer vitamin_a_percent;
    Integer vitamin_c_percent;
    Integer calcium_percent;
    Integer iron_percent;
    List<String> micro_nutrients;
    String tips;
    Integer diet_id;
    String diet_type;
    
    public ProductInfoObject(Integer product_id, String product_name, List<String> ingredients,
            String serving_size, Integer serving_size_amount, String serving_size_unit, Integer calories, Integer total_fat_g, 
            Integer total_fat_percent, Integer fat_saturated_g, Integer fat_saturated_percent, Integer fat_trans_g,
            Integer fat_trans_percent, Integer cholesterol_mg, Integer sodium_mg, Integer sodium_percent,
            Integer carbo_g, Integer carbo_percent, Integer carbo_fibre_g, Integer carbo_fibre_percent,
            Integer carbo_sugars_g, Integer protein_g, Integer vitamin_a_percent, Integer vitamin_c_percent,
            Integer calcium_percent, Integer iron_percent, List<String> micro_nutrients, String tips,
            Integer diet_id, String diet_type){
        
        this.product_id = product_id;
        this.product_name = product_name;
        this.ingredients = ingredients;
        this.serving_size = serving_size;
        this.serving_size_amount = serving_size_amount;
        this.serving_size_unit = serving_size_unit;
        this.calories = calories;
        this.total_fat_g = total_fat_g;
        this.total_fat_percent = total_fat_percent;
        this.fat_saturated_g = fat_saturated_g;
        this.fat_saturated_percent = fat_saturated_percent;
        this.fat_trans_g = fat_trans_g;
        this.fat_trans_percent = fat_trans_percent;
        this.cholesterol_mg = cholesterol_mg;
        this.sodium_mg = sodium_mg;
        this.sodium_percent = sodium_percent;
        this.carbo_g = carbo_g;
        this.carbo_percent = carbo_percent;
        this.carbo_fibre_g = carbo_fibre_g;
        this.carbo_fibre_percent = carbo_fibre_percent;
        this.carbo_sugars_g = carbo_sugars_g;
        this.protein_g = protein_g;
        this.vitamin_a_percent = vitamin_a_percent;
        this.vitamin_c_percent = vitamin_c_percent;
        this.calcium_percent = calcium_percent;
        this.iron_percent = iron_percent;
        this.micro_nutrients = micro_nutrients;
        this.tips = tips;
        this.diet_id = diet_id;
        this.diet_type = diet_type;
    }

    public Integer get_product_id(){ return product_id; }
    public String get_product_name(){ return product_name; }
    public List<String> get_ingredients(){ return ingredients; }
    public String get_serving_size(){ return serving_size; }
    public Integer get_serving_size_amount(){ return serving_size_amount; }
    public String get_serving_size_unit(){ return serving_size_unit; }
    public Integer get_calories(){ return calories; }
    public Integer get_total_fat_g(){ return total_fat_g; }
    public Integer get_total_fat_percent(){ return total_fat_percent; }
    public Integer get_fat_saturated_g(){ return fat_saturated_g; }
    public Integer get_fat_saturated_percent(){ return fat_saturated_percent; }
    public Integer get_fat_trans_g(){ return fat_trans_g; }
    public Integer get_fat_trans_percent(){ return fat_trans_percent; }
    public Integer get_cholesterol_mg(){ return cholesterol_mg; }
    public Integer get_sodium_mg(){ return sodium_mg; }
    public Integer get_sodium_percent(){ return sodium_percent; }
    public Integer get_carbo_g(){ return carbo_g; }
    public Integer get_carbo_percent(){ return carbo_percent; }
    public Integer get_carbo_fibre_g(){ return carbo_fibre_g; }
    public Integer get_carbo_fibre_percent(){ return carbo_fibre_percent; }
    public Integer get_carbo_sugars_g(){ return carbo_sugars_g; }
    public Integer get_protein_g(){ return protein_g; }
    public Integer get_vitamin_a_percent(){ return vitamin_a_percent; }
    public Integer get_vitamin_c_percent(){ return vitamin_c_percent; }
    public Integer get_calcium_percent(){ return calcium_percent; }
    public Integer get_iron_percent(){ return iron_percent; }
    public List<String> get_micro_nutrients(){ return micro_nutrients; }
    public String get_tips(){ return tips; }
    public Integer get_get_diet_id(){ return diet_id; }
    public String get_diet_type(){ return diet_type; }

    public void product_id(Integer product_id){ this.product_id = product_id; }
    public void product_name(String product_name){ this.product_name = product_name; }
    public void ingredients(ArrayList<String> ingredients){ this.ingredients = ingredients; }
    public void serving_size(String serving_size){ this.serving_size = serving_size; }
    public void serving_size(Integer serving_size_amount){ this.serving_size_amount = serving_size_amount; }
    public void serving_size_unit(String serving_size_unit){ this.serving_size_unit = serving_size_unit; }
    public void calories(Integer calories){ this.calories = calories; }
    public void total_fat_g(Integer total_fat_g){ this.total_fat_g = total_fat_g; }
    public void total_fat_percent(Integer total_fat_percent){ this.total_fat_percent = total_fat_percent; }
    public void fat_saturated_g(Integer fat_saturated_g){ this.fat_saturated_g = fat_saturated_g; }
    public void fat_saturated_percent(Integer fat_saturated_percent){ this.fat_saturated_percent = fat_saturated_percent; }
    public void fat_trans_g(Integer fat_trans_g){ this.fat_trans_g = fat_trans_g; }
    public void fat_trans_percent(Integer fat_trans_percent){ this.fat_trans_percent = fat_trans_percent; }
    public void cholesterol_mg(Integer cholesterol_mg){ this.cholesterol_mg = cholesterol_mg; }
    public void sodium_mg(Integer sodium_mg){ this.sodium_mg = sodium_mg; }
    public void sodium_percent(Integer sodium_percent){ this.sodium_percent = sodium_percent; }
    public void carbo_g(Integer carbo_g){ this.carbo_g = carbo_g; }
    public void carbo_percent(Integer carbo_percent){ this.carbo_percent = carbo_percent; }
    public void carbo_fibre_g(Integer carbo_fibre_g){ this.carbo_fibre_g = carbo_fibre_g; }
    public void carbo_fibre_percent(Integer carbo_fibre_percent){ this.carbo_fibre_percent = carbo_fibre_percent; }
    public void carbo_sugars_g(Integer carbo_sugars_g){ this.carbo_sugars_g = carbo_sugars_g; }
    public void protein_g(Integer protein_g){ this.protein_g = protein_g; }
    public void vitamin_a_percent(Integer vitamin_a_percent){ this.vitamin_a_percent = vitamin_a_percent; }
    public void vitamin_c_percent(Integer vitamin_c_percent){ this.vitamin_c_percent = vitamin_c_percent; }
    public void calcium_percent(Integer calcium_percent){ this.calcium_percent = calcium_percent; }
    public void iron_percent(Integer iron_percent){ this.iron_percent = iron_percent; }
    public void micro_nutrients(ArrayList<String> micro_nutrients){ this.micro_nutrients = micro_nutrients; }
    public void tips(String tips){ this.tips = tips; }
    public void diet_id(Integer diet_id){ this.diet_id = diet_id; }
    public void diet_type(String diet_type){ this.diet_type = diet_type; }
    
}
