package com.datascope.application.ui.email;

import com.datascope.application.ui.email.callbacks.OnDeleteEmailCallback;
import com.datascope.application.ui.email.callbacks.OnDeleteEmailGroupCallback;
import com.datascope.application.ui.email.callbacks.OnEditEmailGroupCallback;
import com.datascope.application.ui.email.callbacks.OnEmailGroupSelectedCallback;
import com.datascope.application.ui.email.dialog.EmailDialogs;
import com.datascope.application.ui.email.elements.EmailGridItem;
import com.datascope.application.ui.email.elements.EmailGroupGridItem;
import com.datascope.application.ui.email.helpers.EmailGroupUiHelper;
import com.datascope.application.ui.generated.EmailGroupDeisgn;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailTemplatesCallback;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = EmailGroupView.NAME)
public class EmailGroupView extends EmailGroupDeisgn implements View,
        GetEmailGroupsCallback,
        OnDeleteEmailCallback,
        OnEditEmailGroupCallback,
        OnEmailGroupSelectedCallback,
        OnDeleteEmailGroupCallback, GetEmailTemplatesCallback {

    public static final String NAME = "EmailGroupView";
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
        service.getEmailTemplates(this);
        service.getEmailGroups(this);
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
            String newName = input.getValue();
            //TODO: check for empty string
            service.editGroupName(newName, item.getId());
            item.setName(newName);
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
