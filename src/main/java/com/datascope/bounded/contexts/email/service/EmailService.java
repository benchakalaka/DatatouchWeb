package com.datascope.bounded.contexts.email.service;

import com.datascope.bounded.contexts.email.service.requests.DeleteEmailGroupRequest;
import com.datascope.bounded.contexts.email.service.requests.DeleteEmailRequest;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.datascope.bounded.contexts.core.services.IRestClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    private static final String GET_GROUPS = "Email/getEmailGroups";
    private static final String DELETE_EMAIL_FROM_GROUP = "Email/DeleteFromGroup";
    private static final String DELETE_EMAIL_GROUP = "Email/DeleteGroup";
    private IRestClient rest;

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
    public void deleteEmail(int groupId, int emailId) {
        rest.post(Integer.class, DELETE_EMAIL_FROM_GROUP, new DeleteEmailRequest(groupId, emailId));
    }

    @Override
    public void deleteEmailGroup(int groupId) {
        rest.post(Integer.class, DELETE_EMAIL_GROUP, new DeleteEmailGroupRequest(groupId));
    }

    @Override
    public void editGroupName(String value, int groupId) {

    }
}
