package com.example.pawan_pc.cancerdiaganosis;

/**
 * Created by Pawan-PC on 13-10-2017.
 */
public class Datamodel {
    String contact;
    String distance;
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public Datamodel(String contact, String distance, String name,String adres) {
        this.contact = contact;
        this.distance = distance;
        this.name = name;
        this.address=adres;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}
