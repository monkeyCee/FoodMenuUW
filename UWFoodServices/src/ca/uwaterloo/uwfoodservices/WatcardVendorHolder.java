package ca.uwaterloo.uwfoodservices;

public class WatcardVendorHolder {

    private static WatcardVendorHolder mInstance = null;
    public WatcardVendorObject[] objects;

    public static WatcardVendorHolder getInstance(WatcardVendorObject[] objects){
        if(mInstance == null){
            mInstance = new WatcardVendorHolder(objects);
        }
        return mInstance;
    }

    public static WatcardVendorHolder getInstance(){
        return mInstance;
    }

    private WatcardVendorHolder(WatcardVendorObject[] objects){
        this.objects = objects;         
    }

    public int getCount(){
        return this.objects.length;
    }

}
