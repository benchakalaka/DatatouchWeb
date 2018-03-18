package com.datascope.application.ui.email;

import com.datascope.application.ui.email.callbacks.OnDeleteEmailCallback;
import com.datascope.application.ui.email.callbacks.OnEditEmailCallback;
import com.datascope.application.ui.email.callbacks.OnEmailGroupSelectedCallback;
import com.datascope.application.ui.email.elements.EmailGridItem;
import com.datascope.application.ui.email.elements.EmailGroupGridItem;
import com.datascope.application.ui.generated.EmailGroupDeisgn;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = EmailGroupView.NAME)
public class EmailGroupView extends EmailGroupDeisgn implements View, GetEmailGroupsCallback, OnDeleteEmailCallback, OnEditEmailCallback, OnEmailGroupSelectedCallback {

    public static final String NAME = "EmailGroupView";
    private IEmailService service;
    private DatatouchNotification notification;
    private EmailGroupUiHelper helper;

    public EmailGroupView(
            IEmailService service,
            DatatouchNotification notification,
            EmailGroupUiHelper helper) {
        this.service = service;
        this.notification = notification;
        this.helper = helper;
    }

    @PostConstruct
    public void init(){
        helper.initEmailGroupGrid(getEmailGroupsGrid(), this);
        helper.initEmailGrid(getEmailsGrid(), this);

        getEmailGroups();
    }

    @Override
    public void emailGroupSelected(EmailGroupGridItem item) {
        helper.setEmails(getEmailsGrid(), item.getEmailGridItems());
    }

    @Override
    public void onEditEmailClicked(EmailGridItem item) {

        notification.success(" -> "+item.getFullName());
    }

    @Override
    public void onDeleteEmailClicked(EmailGridItem item) {
        service.deleteEmail(getSelectedGroupId(),item.getId());
        notification.success("email.deleted.from.group");
    }

    private void getEmailGroups() {
        service.getEmailGroups(this);
    }

    @Override
    public void onEmailGroupsLoaded(EmailGroup.List groups) {
        helper.setEmailGroups(getEmailGroupsGrid(), groups);
    }

    @Override
    public void emailGroupsNotFound() {
        notification.warn("email.groups.not.found");
    }

    private int getSelectedGroupId(){
        return getEmailGroupsGrid().getSelectedItems().iterator().next().getId();
    }
}
