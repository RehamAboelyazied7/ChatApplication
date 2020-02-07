package com.iti.chat.model;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private int id;
    private String email;
    private String phone;
    private int status;
    private int gender;
    private List<User> friends;
    private List<ChatRoom> chatRooms;

    public User (String firstName, String lastName, String phone, String email, int status, int gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.gender = gender;
    }

    public User() {
        gender = Gender.MALE;
        status = UserStatus.ONLINE;
    }

    public int getGender() {

        return gender;
    }

    public void setGender(int gender) {

        this.gender = gender;
    }


    public String getStatusMessage() {
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

    public int getStatus() {
        return status;
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

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof User) {
            User user = (User) object;
            return id == user.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
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
