package edu.birzeit.www;

public class order {
    private int orderId;
    private long vinNumber;
    private double rentCost;
    private String userName;
    private String email;
    private long phone;
    private String startDate;
    private String endDate;
    private String carName;

    public order(int orderId, long vinNumber, double rentCost, String userName, String email, long phone, String startDate, String endDate, String carName) {
        this.orderId = orderId;
        this.vinNumber = vinNumber;
        this.rentCost = rentCost;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.carName = carName;
    }

    public int getOrderId() {
        return orderId;
    }

    public long getVinNumber() {
        return vinNumber;
    }

    public double getRentCost() {
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

    public String getCarName() {
        return carName;
    }
}
