package com.datascope.bounded.contexts.email.service.interfaces;

import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;
import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailTemplatesCallback;

public interface IEmailService {
    void getEmailGroups(GetEmailGroupsCallback callback);

    void getEmailTemplates(GetEmailTemplatesCallback callback);

    void deleteEmail(int groupId, int emailId);

    void deleteEmailGroup(int groupId);

    void editGroupName(String value, int groupId);

    void removeEmailFromGroup(int emailTemplateId, int groupId);

    void addEmailToGroup(int emailTemplateId, int groupId);
}
