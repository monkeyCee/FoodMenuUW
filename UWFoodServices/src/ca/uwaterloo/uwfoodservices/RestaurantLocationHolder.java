package ca.uwaterloo.uwfoodservices;

public class RestaurantLocationHolder{

    private static RestaurantLocationHolder mInstance = null;
    public RestaurantObject[] objects;

    public static RestaurantLocationHolder getInstance(RestaurantObject[] objects){
        if(mInstance == null){
            mInstance = new RestaurantLocationHolder(objects);
        }
        return mInstance;
    }

    public static RestaurantLocationHolder getInstance(){
        return mInstance;
    }

    private RestaurantLocationHolder(RestaurantObject[] objects){
        this.objects = objects;			
    }

    public int getCount(){
        return this.objects.length;
    }

}
