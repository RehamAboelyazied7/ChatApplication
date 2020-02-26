package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import com.iti.chat.util.CustomListCell;
import javafx.scene.input.MouseEvent;

public class GroupContactList extends CustomListCell {
    private GroupChatController groupChatController;

    public GroupContactList(GroupChatController groupChatController) {
        super();
        this.groupChatController = groupChatController;
    }

    @Override
    public void setCellAction(MouseEvent mouseEvent, User item) {
        groupChatController.contactListAction(mouseEvent,item);
    }
}
