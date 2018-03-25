package com.datascope.ui.email;

import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.ui.email.callbacks.email.OnDeleteEmailCallback;
import com.datascope.ui.email.callbacks.email.OnEmailTemplateEditedCallback;
import com.datascope.ui.email.callbacks.email.OnEmailTemplateSelectedInGroupCallback;
import com.datascope.ui.email.controller.EmailGroupViewController;
import com.datascope.ui.email.dialog.EmailDialogs;
import com.datascope.ui.email.elements.EmailGridItem;
import com.datascope.ui.email.elements.EmailGroupGridItem;
import com.datascope.ui.generated.EmailGroupDeisgn;
import com.datascope.ui.utils.notifications.Notifications;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

import javax.annotation.PostConstruct;


@UIScope
@NavigatorViewName(EmailGroupView.NAME)
@MenuCaption("Emails")
@MenuIcon(VaadinIcons.AT)
@SpringView(name = EmailGroupView.NAME)
public class EmailGroupView extends EmailGroupDeisgn implements View,
        OnDeleteEmailCallback,
        OnEmailTemplateSelectedInGroupCallback,
        OnEmailTemplateEditedCallback {

    static final String NAME = "EmailGroupView";
    private IEmailService service;
    private Notifications notifications;
    private EmailGroupViewController controller;
    private EmailDialogs dialogs;

    public EmailGroupView(IEmailService service, Notifications notifications, EmailGroupViewController controller, EmailDialogs dialogs) {
        this.service = service;
        this.notifications = notifications;
        this.controller = controller;
        this.dialogs = dialogs;
    }

    @PostConstruct
    public void init() {
        controller.initGroupGrid(getEmailGroupsGrid(), this::editGroup, this::deleteGroup, () -> dialogs.createNewGroup(this::createNewGroup));
        controller.initEmailGrid(getEmailsGrid(), this::editTemplate, this::deleteTemplate, () -> dialogs.createNewTemplate(this::createNewTemplateService) ,this);
        service.getGroups(this::groupsLoaded);
        service.getTemplates(this::templatesLoaded);
    }

    private void createNewTemplateService(String newGroup) {
        //service.createNewTemplate(this::addNewGroupToGrid, newGroup);
    }

    private void createNewGroup(String newGroup) {
        service.createNewGroup(this::addNewGroupToGrid, newGroup);
    }

    private void addNewGroupToGrid(int groupId, String groupName) {
        controller.addNewGroupToGrid(groupId, groupName);
    }

    private void editGroup(EmailGroupGridItem item) {
        service.editGroupName(item.getName(), item.getId());
    }

    private void templatesLoaded(EmailTemplate.List emailTemplates) {
        controller.setEmails(getEmailsGrid(), controller.toEmailGridItems(emailTemplates));
        controller.selectFirstEmailGroup(getEmailGroupsGrid());
    }


    private void deleteGroup(EmailGroupGridItem item) {
        dialogs.confirmDeleteEmailGroup(() -> {
            service.deleteEmailGroup(item.getId());
            controller.removeEmailGroup(item);
            notifications.tray("email.group.deleted");
        });
    }

    @Override
    public void deleteTemplate(EmailGridItem item) {
        dialogs.confirmDeleteEmail(() -> {
            service.deleteEmailTemplate(item.getId());
            controller.removeEmailFromGroup(item);
            notifications.tray("email.deleted.from.group");
        });
    }


    private void groupsLoaded(EmailGroup.List groups) {
        controller.showEmailGroupsInGrid(getEmailGroupsGrid(), groups);
    }

    private int getSelectedGroupId() {
        return getEmailGroupsGrid().getSelectedItems().iterator().next().getId();
    }

    @Override
    public void emailTemplateIsActiveChanged(int emailTemplateId, boolean isSelected) {
        if (isSelected)
            service.addEmailToGroup(emailTemplateId, getSelectedGroupId());
        else
            service.removeEmailFromGroup(emailTemplateId, getSelectedGroupId());
    }


    public void editTemplate(EmailGridItem email) {
        service.editEmailTemplate(email.getId(), email.getEmail(), email.getName(), email.getLastName());
    }
}
