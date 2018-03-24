package com.datascope.bounded.contexts.email.service.interfaces;

import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailTemplatesCallback;

import java.util.function.Consumer;

public interface IEmailService {
    void getGroups(Consumer<EmailGroup.List> onSuccess);

    void getTemplates(Consumer<EmailTemplate.List> onSuccess);

    void deleteEmailTemplate(int templateId);

    void deleteEmailGroup(int groupId);

    void editGroupName(String value, int groupId);

    void removeEmailFromGroup(int emailTemplateId, int groupId);

    void addEmailToGroup(int emailTemplateId, int groupId);

    void editEmailTemplate(int emailId, String email, String name, String lastName);
}
