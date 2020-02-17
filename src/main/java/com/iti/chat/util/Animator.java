package com.iti.chat.util;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

public class Animator {

    /**
     * This method takes a node and set several handlers to it to make it more live and interactive
     * with the user and also makes it looks selectable
     */
    public static void setIconAnimation(Node node) {

        node.setOnMousePressed(mouseEvent -> {

            node.setOpacity(1.0);
            node.setEffect(new GaussianBlur());

        });

        node.setOnMouseReleased(mouseEvent -> {


            node.setEffect(null);

        });

        node.setOnMouseEntered(mouseEvent -> {

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.4);
            fadeTransition.setCycleCount(1);
            fadeTransition.setNode(node);
            fadeTransition.setDuration(Duration.millis(100));
            fadeTransition.play();
            node.setOpacity(0.4);


        });

        node.setOnMouseExited(mouseEvent -> {

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setFromValue(0.4);
            fadeTransition.setToValue(1.0);
            fadeTransition.setCycleCount(1);
            fadeTransition.setNode(node);
            fadeTransition.setDuration(Duration.millis(300));
            fadeTransition.play();
            node.setOpacity(1.0);

        });

    }


    public static void suspendIconAnimation(Node node) {

        node.setOnMouseExited(mouseEvent -> {

        });

        node.setOnMouseEntered(mouseEvent -> {

        });

        node.setOnMouseReleased(mouseEvent -> {

        });

        node.setOnMousePressed(mouseEvent -> {

        });


    }

}
