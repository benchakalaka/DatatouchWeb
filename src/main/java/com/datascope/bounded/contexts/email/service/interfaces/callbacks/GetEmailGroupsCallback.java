package com.datascope.bounded.contexts.email.service.interfaces.callbacks;

import com.datascope.bounded.contexts.email.domain.EmailGroup;

public interface GetEmailGroupsCallback {
    void onEmailGroupsLoaded(EmailGroup.List groups);
    void emailGroupsNotFound();
}
