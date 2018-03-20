package com.datascope.ui.email.controller;

import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.ui.email.EmailGroupView;
import com.datascope.ui.email.callbacks.email.OnDeleteEmailCallback;
import com.datascope.ui.email.callbacks.email.OnEmailTemplateSelectedInGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnAddEmailToGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnDeleteEmailGroupCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnEditEmailGroupCallback;
import com.datascope.ui.email.elements.EmailGridItem;
import com.datascope.ui.email.elements.EmailGroupGridItem;
import com.datascope.ui.utils.factories.ButtonFactory;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.vaadin.icons.VaadinIcons.*;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_SMALL;

@Component
public class EmailGroupViewController extends UiHelper {

    private static final int GRID_BUTTON_EXPAND_RATIO = 1;
    private static final int GRID_FIELDS_EXPAND_RATIO = 8;
    private ListDataProvider<EmailGroupGridItem> emailGroupsProvider;
    private ListDataProvider<EmailGridItem> emailsProvider;
    private String CENTER_ALIGN = "v-align-center";
    private EmailGroupGridItem lastEmailGroupClicked;

    public EmailGroupViewController(Labels labels) {
        super(labels);
    }

    public void initEmailGroupGrid(Grid<EmailGroupGridItem> emailGroupsGrid, EmailGroupView view) {
        emailGroupsGrid.removeAllColumns();

        emailGroupsGrid.addColumn(EmailGroupGridItem::getName)
                .setCaption(getLabel("email.grid.group.name"))
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailGroupsGrid.addSelectionListener(event ->
                event.getFirstSelectedItem().ifPresent(view::emailGroupSelected)
        );

        emailGroupsGrid
                .addComponentColumn(item -> buildAddButton(item, view))
                .setCaption(getLabel("email.grid.email.add"))
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setStyleGenerator(item -> CENTER_ALIGN);

        emailGroupsGrid
                .addComponentColumn(item -> buildEditButton(item, view))
                .setCaption(getLabel("email.grid.email.edit"))
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setStyleGenerator(item -> CENTER_ALIGN);

        emailGroupsGrid
                .addComponentColumn(item -> buildEmailGroupGridDeleteButton(item, view))
                .setCaption(getLabel("email.grid.email.delete"))
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setStyleGenerator(item -> CENTER_ALIGN);
    }

    public void initEmailGrid(Grid<EmailGridItem> emailsGrid, EmailGroupView view) {
        emailsGrid.removeAllColumns();

        emailsGrid.addColumn(EmailGridItem::getEmail)
                .setCaption(getLabel("email.grid.email.email"))
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailsGrid
                .addComponentColumn(item -> buildEmailGridDeleteButton(item, view))
                .setCaption(getLabel("email.grid.email.delete"))
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setStyleGenerator(item -> CENTER_ALIGN);
    }

    public Grid<EmailTemplate> buildEmailsGrid(EmailTemplate.List templates, OnEmailTemplateSelectedInGroupCallback callback) {
        Grid<EmailTemplate> grid = new Grid<>();
        grid.setItems(templates);

        grid.addColumn(EmailTemplate::getFullName)
                .setCaption(getLabel("email.grid.email.name"))
                .setExpandRatio(5);

        grid.addColumn(EmailTemplate::getEmail)
                .setCaption(getLabel("email.grid.email.email"))
                .setExpandRatio(5);

        grid.addComponentColumn(item -> buildCheckbox(item, callback))
                .setExpandRatio(1)
                .setStyleGenerator(item -> CENTER_ALIGN);

        return grid;
    }

    private CheckBox buildCheckbox(EmailTemplate item, OnEmailTemplateSelectedInGroupCallback callback) {
        CheckBox checkBox = new CheckBox("", lastEmailGroupClicked.containsEmailTemplate(item.getId()));
        checkBox.addValueChangeListener(e -> callback.onEmailTemplateClicked(item.getId(),getLastClickedEmailGroupId(),e.getValue()));
        return checkBox;
    }

    private Button buildEmailGroupGridDeleteButton(EmailGroupGridItem item, OnDeleteEmailGroupCallback callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> callback.onDeleteEmailGroupClicked(item));
    }

    private Button buildEmailGridDeleteButton(EmailGridItem item, OnDeleteEmailCallback callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> callback.onDeleteEmailClicked(item));
    }

    private Button buildEditButton(EmailGroupGridItem item, OnEditEmailGroupCallback callback) {
        return ButtonFactory.buildButton(EDIT, BUTTON_SMALL, e -> callback.onEditEmailGroupClicked(item));
    }

    private Button buildAddButton(EmailGroupGridItem item, OnAddEmailToGroupCallback callback) {
        return ButtonFactory.buildButton(PLUS_CIRCLE, BUTTON_SMALL, e -> callback.onAddEmailToGroupClicked(item));
    }

    private EmailGroupGridItem.List toEmailGroupGridItems(EmailGroup.List groups) {
        return groups
                .stream()
                .map(item -> new EmailGroupGridItem(item.getId(), item.getName(), toEmailGridItems(item.getEmails())))
                .collect(Collectors.toCollection(EmailGroupGridItem.List::new));
    }

    private EmailGridItem.List toEmailGridItems(EmailTemplate.List emails) {
        return emails
                .stream()
                .map(EmailGridItem::fromEmail)
                .collect(Collectors.toCollection(EmailGridItem.List::new));
    }

    public void setEmailGroups(Grid<EmailGroupGridItem> emailsGrid, EmailGroup.List groups) {
        refreshEmailGroupProvider(toEmailGroupGridItems(groups));
        emailsGrid.setDataProvider(emailGroupsProvider);
        emailGroupsProvider
                .getItems()
                .stream()
                .findFirst()
                .ifPresent(emailsGrid::select);
    }

    private void refreshEmailGroupProvider(EmailGroupGridItem.List items) {
        emailGroupsProvider = new ListDataProvider<>(items);
    }

    private void refreshEmailProvider(EmailGridItem.List items) {
        emailsProvider = new ListDataProvider<>(items);
    }

    public void setEmails(Grid<EmailGridItem> emailsGrid, EmailGridItem.List emailGridItems) {
        refreshEmailProvider(emailGridItems);
        emailsGrid.setDataProvider(emailsProvider);
        emailsProvider
                .getItems()
                .stream()
                .findFirst()
                .ifPresent(emailsGrid::select);
    }

    public void removeEmailGroup(EmailGroupGridItem item) {
        emailGroupsProvider.getItems().remove(item);
        emailGroupsProvider.refreshAll();
    }

    public void removeEmailFromGroup(EmailGridItem item) {
        emailsProvider.getItems().remove(item);
        emailsProvider.refreshAll();
    }

    public void editEmailGroupItem(EmailGroupGridItem item) {
        emailGroupsProvider.refreshItem(item);
    }

    public TextField createEditEmailGroupTextField() {
        return new TextField(getLabel("email.email.group.new.name"));
    }


    public void setLastEmailGroupClicked(EmailGroupGridItem item) {
        this.lastEmailGroupClicked = item;
    }

    public String getLastClickedEmailGroupName() {
        return null != lastEmailGroupClicked
                ? lastEmailGroupClicked.getName()
                : "";
    }

    private int getLastClickedEmailGroupId() {
        return null != lastEmailGroupClicked
                ? lastEmailGroupClicked.getId()
                : 0;
    }
}
