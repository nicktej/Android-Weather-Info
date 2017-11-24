package hu.ait.demos.weatherinfo.data;

import hu.ait.demos.weatherinfo.R;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Item extends RealmObject {
    @PrimaryKey
    private String itemID;

    private String city;


    public Item() {

    }

    public Item(String city) {
        this.city = city;

    }

    public String getCity() {
        return city;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setCity(String city) {
        this.city = city;
    }

}