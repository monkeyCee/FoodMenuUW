package ca.uwaterloo.uwfoodservices;

public class WatcardHolder {
    
    private static WatcardHolder mInstance = null;
    public WatcardObject[] objects;
    private float totalFlex = 0;
    private float mealPlan = 0;
    private String total;
    
    public static WatcardHolder getInstance(WatcardObject[] objects){
        if(mInstance == null){
            mInstance = new WatcardHolder(objects);
        }
        return mInstance;
    }
    
    private WatcardHolder(WatcardObject[] objects){
        this.objects = objects;
    }
    
    public static WatcardHolder getInstance(){
        return mInstance;
    }
    
    public void reset(){
        mInstance = null;
    }
    
    public void setFlex(float flex){
        totalFlex = flex;
    }
    
    public void setMealplan(float mealPlan){
        this.mealPlan = mealPlan;
    }
    
    public void setTotal(String total){
        this.total = total;  
    }
    
    public String getFlex(){
        return Float.toString(totalFlex);  
    }
    
    public String getMealplan(){
        return Float.toString(mealPlan);  
    }
    
    public String getTotal(){
        return total;  
    }
}
