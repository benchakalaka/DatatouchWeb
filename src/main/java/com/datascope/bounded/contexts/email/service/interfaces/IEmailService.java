package com.datascope.bounded.contexts.email.service.interfaces;

import com.datascope.bounded.contexts.email.service.interfaces.callbacks.GetEmailGroupsCallback;

public interface IEmailService {
    void getEmailGroups(GetEmailGroupsCallback callback);

    void deleteEmail(int groupId, int emailId);
}
