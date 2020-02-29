package com.iti.chat.util;

import com.iti.chat.model.Gender;
import com.iti.chat.model.User;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static ImageCache instance;
    Map<User, Image> images;
    private  ImageCache() {
        images = new HashMap<>();
    }
    public static ImageCache getInstance() {
        if(instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public void setImage(User user, Image image) {
        images.put(user, image);
    }
    public Image getImage(User user) {
        return images.get(user);
    }
    public Image getDefaultImage(User user) {
        Image image;
        if(user == null) {
            image = new Image(getClass().getResourceAsStream("/view/icons/userIcon.png"));
        }
        else if(user.getGender() == Gender.MALE) {
            image = new Image(getClass().getResourceAsStream("/view/icons/Male.png"));
        }
        else {
            image = new Image(getClass().getResourceAsStream("/view/icons/Female.png"));
        }
        return image;
    }

}
