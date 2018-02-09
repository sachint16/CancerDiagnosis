package com.example.pawan_pc.cancerdiaganosis;

/**
 * Created by Pawan-PC on 09-10-2017.
 */
public class userdata {
    String name;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    String pass;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBP() {
        return BP;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSmoker() {
        return smoker;
    }

    public void setSmoker(String smoker) {
        this.smoker = smoker;
    }

    public String getCancertest() {
        return cancertest;
    }

    public void setCancertest(String cancertest) {
        this.cancertest = cancertest;
    }

    String email;
    String dob;
    String BP;
    String gender;
    String smoker;
    String cancertest;
}
