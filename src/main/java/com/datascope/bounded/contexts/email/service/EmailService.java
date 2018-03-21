package com.datascope.bounded.contexts.email.service;

import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailTemplatesCallback;
import com.datascope.bounded.contexts.email.service.requests.AddEmailToGroupRequest;
import com.datascope.bounded.contexts.email.service.requests.DeleteEmailGroupRequest;
import com.datascope.bounded.contexts.email.service.requests.DeleteEmailRequest;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.email.service.requests.EditEmailGroupName;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    private IRestClient rest;
    private static final String GET_GROUPS = "Email/GetEmailGroups";
    private static final String DELETE_EMAIL_FROM_GROUP = "Email/DeleteFromGroup";
    private static final String DELETE_EMAIL_GROUP = "Email/DeleteGroup";
    private static final String EDIT_EMAIL_GROUP_NAME = "Email/EditGroupName";
    private static final String GET_EMAIL_TEMPLATES = "Email/GetAllEmailTemplates";
    private static final String ADD_EMAIL_TO_GROUP = "Email/AddEmailTemplateToGroup";

    public EmailService(IRestClient client) {
        this.rest = client;
    }


    @Override
    public void getEmailGroups(GetEmailGroupsCallback callback) {
        EmailGroup.List groups = rest.post(EmailGroup.List.class, GET_GROUPS);

        if (CollectionUtils.isNotEmpty(groups))
            callback.onEmailGroupsLoaded(groups);
        else
            callback.emailGroupsNotFound();
    }

    @Override
    public void getEmailTemplates(GetEmailTemplatesCallback callback) {
        EmailTemplate.List templates = rest.post(EmailTemplate.List.class, GET_EMAIL_TEMPLATES);

        if (CollectionUtils.isNotEmpty(templates))
            callback.onEmailTemplatesFound(templates);
        else
            callback.onEmailTemplatesNotFound();
    }

    @Override
    public void deleteEmail(int groupId, int emailId) {
        rest.post(Integer.class, DELETE_EMAIL_FROM_GROUP, new DeleteEmailRequest(groupId, emailId));
    }

    @Override
    public void deleteEmailGroup(int groupId) {
        rest.post(Integer.class, DELETE_EMAIL_GROUP, new DeleteEmailGroupRequest(groupId));
    }

    @Override
    public void editGroupName(String newName, int groupId) {
        rest.post(Integer.class, EDIT_EMAIL_GROUP_NAME, new EditEmailGroupName(newName, groupId));
    }

    @Override
    public void removeEmailFromGroup(int emailTemplateId, int groupId) {
        rest.post(Integer.class, DELETE_EMAIL_FROM_GROUP, new DeleteEmailRequest(groupId, emailTemplateId));
    }

    @Override
    public void addEmailToGroup(int emailTemplateId, int groupId) {
        rest.post(Integer.class, ADD_EMAIL_TO_GROUP, new AddEmailToGroupRequest(groupId, emailTemplateId));
    }
}
