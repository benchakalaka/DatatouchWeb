package com.datascope.ui.email;

import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailTemplatesCallback;
import com.datascope.ui.email.callbacks.OnDeleteEmailCallback;
import com.datascope.ui.email.callbacks.OnDeleteEmailGroupCallback;
import com.datascope.ui.email.callbacks.OnEditEmailGroupCallback;
import com.datascope.ui.email.callbacks.OnEmailGroupSelectedCallback;
import com.datascope.ui.email.dialog.EmailDialogs;
import com.datascope.ui.email.elements.EmailGridItem;
import com.datascope.ui.email.elements.EmailGroupGridItem;
import com.datascope.ui.email.helpers.EmailGroupUiHelper;
import com.datascope.ui.generated.EmailGroupDeisgn;
import com.datascope.ui.utils.notifications.DatatouchNotification;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import javax.annotation.PostConstruct;

@UIScope
@NavigatorViewName(EmailGroupView.NAME)
@MenuCaption("Emails")
@MenuIcon(VaadinIcons.HOME)
@SpringView(name = EmailGroupView.NAME)
public class EmailGroupView extends EmailGroupDeisgn implements View,
        GetEmailGroupsCallback,
        OnDeleteEmailCallback,
        OnEditEmailGroupCallback,
        OnEmailGroupSelectedCallback,
        OnDeleteEmailGroupCallback,
        GetEmailTemplatesCallback {

    static final String NAME = "EmailGroupView";
    private IEmailService service;
    private DatatouchNotification notification;
    private EmailGroupUiHelper helper;
    private EmailDialogs dialogs;

    public EmailGroupView(
            IEmailService service,
            DatatouchNotification notification,
            EmailGroupUiHelper helper,
            EmailDialogs dialogs) {
        this.service = service;
        this.notification = notification;
        this.helper = helper;
        this.dialogs = dialogs;
    }

    @PostConstruct
    public void init() {
        helper.initEmailGroupGrid(getEmailGroupsGrid(), this);
        helper.initEmailGrid(getEmailsGrid(), this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        service.getEmailGroups(this);
    }

    @Override
    public Component getViewComponent() {
        return this;
    }

    @Override
    public void onDeleteEmailGroupClicked(EmailGroupGridItem item) {
        dialogs.confirmDeleteEmailGroup(() -> {
            service.deleteEmailGroup(item.getId());
            helper.removeEmailGroup(item);
            notification.tray("email.group.deleted");
        });
    }

    @Override
    public void emailGroupSelected(EmailGroupGridItem item) {
        helper.setEmails(getEmailsGrid(), item.getEmailGridItems());
    }

    @Override
    public void onEditEmailGroupClicked(EmailGroupGridItem item) {
        TextField input = helper.createEditEmailGroupTextField();
        input.setValue(item.getName());
        dialogs.editEmailGroup(input, () -> {
            service.editGroupName(input.getValue(), item.getId());
            item.setName(input.getValue());
            helper.editEmailGroupItem(item);
        });
    }

    @Override
    public void onDeleteEmailClicked(EmailGridItem item) {
        dialogs.confirmDeleteEmail(() -> {
            service.deleteEmail(getSelectedGroupId(), item.getId());
            helper.removeEmailFromGroup(item);
            notification.tray("email.deleted.from.group");
        });
    }

    @Override
    public void onEmailGroupsLoaded(EmailGroup.List groups) {
        helper.setEmailGroups(getEmailGroupsGrid(), groups);
    }

    @Override
    public void emailGroupsNotFound() {
        notification.warn("email.groups.not.found");
    }

    private int getSelectedGroupId() {
        return getEmailGroupsGrid().getSelectedItems().iterator().next().getId();
    }

    @Override
    public void onEmailTemplatesFound(EmailTemplate.List templates) {

    }

    @Override
    public void onEmailTemplatesNotFound() {

    }
}