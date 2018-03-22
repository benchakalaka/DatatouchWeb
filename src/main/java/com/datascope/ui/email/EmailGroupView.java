package com.datascope.ui.email;

import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.ui.email.callbacks.email.OnDeleteEmailCallback;
import com.datascope.ui.email.callbacks.email.OnEmailTemplateEditedCallback;
import com.datascope.ui.email.callbacks.email.OnEmailTemplateSelectedInGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnDeleteEmailGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnEditEmailGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnEmailGroupSelectedCallback;
import com.datascope.ui.email.controller.EmailGroupViewController;
import com.datascope.ui.email.dialog.EmailDialogs;
import com.datascope.ui.email.elements.EmailGridItem;
import com.datascope.ui.email.elements.EmailGroupGridItem;
import com.datascope.ui.generated.EmailGroupDeisgn;
import com.datascope.ui.utils.notifications.Messages;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@UIScope
@NavigatorViewName(EmailGroupView.NAME)
@MenuCaption("Emails")
@MenuIcon(VaadinIcons.SITEMAP)
@SpringView(name = EmailGroupView.NAME)
public class EmailGroupView extends EmailGroupDeisgn implements View,
        OnDeleteEmailCallback,
        OnEditEmailGroupCallback,
        OnEmailGroupSelectedCallback,
        OnDeleteEmailGroupCallback,
        OnEmailTemplateSelectedInGroupCallback,
        OnEmailTemplateEditedCallback {

    static final String NAME = "EmailGroupView";
    private IEmailService service;
    private Messages messages;
    private EmailGroupViewController controller;
    private EmailDialogs dialogs;

    public EmailGroupView(IEmailService service, Messages messages, EmailGroupViewController controller, EmailDialogs dialogs) {
        this.service = service;
        this.messages = messages;
        this.controller = controller;
        this.dialogs = dialogs;
    }

    @PostConstruct
    public void init() {
        controller.initEmailGroupGrid(getEmailGroupsGrid(), this);
        controller.initEmailGrid(getEmailsGrid(), this);
        service.getEmailGroups(this::onEmailGroupsLoaded);
        service.getEmailTemplates(this::onEmailsLoaded);
    }

    private void onEmailsLoaded(EmailTemplate.List emailTemplates) {
        controller.setEmails(getEmailsGrid(), controller.toEmailGridItems(emailTemplates));
        controller.selectFirstEmailGroup(getEmailGroupsGrid());
    }

    @Override
    public void onDeleteEmailGroupClicked(EmailGroupGridItem item) {
        dialogs.confirmDeleteEmailGroup(() -> {
            service.deleteEmailGroup(item.getId());
            controller.removeEmailGroup(item);
            messages.tray("email.group.deleted");
        });
    }

    @Override
    public void emailGroupSelected(EmailGroupGridItem item) {
        controller.setEmails(getEmailsGrid(), item.getEmailGridItems());
    }

    @Override
    public void onEditEmailGroupClicked(EmailGroupGridItem item) {
        service.editGroupName(item.getName(), item.getId());
    }

    @Override
    public void onDeleteEmailClicked(EmailGridItem item) {
        dialogs.confirmDeleteEmail(() -> {
            service.deleteEmailTemplate(item.getId());
            controller.removeEmailFromGroup(item);
            messages.tray("email.deleted.from.group");
        });
    }


    private void onEmailGroupsLoaded(EmailGroup.List groups) {
        controller.showEmailGroupsInGrid(getEmailGroupsGrid(), groups);
    }

    private int getSelectedGroupId() {
        return getEmailGroupsGrid().getSelectedItems().iterator().next().getId();
    }

    @Override
    public void onEmailTemplateClicked(int emailTemplateId, boolean isSelected) {
        if (isSelected)
            service.addEmailToGroup(emailTemplateId, getSelectedGroupId());
        else
            service.removeEmailFromGroup(emailTemplateId, getSelectedGroupId());
    }

    @Override
    public void onEditEmailTemplate(EmailGridItem email) {
        service.editEmailTemplate(email.getId(), email.getEmail(), email.getName(), email.getLastName());
    }
}
