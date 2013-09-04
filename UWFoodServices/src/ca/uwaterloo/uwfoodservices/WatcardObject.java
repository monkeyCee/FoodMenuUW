package ca.uwaterloo.uwfoodservices;

public class WatcardObject {

    private String id;
    private String type;
    private String name;
    private String percent;
    private String price;
    private String amount;
    private String credit;

    public WatcardObject(String id, String type, String name, String percent, String price, String amount, String credit){
        this.id = id;
        this.type = type;
        this.name = name;
        this.percent = percent;
        this.price = price;
        this.amount = amount;
        this.credit = credit;
    }

    public void setID(String id){
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPercent(String percent){
        this.percent = percent;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public void setCredit(String credit){
        this.credit = credit;
    }

    public String getID(){
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public String getPercent(){
        return this.percent;
    }

    public String getPrice(){
        return this.price;
    }

    public String getAmount(){
        return this.amount;
    }

    public String getCredit(){
        return this.credit;
    }


}
