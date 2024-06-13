package com.example.platemate;

public class Post {
    private String place_name;
    private String category_name;
    private String phone;
    private String road_address_name;
    private String place_url;

    // Constructor
    public Post(String place_name, String category_name, String phone, String road_address_name, String place_url) {
        this.place_name = place_name;
        this.category_name = category_name;
        this.phone = phone;
        this.road_address_name = road_address_name;
        this.place_url = place_url;
    }

    // Getters and Setters
    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoad_address_name() {
        return road_address_name;
    }

    public void setRoad_address_name(String road_address_name) {
        this.road_address_name = road_address_name;
    }

    public String getPlace_url() {
        return place_url;
    }

    public void setPlace_url(String place_url) {
        this.place_url = place_url;
    }

    // A method to format the display of the post information
    public String getFormattedInfo() {
        return place_name + "\n" + category_name + "\n" + phone + "\n" + road_address_name + "\n" + place_url;
    }
}
