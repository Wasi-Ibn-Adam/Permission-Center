package com.wasitech.permissioncenter.java.com.wasitech.register.classes;

public class UserProfile {
    private String fName, lName, mName;  // NAME
    private int d, m, y;                  // DOB
    private int street, house;
    private String town, city, state, country;    // ADDRESS
    private int gender;     // GENDER
    private int interestedIn;   // INTERESTED
    private int religion;   // RELIGION
    private int accountType;  // ACCOUNT TYPE

    public void setfName(String fName) {
        this.fName = fName;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }
    public void setD(int d) {
        this.d = d;
    }
    public void setM(int m) {
        this.m = m;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setStreet(int street) {
        this.street = street;
    }
    public void setHouse(int house) {
        this.house = house;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public void setInterestedIn(int interestedIn) {
        this.interestedIn = interestedIn;
    }

    public int getReligion() {
        return religion;
    }
    public void setReligion(int religion) {
        this.religion = religion;
    }
    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }
    public String getmName() {
        return mName;
    }
    public int getD() {
        return d;
    }
    public int getM() {
        return m;
    }
    public int getY() {
        return y;
    }
    public int getStreet() {
        return street;
    }
    public int getHouse() {
        return house;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
    public int getGender() {
        return gender;
    }
    public int getInterestedIn() {
        return interestedIn;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
