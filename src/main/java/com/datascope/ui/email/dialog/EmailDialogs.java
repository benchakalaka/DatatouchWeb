package com.datascope.ui.email.dialog;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.TextField;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.ButtonType;
import de.steinwedel.messagebox.MessageBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailDialogs {

    @Value(value = "${email.delete.email}")
    private String deleteEmailLabel;

    @Value(value = "${email.delete.are.you.sure}")
    private String areYouSureLabel;

    @Value(value = "${email.delete.group}")
    private String deleteGroupLabel;

    @Value(value = "${email.edit.group.name}")
    private String editGroupNameLabel;

    public EmailDialogs() {
    }

    public void confirmDeleteEmail(Runnable okListener) {
        showDialog(deleteEmailLabel, areYouSureLabel, okListener);
    }

    public void confirmDeleteEmailGroup(Runnable okListener) {
        showDialog(deleteGroupLabel, areYouSureLabel, okListener);
    }

    private void showDialog(String title, String message, Runnable okListener) {
        MessageBox.createQuestion()
                .withCaption(title)
                .withMessage(message)
                .withYesButton(okListener)
                .withNoButton().open();
    }

    public void editEmailGroup(TextField input, Runnable okListener) {

        MessageBox messageBox = MessageBox.createQuestion()
                .withCaption(editGroupNameLabel)
                .withMessage(input)
                .withOkButton(okListener)
                .withCancelButton();

        input.addValueChangeListener((e) -> messageBox.getButton(ButtonType.OK).setEnabled(!e.getValue().isEmpty()));
        messageBox.open();
    }

    public void selectEmailsInGroup(com.vaadin.ui.Component components, String groupName) {
        MessageBox.create()
                .withCaption(groupName)
                .withMessage(components)
                .withCloseButton(ButtonOption.icon(VaadinIcons.CLOSE))
                .withWidth("50%")
                .withHeight("80%")
                .open();
    }
}
