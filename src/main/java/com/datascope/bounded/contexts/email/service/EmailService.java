package com.datascope.bounded.contexts.email.service;

import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.service.requests.*;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.service.interfaces.IEmailService;
import com.datascope.bounded.contexts.core.services.IRestClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
public class EmailService implements IEmailService {

    private IRestClient rest;
    private static final String GET_GROUPS = "Email/GetEmailGroups";
    private static final String DELETE_EMAIL_FROM_GROUP = "Email/DeleteFromGroup";
    private static final String DELETE_EMAIL_GROUP = "Email/DeleteGroup";
    private static final String EDIT_EMAIL_GROUP_NAME = "Email/EditGroupName";
    private static final String GET_EMAIL_TEMPLATES = "Email/GetAllEmailTemplates";
    private static final String ADD_EMAIL_TO_GROUP = "Email/AddEmailTemplateToGroup";
    private static final String DELETE_EMAIL_TEMPLATE = "Email/DeleteEmailTemplate";
    private static final String EDIT_EMAIL_TEMPLATE = "Email/EditEmailTemplate";
    private static final String CREATE_GROUP = "Email/CreateGroup";

    public EmailService(IRestClient client) {
        this.rest = client;
    }

    @Override
    public void getGroups(Consumer<EmailGroup.List> consumer) {
        Observable.just(rest.post(EmailGroup.List.class, GET_GROUPS))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(emailGroups -> CollectionUtils.isNotEmpty(emailGroups) ? emailGroups : EmailGroup.List.empty())
                .subscribe(consumer::accept);
    }

    @Override
    public void getTemplates(Consumer<EmailTemplate.List> onSuccess) {
        Observable.just(rest.post(EmailTemplate.List.class, GET_EMAIL_TEMPLATES))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(emails -> CollectionUtils.isNotEmpty(emails) ? emails : EmailTemplate.List.empty())
                .subscribe(onSuccess::accept);
    }

    @Override
    public void deleteEmailTemplate(int templateId) {
        Observable.just(rest.post(Integer.class, DELETE_EMAIL_TEMPLATE, new DeleteEmailTemplateRequest(templateId)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
    }

    @Override
    public void deleteEmailGroup(int groupId) {
        Observable.just(rest.post(Integer.class, DELETE_EMAIL_GROUP, new DeleteEmailGroupRequest(groupId)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
    }

    @Override
    public void editGroupName(String newName, int groupId) {
        Observable.just(rest.post(Integer.class, EDIT_EMAIL_GROUP_NAME, new EditEmailGroupName(newName, groupId)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
    }

    @Override
    public void removeEmailFromGroup(int emailTemplateId, int groupId) {
        Observable.just(rest.post(Integer.class, DELETE_EMAIL_FROM_GROUP, new DeleteEmailFromGroupRequest(groupId, emailTemplateId)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
    }

    @Override
    public void addEmailToGroup(int emailTemplateId, int groupId) {
        Observable.just(rest.post(Integer.class, ADD_EMAIL_TO_GROUP, new AddEmailToGroupRequest(groupId, emailTemplateId)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
    }

    @Override
    public void editEmailTemplate(int emailId, String email, String name, String lastName) {
        Observable.just(rest.post(Integer.class, EDIT_EMAIL_TEMPLATE, new EditEmailTemplateRequest(emailId, email, name, lastName)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());
    }

    @Override
    public void createNewGroup(BiConsumer<Integer, String> onGroupCreatedListener, String newGroupName) {
        Observable.just(rest.post(Integer.class, CREATE_GROUP, new CreateGroupRequest(newGroupName)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
        .subscribe(groupId -> onGroupCreatedListener.accept(groupId,newGroupName));
    }
}
