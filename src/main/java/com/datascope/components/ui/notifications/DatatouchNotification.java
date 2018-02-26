package com.datascope.components.ui.notifications;

import com.vaadin.ui.Notification;
import org.springframework.stereotype.Component;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;
import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static com.vaadin.ui.Notification.Type.WARNING_MESSAGE;

@Component
public class DatatouchNotification {

    private Messages messages;

    public DatatouchNotification(Messages messages) {
        this.messages = messages;
    }

    public void warn(String message) {
        Notification.show(get(message), WARNING_MESSAGE);
    }

    public void error(String message) {
        Notification.show(get(message), ERROR_MESSAGE);
    }

    public void success(String message) {
        Notification.show(get(message));
    }

    public void tray(String message) {
        Notification.show(get(message), TRAY_NOTIFICATION);
    }

    private String get(String message){
        try {
            return messages.get(message);
        } catch (Exception ex){
            return message;
        }
    }


}
