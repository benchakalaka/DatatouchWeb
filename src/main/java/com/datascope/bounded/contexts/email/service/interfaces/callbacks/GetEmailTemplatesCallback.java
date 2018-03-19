package com.datascope.bounded.contexts.email.service.interfaces.callbacks;

import com.datascope.bounded.contexts.email.domain.EmailTemplate;

public interface GetEmailTemplatesCallback {
    void onEmailTemplatesFound(EmailTemplate.List templates);

    void onEmailTemplatesNotFound();
}
