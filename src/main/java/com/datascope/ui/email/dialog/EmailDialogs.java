package com.datascope.ui.email.dialog;

import com.vaadin.ui.TextField;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.ButtonType;
import de.steinwedel.messagebox.MessageBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class EmailDialogs {

    @Value("${email.delete.email.template}")
    private String deleteEmailLabel;

    @Value("${email.delete.are.you.sure}")
    private String areYouSureLabel;

    @Value("${email.delete.email.template.warning}")
    private String willDeleteEmailTemplateCompletely;

    @Value("${email.delete.group}")
    private String deleteGroupLabel;

    @Value("${email.edit.group.name}")
    private String editGroupNameLabel;

    @Value("${email.edit.group.create.new}")
    private String createNew;

    public EmailDialogs() {
    }

    public void confirmDeleteEmail(Runnable okListener) {
        showDialog(deleteEmailLabel, willDeleteEmailTemplateCompletely, okListener);
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

    private void showDialogWithInput(String title, Consumer<String> okListener) {
        TextField text = new TextField();

        MessageBox messageBox = MessageBox.create()
                .withCaption(title)
                .withMessage(text)
                .withOkButton(() -> okListener.accept(text.getValue())
                        ,ButtonOption.disable())
                .withCancelButton();

        text.addValueChangeListener((valueEvent) ->
                messageBox.getButton(ButtonType.OK)
                        .setEnabled(!valueEvent.getValue().isEmpty()));

        messageBox.open();
    }

    public void createNewGroup(Consumer<String> okListener) {
        showDialogWithInput(createNew, okListener);
    }

    public void createNewTemplate(Consumer<String> okListener) {
        showDialogWithInput(createNew, okListener);
    }
}
