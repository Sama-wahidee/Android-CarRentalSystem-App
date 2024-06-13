package edu.birzeit.www;

public class Orders { //shaheen
    private int orderId;
    private long vinNumber;
    private String rentCost;
    private String userName;
    private String email;
    private long phone;
    private String startDate;
    private String endDate;
    private String imageUrl;

    // Constructor, getters, and setters

    public Orders(int orderId, long vinNumber, String rentCost, String userName, String email, long phone, String startDate, String endDate, String imageUrl) {
        this.orderId = orderId;
        this.vinNumber = vinNumber;
        this.rentCost = rentCost;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
    }

    public int getOrderId() {
        return orderId;
    }

    public long getVinNumber() {
        return vinNumber;
    }

    public String getRentCost() {
        return rentCost;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
