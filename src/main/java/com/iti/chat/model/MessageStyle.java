package com.iti.chat.model;

import com.iti.chat.util.ColorUtils;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "MessageStyleType" , propOrder = {
        "fontFamily",
        "size",
        "color",
        "fontWeight",
        "fontPosture"
})
public class MessageStyle implements Serializable {

    @XmlElement(name = "FontFamily")
    String fontFamily;

    @XmlElement(name = "Size")
    int size;

    @XmlElement(name = "Color")
    String color;

    @XmlElement(name = "FontWeight")
    String fontWeight;

    @XmlElement(name = "FontPosture")
    String fontPosture;

    {
        size = 15;
        color = ColorUtils.toRGB(Color.BLACK);
        fontFamily = "Arial";
        fontWeight = FontWeight.LIGHT.name();
        fontPosture = FontPosture.REGULAR.name();
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getFontPosture() {
        return fontPosture;
    }

    public void setFontPosture(String fontPosture) {
        this.fontPosture = fontPosture;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return "-fx-font-family: \"" + fontFamily + "\"; " + " -fx-text-fill: " + color + ";" + " -fx-font-size: "
                + size + ";" + " -fx-font-weight:" + fontWeight + ";" + " -fx-font-style:" + fontPosture;
    }
}
