package com.iti.chat.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterValidation {


    String password = "";
    public static final int INVALID = -1, VALID = 1, EMPTY = 0;

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
            if (!email.matches("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
                return INVALID;//Enter a valid email address
        }
        return VALID;//match
    }

    public int checkName(String name) {
        if (name.isEmpty()) {
            return 0;//empty name
        } else if (!name.matches("([a-zA-Z]*)")) {
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

    public boolean checkBirthDate(String birthdate) {
        boolean isValid = true;
        if (!birthdate.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$"))
            isValid = false;
        return isValid;
    }


}
