package com.iti.chat.model;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private int id;
    private String email;
    private String phone;
    private List<User> friends;
    private int status;

    public User (String firstName, String lastName, String phone, String email, int status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public User() {

    }

    public String getStatus() {
        String state = "";
        switch (status) {
            case UserStatus.AWAY:
                state = "Away";
                break;
            case UserStatus.BUSY:
                state = "Busy";
                break;
            case UserStatus.ONLINE:
                state = "Online";
                break;
            default:
                state = "Offline";
        }
        return state;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public List<User> getFriends() {

        return friends;
    }

    public void setFriends(List<User> friends) {

        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
