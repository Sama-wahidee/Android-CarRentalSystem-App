package edu.birzeit.www;

public class Car {
    private int id;

    private String description;
    private String vinNumber;
    private String fuelType;
    private String transmission;
    private int numberOfSeats;
    private int rentPrice;
    private String color;
    private String model;
    private int topSpeed;
    private String imageUrl;
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Constructor
    public Car( String description, String vinNumber, String fuelType, String transmission,
               int numberOfSeats, int rentPrice, String color, String model, int topSpeed, String imageUrl, int year) {

        this.description = description;
        this.vinNumber = vinNumber;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.numberOfSeats = numberOfSeats;
        this.rentPrice = rentPrice;
        this.color = color;
        this.model = model;
        this.topSpeed = topSpeed;
        this.imageUrl = imageUrl;
        this.year=year;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }



    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVinNumber() { return vinNumber; }
    public void setVinNumber(String vinNumber) { this.vinNumber = vinNumber; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public double getRentPrice() { return rentPrice; }
    public void setRentPrice(int rentPrice) { this.rentPrice = rentPrice; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getTopSpeed() { return topSpeed; }
    public void setTopSpeed(int topSpeed) { this.topSpeed = topSpeed; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}