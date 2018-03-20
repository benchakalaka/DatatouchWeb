package com.datascope.ui.email;

import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailTemplatesCallback;
import com.datascope.ui.email.callbacks.email.OnDeleteEmailCallback;
import com.datascope.ui.email.callbacks.email.OnEmailTemplateSelectedInGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnAddEmailToGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnDeleteEmailGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnEditEmailGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnEmailGroupSelectedCallback;
import com.datascope.ui.email.dialog.EmailDialogs;
import com.datascope.ui.email.elements.EmailGridItem;
import com.datascope.ui.email.elements.EmailGroupGridItem;
import com.datascope.ui.email.controller.EmailGroupViewController;
import com.datascope.ui.generated.EmailGroupDeisgn;
import com.datascope.ui.utils.notifications.Messages;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;

import static com.vaadin.navigator.ViewChangeListener.*;

@UIScope
@NavigatorViewName(EmailGroupView.NAME)
@MenuCaption("Emails")
@MenuIcon(VaadinIcons.SITEMAP)
@SpringView(name = EmailGroupView.NAME)
public class EmailGroupView extends EmailGroupDeisgn implements View, GetEmailGroupsCallback, OnDeleteEmailCallback, OnEditEmailGroupCallback, OnEmailGroupSelectedCallback, OnDeleteEmailGroupCallback, GetEmailTemplatesCallback, OnAddEmailToGroupCallback, OnEmailTemplateSelectedInGroupCallback {

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
    }

    @Override
    public void enter(ViewChangeEvent event) {
        service.getEmailGroups(this);
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
        TextField input = controller.createEditEmailGroupTextField();
        input.setValue(item.getName());
        dialogs.editEmailGroup(input, () -> {
            service.editGroupName(input.getValue(), item.getId());
            item.setName(input.getValue());
            controller.editEmailGroupItem(item);
        });
    }

    @Override
    public void onDeleteEmailClicked(EmailGridItem item) {
        dialogs.confirmDeleteEmail(() -> {
            service.deleteEmail(getSelectedGroupId(), item.getId());
            controller.removeEmailFromGroup(item);
            messages.tray("email.deleted.from.group");
        });
    }

    @Override
    public void onEmailGroupsLoaded(EmailGroup.List groups) {
        controller.setEmailGroups(getEmailGroupsGrid(), groups);
    }

    @Override
    public void emailGroupsNotFound() {
        messages.warn("email.groups.not.found");
    }

    private int getSelectedGroupId() {
        return getEmailGroupsGrid().getSelectedItems().iterator().next().getId();
    }

    @Override
    public void onEmailTemplatesFound(EmailTemplate.List templates) {
        Grid<EmailTemplate> grid = controller.buildEmailsGrid(templates, this);
        dialogs.selectEmailsInGroup(grid,controller.getLastClickedEmailGroupName());
    }

    @Override
    public void onEmailTemplatesNotFound() {
        messages.tray("email.templates.not.found");
    }

    @Override
    public void onAddEmailToGroupClicked(EmailGroupGridItem item) {
        // Don't like it, maybe find a way when clicking on item, it should be selected
        controller.setLastEmailGroupClicked(item);
        service.getEmailTemplates(this);
    }

    @Override
    public void onEmailTemplateClicked(int emailTemplateId, int groupId, boolean isSelected) {
        if (isSelected)
            service.removeEmailFromGroup(emailTemplateId,groupId);
        else
            service.addEmailToGroup(emailTemplateId,groupId);
    }
}
