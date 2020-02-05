package com.iti.chat.validator;

import javafx.scene.layout.VBox;

import java.util.regex.*;

public class RegisterValidation {

    String password = "";
    static final int INVALID = -1, VALID = 1, EMPTY = 0;

    public int checkPhone(String phone) {
        if (phone.isEmpty()) {
            return EMPTY; //enter your name
        } else {
            if (phone.startsWith("+2"))
                phone = phone.replace("+2", "");
            else if (phone.startsWith("002"))
                phone = phone.replace("002", "");
            Pattern p = Pattern.compile("^(01)[0-9]{9}");
            Matcher m = p.matcher(phone);
            if (!m.matches())
                return INVALID;//not match
        }
        return VALID;//match
    }

    public int checkEmail(String email) {
        if (email.isEmpty()) {
            return EMPTY; //enter your Email
        } else {

            Pattern p = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$");
            Matcher m = p.matcher(email);
            if (!m.matches())
                return INVALID;//Enter a valid email address
        }
        return VALID;//match
    }

    public int checkName(String name) {
        if (name.isEmpty()) {
            return 0;//empty name
        } else if (!name.matches("([A-Z][a-zA-Z]*)")) {
            return -1;
        } else {
            return 1;
        }
    }

    public int checkPass(String pass) {

        if (pass.isEmpty()) {
            return EMPTY;//enter your pass
        } else if (!pass.matches("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,30})")) {
            return INVALID; //check pass more than 8 and contains characters,digits
        }
        return VALID;

    }

    public int checkConfirmPass(String confirmPass) {
        if (confirmPass.isEmpty()) {
            return EMPTY;//enter your ConfirmPass
        } else if (!confirmPass.equals(password)) {
            return INVALID; //pass and confirm not the same
        }
        return VALID;//equals
    }

    public boolean chooseImage(String filePath) {
        if (filePath != null) {
            return true;//already choose
        }
        return false;//not choose image and textField Empty
    }

    public boolean writeBio(String bio) {
        if (bio.isEmpty())
            return false;//write about your Bio
        else
            return true;
    }


}
