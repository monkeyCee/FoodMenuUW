package ca.uwaterloo.uwfoodservices;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;

public class ParseWatcardData {

    Document doc;
    private WatcardHolder holder;
    Context context;
    WatcardObject[] objects;
    private float totalFlex = 0;
    private float mealPlan = 0;
    private String total;
    private String name;

    public ParseWatcardData(Document doc, Context context){
        this.doc = doc;
        this.context = context;
        objects = new WatcardObject[13];
    }

    public boolean parse(){
        int i = 0;

        if(doc.getElementById("oneweb_message_invalid_login") != null){
            return false;
        }

        String name = doc.getElementById("oneweb_account_name").text();

        for(Element table : this.doc.select("table[id=oneweb_balance_information_table]")){ 
            for (Element row : table.select("tr:gt(1)")) {                   
                objects[i] = new WatcardObject(row.getElementById("oneweb_balance_information_td_number").text(), 
                        row.getElementById("oneweb_balance_information_td_type").text(),
                        row.getElementById("oneweb_balance_information_td_name").text(), 
                        row.getElementById("oneweb_balance_information_td_percent").text(), 
                        row.getElementById("oneweb_balance_information_td_price").text(),
                        row.getElementById("oneweb_balance_information_td_amount").text(), 
                        row.getElementById("oneweb_balance_information_td_credit").text());
                if(objects[i].getID().equals("1") || objects[i].getID().equals("2") || objects[i].getID().equals("3")){
                    mealPlan += Float.parseFloat(row.getElementById("oneweb_balance_information_td_amount").text());
                }
                if(objects[i].getID().equals("4") || objects[i].getID().equals("5") || objects[i].getID().equals("6")){
                    totalFlex += Float.parseFloat(row.getElementById("oneweb_balance_information_td_amount").text());
                }
                if(i == 12){
                    total = row.getElementById("oneweb_balance_information_td_amount").text();
                }
                i++;               
            }
        }

        WatcardHolder.getInstance(objects).setMealplan(mealPlan);
        WatcardHolder.getInstance().setTotal(total);
        WatcardHolder.getInstance().setFlex(totalFlex);
        WatcardHolder.getInstance().setName(name);
        return true;
    }

}
