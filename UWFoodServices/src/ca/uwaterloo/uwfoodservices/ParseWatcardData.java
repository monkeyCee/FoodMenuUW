package ca.uwaterloo.uwfoodservices;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ParseWatcardData {
    
    Document doc;
    private WatcardHolder holder;
    Context context;
    WatcardObject[] objects;
    
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
        
        for(Element table : this.doc.select("table[id=oneweb_balance_information_table]")){ 
            for (Element row : table.select("tr:gt(1)")) {                   
                objects[i] = new WatcardObject(row.getElementById("oneweb_balance_information_td_number").text(), 
                            row.getElementById("oneweb_balance_information_td_type").text(),
                            row.getElementById("oneweb_balance_information_td_name").text(), 
                            row.getElementById("oneweb_balance_information_td_percent").text(), 
                            row.getElementById("oneweb_balance_information_td_price").text(),
                            row.getElementById("oneweb_balance_information_td_amount").text(), 
                            row.getElementById("oneweb_balance_information_td_credit").text());
                i++;               
            }
        }
        
        WatcardHolder.getInstance(objects);
        return true;
    }

}
