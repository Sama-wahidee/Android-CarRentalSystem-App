package edu.birzeit.www;

public class Car {

    private String name;

    private String model;

    private int imageUrl;
    private int seatCount;
    private int speed;
    private int pricePerDay;


    // Constructor with all parameters
    public Car(String name, int imageUrl, int seatCount, int speed, int pricePerDay,String model) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.seatCount = seatCount;
        this.speed = speed;
        this.pricePerDay = pricePerDay;
        this.model=model;
    }

    // Getters and setters for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int  getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
