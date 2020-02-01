package com.iti.chat.util.image;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class ImageTint {
    public static void setWhiteTint(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        colorAdjust.setHue(1.0);
        colorAdjust.setSaturation(1.0);
        imageView.setEffect(colorAdjust);
    }

    public static void removeTint(ImageView imageView) {
        imageView.setEffect(null);
    }
}
