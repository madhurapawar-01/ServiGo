package com.example.servigo;

public class Provider {

    String name;
    String skill;
    String price;
    String rating;
    String distance;
    String experience;
    String userId;

    public Provider() {}

    public Provider(String name, String skill, String price,
                    String rating, String distance,
                    String experience, String userId) {
        this.name = name;
        this.skill = skill;
        this.price = price;
        this.rating = rating;
        this.distance = distance;
        this.experience = experience;
        this.userId = userId;
    }

    public String getName() { return name; }
    public String getSkill() { return skill; }
    public String getPrice() { return price; }
    public String getRating() { return rating; }
    public String getDistance() { return distance; }
    public String getExperience() { return experience; }
    public String getUserId() { return userId; }
}