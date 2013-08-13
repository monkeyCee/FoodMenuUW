package ca.uwaterloo.uwfoodservices;

public class WatcardHolder {
    
    private static WatcardHolder mInstance = null;
    public WatcardObject[] objects;
    
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
    
}
