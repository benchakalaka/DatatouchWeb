package com.datascope.ui.utils.notifications;

import com.vaadin.ui.Notification;
import org.springframework.stereotype.Component;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;
import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static com.vaadin.ui.Notification.Type.WARNING_MESSAGE;

@Component
public class Messages {

    private NotificationMessages notificationMessages;

    public Messages(NotificationMessages notificationMessages) {
        this.notificationMessages = notificationMessages;
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
            return notificationMessages.get(message);
        } catch (Exception ex){
            return message;
        }
    }


}
