package com.iti.chat.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Set;

public final class WebViewFitContent extends Region {

    final WebView webview = new WebView();
    final WebEngine webEngine = webview.getEngine();

    public WebViewFitContent(String content) {
        webview.setPrefHeight(5);

        widthProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            Double width = (Double)newValue;
            webview.setPrefWidth(width);
            adjustHeight();
        });

        webview.getEngine().getLoadWorker().stateProperty().addListener((arg0, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                adjustHeight();
            }
        });

        webview.getChildrenUnmodifiable().addListener((ListChangeListener<Node>) change -> {
            Set<Node> scrolls = webview.lookupAll(".scroll-bar");
            for (Node scroll : scrolls) {
                scroll.setVisible(false);
            }
        });

        setContent(content);
        getChildren().add(webview);
    }

    public void setContent(final String content) {
        Platform.runLater(() -> {
            webEngine.loadContent(getHtml(content));
            Platform.runLater(() -> adjustHeight());
        });
    }


    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(webview,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    private void adjustHeight() {
        Platform.runLater(() -> {
            String heightString = (String) webview.getEngine().executeScript(
                    "window.getComputedStyle(document.body, null).getPropertyValue('height')");
            heightString = heightString.replaceFirst("px", "");
            double height = Double.valueOf(heightString);
            webview.setPrefHeight(height - 50);
        });
    }

    private String getHtml(String content) {
        return "<html><body>" +
                "<div id=\"mydiv\">" + content + "</div>" +
                "</body></html>";
    }

}
