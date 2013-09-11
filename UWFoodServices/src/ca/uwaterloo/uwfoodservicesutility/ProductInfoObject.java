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
    Double serving_size_amount;
    String serving_size_unit;
    Double calories;
    Double total_fat_g;
    Double total_fat_percent;
    Double fat_saturated_g;
    Double fat_saturated_percent;
    Double fat_trans_g;
    Double fat_trans_percent;
    Double cholesterol_mg;
    Double sodium_mg;
    Double sodium_percent;
    Double carbo_g;
    Double carbo_percent;
    Double carbo_fibre_g;
    Double carbo_fibre_percent;
    Double carbo_sugars_g;
    Double protein_g;
    Double vitamin_a_percent;
    Double vitamin_c_percent;
    Double calcium_percent;
    Double iron_percent;
    List<String> micro_nutrients;
    String tips;
    Double diet_id;
    String diet_type;

    public ProductInfoObject(Integer product_id, String product_name, List<String> ingredients,
            String serving_size, Double serving_size_amount, String serving_size_unit, Double calories, Double total_fat_g, 
            Double total_fat_percent, Double fat_saturated_g, Double fat_saturated_percent, Double fat_trans_g,
            Double fat_trans_percent, Double cholesterol_mg, Double sodium_mg, Double sodium_percent,
            Double carbo_g, Double carbo_percent, Double carbo_fibre_g, Double carbo_fibre_percent,
            Double carbo_sugars_g, Double protein_g, Double vitamin_a_percent, Double vitamin_c_percent,
            Double calcium_percent, Double iron_percent, List<String> micro_nutrients, String tips,
            Double diet_id, String diet_type){

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
    public Double get_serving_size_amount(){ return serving_size_amount; }
    public String get_serving_size_unit(){ return serving_size_unit; }
    public Double get_calories(){ return calories; }
    public Double get_total_fat_g(){ return total_fat_g; }
    public Double get_total_fat_percent(){ return total_fat_percent; }
    public Double get_fat_saturated_g(){ return fat_saturated_g; }
    public Double get_fat_saturated_percent(){ return fat_saturated_percent; }
    public Double get_fat_trans_g(){ return fat_trans_g; }
    public Double get_fat_trans_percent(){ return fat_trans_percent; }
    public Double get_cholesterol_mg(){ return cholesterol_mg; }
    public Double get_sodium_mg(){ return sodium_mg; }
    public Double get_sodium_percent(){ return sodium_percent; }
    public Double get_carbo_g(){ return carbo_g; }
    public Double get_carbo_percent(){ return carbo_percent; }
    public Double get_carbo_fibre_g(){ return carbo_fibre_g; }
    public Double get_carbo_fibre_percent(){ return carbo_fibre_percent; }
    public Double get_carbo_sugars_g(){ return carbo_sugars_g; }
    public Double get_protein_g(){ return protein_g; }
    public Double get_vitamin_a_percent(){ return vitamin_a_percent; }
    public Double get_vitamin_c_percent(){ return vitamin_c_percent; }
    public Double get_calcium_percent(){ return calcium_percent; }
    public Double get_iron_percent(){ return iron_percent; }
    public List<String> get_micro_nutrients(){ return micro_nutrients; }
    public String get_tips(){ return tips; }
    public Double get_get_diet_id(){ return diet_id; }
    public String get_diet_type(){ return diet_type; }

    public void product_id(Integer product_id){ this.product_id = product_id; }
    public void product_name(String product_name){ this.product_name = product_name; }
    public void ingredients(ArrayList<String> ingredients){ this.ingredients = ingredients; }
    public void serving_size(String serving_size){ this.serving_size = serving_size; }
    public void serving_size(Double serving_size_amount){ this.serving_size_amount = serving_size_amount; }
    public void serving_size_unit(String serving_size_unit){ this.serving_size_unit = serving_size_unit; }
    public void calories(Double calories){ this.calories = calories; }
    public void total_fat_g(Double total_fat_g){ this.total_fat_g = total_fat_g; }
    public void total_fat_percent(Double total_fat_percent){ this.total_fat_percent = total_fat_percent; }
    public void fat_saturated_g(Double fat_saturated_g){ this.fat_saturated_g = fat_saturated_g; }
    public void fat_saturated_percent(Double fat_saturated_percent){ this.fat_saturated_percent = fat_saturated_percent; }
    public void fat_trans_g(Double fat_trans_g){ this.fat_trans_g = fat_trans_g; }
    public void fat_trans_percent(Double fat_trans_percent){ this.fat_trans_percent = fat_trans_percent; }
    public void cholesterol_mg(Double cholesterol_mg){ this.cholesterol_mg = cholesterol_mg; }
    public void sodium_mg(Double sodium_mg){ this.sodium_mg = sodium_mg; }
    public void sodium_percent(Double sodium_percent){ this.sodium_percent = sodium_percent; }
    public void carbo_g(Double carbo_g){ this.carbo_g = carbo_g; }
    public void carbo_percent(Double carbo_percent){ this.carbo_percent = carbo_percent; }
    public void carbo_fibre_g(Double carbo_fibre_g){ this.carbo_fibre_g = carbo_fibre_g; }
    public void carbo_fibre_percent(Double carbo_fibre_percent){ this.carbo_fibre_percent = carbo_fibre_percent; }
    public void carbo_sugars_g(Double carbo_sugars_g){ this.carbo_sugars_g = carbo_sugars_g; }
    public void protein_g(Double protein_g){ this.protein_g = protein_g; }
    public void vitamin_a_percent(Double vitamin_a_percent){ this.vitamin_a_percent = vitamin_a_percent; }
    public void vitamin_c_percent(Double vitamin_c_percent){ this.vitamin_c_percent = vitamin_c_percent; }
    public void calcium_percent(Double calcium_percent){ this.calcium_percent = calcium_percent; }
    public void iron_percent(Double iron_percent){ this.iron_percent = iron_percent; }
    public void micro_nutrients(ArrayList<String> micro_nutrients){ this.micro_nutrients = micro_nutrients; }
    public void tips(String tips){ this.tips = tips; }
    public void diet_id(Double diet_id){ this.diet_id = diet_id; }
    public void diet_type(String diet_type){ this.diet_type = diet_type; }

}
