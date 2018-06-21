package com.datascope.ui.usermanagement.dialog;

import com.datascope.ui.usermanagement.callbacks.IChangeUserPinCallback;
import com.datascope.ui.utils.notifications.Notifications;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import de.steinwedel.messagebox.MessageBox;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChangeUserPinDialog extends Window {

    private static final float PIN_BUTTON_SIZE = 60;
    private static final int PIN_INDICATOR_COUNT = 6;

    @Value("${usersettings.changepin.dialog.title}")
    private String textDialogTitle;

    @Value("${usersettings.changepin.dialog.buttoncleardigit}")
    private String textButonClearDigit;

    @Value("${usersettings.changepin.dialog.buttonok}")
    private String textButonOk;

    @Value("${usersettings.changepin.dialog.buttonunassign}")
    private String textButonUnassign;

    @Value("${usersettings.changepin.dialog.unassignconfirm}")
    private String textUnassignConfirm;

    private Notifications notifications;

    private String pinCodeValue;

    private HorizontalLayout pinIndicatorLayout;

    private int userId;
    private IChangeUserPinCallback callback = new IChangeUserPinCallback() {
        @Override
        public void onUserPinUpdate(int userId, String newPin) {
        }

        @Override
        public void onUserPinUnassign(int userId) {
        }
    };

    public ChangeUserPinDialog(Notifications notifications) {
        super();
        pinCodeValue = "";
        this.notifications = notifications;
        center();
    }

    @Override
    public void attach() {
        super.attach();
        setCaption(textDialogTitle);

        setClosable(true);
        setModal(true);
        setResizable(false);
        reset();

        setContent(buildPinBoard());
    }

    @Override
    public void close() {
        reset();
        super.close();
    }

    private void reset() {
        pinCodeValue = "";
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setChangeUserPinCallback(IChangeUserPinCallback callback) {
        if (callback != null) {
            this.callback = callback;
        }
    }

    private GridLayout buildPinBoard() {
        GridLayout gridLayout = new GridLayout(3, 6);
        gridLayout.setSpacing(true);
        gridLayout.setMargin(true);
        gridLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        pinIndicatorLayout = buildPinIndicator();
        gridLayout.addComponent(pinIndicatorLayout, 0, 0, 2, 0);

        for (int i = 1; i <= 9; i++) {
            gridLayout.addComponent(buildDigitButton(i));
        }

        gridLayout.addComponent(buildDigitClearButton());

        gridLayout.addComponent(buildDigitButton(0));

        gridLayout.addComponent(buildOkButton());


        gridLayout.addComponent(buildUnassignPinButton(), 0, 5, 2, 5);

        return gridLayout;
    }

    private HorizontalLayout buildPinIndicator() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setMargin(false);
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth(100, Unit.PERCENTAGE);
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);


        for (int i = 0; i < PIN_INDICATOR_COUNT; i++) {
            Label label = new Label();
            label.setIcon(VaadinIcons.CIRCLE_THIN);
            horizontalLayout.addComponent(label);
        }

        return horizontalLayout;
    }

    private Button buildDigitButton(int digit) {
        String digitValue = String.valueOf(digit);

        Button button = new Button(digitValue);
        button.setId(digitValue);
        button.setWidth(PIN_BUTTON_SIZE, Unit.PIXELS);
        button.setHeight(PIN_BUTTON_SIZE, Unit.PIXELS);

        button.addClickListener(event -> addDigit(event.getButton().getId()));

        return button;
    }

    private Button buildDigitClearButton() {
        Button button = new Button(textButonClearDigit);
        button.setWidth(PIN_BUTTON_SIZE, Unit.PIXELS);
        button.setHeight(PIN_BUTTON_SIZE, Unit.PIXELS);
        button.addClickListener(event -> clearDigit());
        return button;
    }

    private Button buildOkButton() {
        Button button = new Button(textButonOk);
        button.setWidth(PIN_BUTTON_SIZE, Unit.PIXELS);
        button.setHeight(PIN_BUTTON_SIZE, Unit.PIXELS);
        button.addClickListener(event -> onButtonOkClick());
        return button;
    }

    private Button buildUnassignPinButton() {
        Button button = new Button(textButonUnassign);
        button.setWidth(100, Unit.PERCENTAGE);
        button.addClickListener(event -> onButtonUnassignClick());
        return button;
    }

    private void addDigit(String digit) {
        pinCodeValue += digit;
        updatePinIndicator();
    }

    private void clearDigit() {
        pinCodeValue = StringUtils.chop(pinCodeValue);
        updatePinIndicator();
    }

    private void updatePinIndicator() {
        int blockCount = pinCodeValue.length() / PIN_INDICATOR_COUNT;
        int digitCount = blockCount * PIN_INDICATOR_COUNT;
        int hilightCount = pinCodeValue.length() - digitCount;

        if (pinCodeValue.length() == digitCount && digitCount > 0) {
            hilightCount = PIN_INDICATOR_COUNT;
        }

        for (int i = 0; i < hilightCount; i++) {
            com.vaadin.ui.Component component = pinIndicatorLayout.getComponent(i);
            component.setIcon(VaadinIcons.CIRCLE);
        }

        for (int i = hilightCount; i < PIN_INDICATOR_COUNT; i++) {
            com.vaadin.ui.Component component = pinIndicatorLayout.getComponent(i);
            component.setIcon(VaadinIcons.CIRCLE_THIN);
        }
    }

    private void onButtonOkClick() {
        if (pinCodeValue.isEmpty()) {
            notifications.error("usermanagement.changepin.emptypin");
            return;
        }
        callback.onUserPinUpdate(userId, pinCodeValue);
        close();
    }

    private void onButtonUnassignClick() {
        MessageBox.createQuestion()
                .withCaption(textButonUnassign)
                .withMessage(textUnassignConfirm)
                .withYesButton(() -> callback.onUserPinUnassign(userId))
                .withNoButton().open();
    }

}
