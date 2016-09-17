package hackthenorth.neighborcater.models;

/**
 * Created by rowandempster on 9/17/16.
 */

public class Kitchen {

    private double latitude;
    private double longtitude;
    private String ownerName;
    private String foodName;
    private String kitchenName;
    private String description;
    private double price;

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setFoodName(String foodName){
        this.foodName = foodName;
    }
}
