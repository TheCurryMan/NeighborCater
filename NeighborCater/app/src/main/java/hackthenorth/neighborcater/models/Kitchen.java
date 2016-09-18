package hackthenorth.neighborcater.models;

import java.io.Serializable;

/**
 * Created by rowandempster on 9/17/16.
 */

public class Kitchen implements Serializable {

    private String address;
    private String foodDescription;
    private String foodName;
    private String kitchenName;
    private double latitude;
    private double longitude;
    private String price;
    private String user;



    public Kitchen(){

    }

    public Kitchen(String address, String foodDescription, String foodName, String kitchenName, double latitude, double longitude, String price, String user){
        this.address = address;
        this.foodDescription = foodDescription;
        this.foodName = foodName;
        this.kitchenName = kitchenName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
