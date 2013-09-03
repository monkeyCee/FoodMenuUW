package ca.uwaterloo.uwfoodservicesutility;

import java.util.List;

public class ProductInfoHolder{

    private static ProductInfoHolder mInstance = null;
    public List<ProductInfoObject> productInfo;

    public static ProductInfoHolder getInstance(List<ProductInfoObject> productInfo){
        if(mInstance == null){
            mInstance = new ProductInfoHolder(productInfo);
        }
        return mInstance;
    }

    public static ProductInfoHolder getInstance(){
        return mInstance;
    }

    private ProductInfoHolder(List<ProductInfoObject> productInfo){
        this.productInfo = productInfo;
    }

    public int getCount(){
        return productInfo.size();
    }
    
    public static void resetInstance() {
        mInstance = null;
    }

}
