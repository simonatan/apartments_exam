package com.company;

public class Apartment {
    private String city;
    private int numberOfRooms;
    private int area;
    private int price;
    private String phoneNumber;

    public Apartment() {
    }

    public Apartment(String city, int numberOfRooms, int area, int price, String phoneNumber) {
        this.city = city;
        this.numberOfRooms = numberOfRooms;
        this.area = area;
        this.price = price;
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
