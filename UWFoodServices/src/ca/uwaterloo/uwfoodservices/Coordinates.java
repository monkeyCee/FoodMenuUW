package ca.uwaterloo.uwfoodservices;

import java.util.HashMap;

import com.google.android.gms.maps.model.LatLng;

public class Coordinates {

    public HashMap<String, LatLng> map;

    public Coordinates(){

        map = new HashMap<String,LatLng>();

        map.put("Bon Appetit Davis Centre", new LatLng(43.472883, -80.543051));
        map.put("Browsers Café Dana Porter Library", new LatLng(43.469681,-80.541998));
        map.put("Brubakers Student Life Centre", new LatLng(43.472051,-80.545284));
        map.put("CEIT Café CEIT Building", new LatLng(43.471814,-80.542223));
        map.put("Eye-Opener Café Optometry Building", new LatLng(43.475454,-80.54565));
        map.put("Festival Fare Above the Bookstore in SCH", new LatLng(43.469202,-80.540234));
        map.put("Liquid Assets Café Hagey Hall", new LatLng(43.467317,-80.542421));
        map.put("ML's Coffee Shop Modern Languages", new LatLng(43.468851,-80.542705));
        map.put("Mudie's Village 1", new LatLng(43.471799,-80.550034));
        map.put("PAS Lounge PAS Building", new LatLng(43.467187,-80.542481));
        map.put("Pastry Plus B.C. Matthews Hall", new LatLng(43.473578,-80.545484));
        map.put("Pastry Plus Needles Hall", new LatLng(43.469737,-80.543809));
        map.put("Pastry Plus Tatham Centre", new LatLng(43.469052,-80.541242));
        map.put("REVelation Ron Eydt Village", new LatLng(43.470275,-80.554133));
        map.put("Subway Student Life Centre", new LatLng(43.472106,-80.545223));
        map.put("Tim Hortons Modern Languages", new LatLng(43.468832,-80.542677));
        map.put("Tim Hortons Davis Centre", new LatLng(43.472959,-80.542799));
        map.put("Tim Hortons South Campus Hall", new LatLng(43.469202,-80.540008));
        map.put("Tim Hortons Student Life Centre", new LatLng(43.471344,-80.54532));
        map.put("University Club ", new LatLng(43.472284,-80.547364));
        map.put("UW Food Services Admin Office ", new LatLng(43.469591,-80.543349));
        map.put("Williams Fresh Café Environment 3", new LatLng(43.46824,-80.543573));

    }

}
