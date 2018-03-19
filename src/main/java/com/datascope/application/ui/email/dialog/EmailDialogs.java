package com.datascope.application.ui.email.dialog;

import com.datascope.application.ui.utils.helper.Labels;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import de.steinwedel.messagebox.MessageBox;
import org.springframework.stereotype.Component;

@Component
public class EmailDialogs {

    private Labels labels;

    public EmailDialogs(Labels labels) {
        this.labels = labels;
    }

    public void confirmDeleteEmail(Runnable okListener) {
        showDialog("email.delete.email", "email.delete.are.you.sure", okListener);
    }

    public void confirmDeleteEmailGroup(Runnable okListener) {
        showDialog("email.delete.group", "email.delete.are.you.sure", okListener);
    }

    private void showDialog(String title, String message, Runnable okListener) {
        MessageBox.createQuestion()
                .withCaption(labels.get(title))
                .withMessage(labels.get(message))
                .withYesButton(okListener)
                .withNoButton().open();
    }

    public void editEmailGroup(TextField input, Runnable okListener) {
        MessageBox.createQuestion()
                .withCaption("Text input")
                .withMessage(input)
                .withOkButton(okListener)
                .withCancelButton()
                .open();
    }
}
