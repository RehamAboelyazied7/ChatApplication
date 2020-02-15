package com.iti.chat.viewcontroller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FileTransferProgressController {
    @FXML
    JFXSpinner spinner;
    @FXML
    Text progressText;
    @FXML
    VBox rootVBox;
    JFXDialog dialog;

    private long totalBytes;

    public void setTotalBytes(long bytes) {

        totalBytes = bytes;
    }

    public void setCurrentBytes(long currentBytes) {
        Platform.runLater(() -> {
            if(currentBytes == totalBytes) {
                dialog.close();
            }
            else {
                double currentMB = currentBytes / (1024.0 * 1024.0);
                double totalMB = totalBytes / (1024.0 * 1024.0);
                progressText.setText(String.format("%.2f MB out of %.2f MB", currentMB, totalMB));
            }
        });
    }

    public void willStartTransfer(StackPane container) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(rootVBox);
        dialog = new JFXDialog(container, layout, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }
}
