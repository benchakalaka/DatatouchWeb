package com.datascope.ui.email.controller;

import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.ui.email.EmailGroupView;
import com.datascope.ui.email.callbacks.email.OnDeleteEmailCallback;
import com.datascope.ui.email.callbacks.emailgroup.OnDeleteEmailGroupCallback;
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
import com.vaadin.ui.renderers.ComponentRenderer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.vaadin.icons.VaadinIcons.CLOSE;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_SMALL;

@Component
public class EmailGroupViewController extends UiHelper {

    private static final int GRID_BUTTON_EXPAND_RATIO = 1;
    private static final int GRID_FIELDS_EXPAND_RATIO = 8;
    private ListDataProvider<EmailGroupGridItem> emailGroupsProvider;
    private ListDataProvider<EmailGridItem> emailsProvider;
    private String CENTER_ALIGN = "v-align-center";


    public EmailGroupViewController(Labels labels) {
        super(labels);
    }

    public void initEmailGroupGrid(Grid<EmailGroupGridItem> emailGroupsGrid, EmailGroupView view) {
        emailGroupsGrid.removeAllColumns();

        emailGroupsGrid.getEditor().setEnabled(true);
        emailGroupsGrid.getEditor().addSaveListener(editorSaveEvent -> view.onEditEmailGroupClicked(editorSaveEvent.getBean()));

        emailGroupsGrid.addColumn(EmailGroupGridItem::getName)
                .setCaption(getLabel("email.grid.group.name"))
                .setEditorComponent(new TextField(), EmailGroupGridItem::setName)
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailGroupsGrid.addSelectionListener(event ->
                event.getFirstSelectedItem().ifPresent(this::emailGroupSelected)
        );

        emailGroupsGrid
                .addComponentColumn(item -> buildEmailGroupGridDeleteButton(item, view))
                .setCaption(getLabel("email.grid.email.delete"))
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setStyleGenerator(item -> CENTER_ALIGN);
    }

    public void initEmailGrid(Grid<EmailGridItem> emailsGrid, EmailGroupView view) {
        emailsGrid.removeAllColumns();

        emailsGrid.getEditor().setEnabled(true);
        emailsGrid.getEditor().addSaveListener(editorSaveEvent -> view.onEditEmailTemplate(editorSaveEvent.getBean()));

        emailsGrid.addColumn(EmailGridItem::getName)
                .setCaption(getLabel("email.grid.email.name"))
                .setEditorComponent(new TextField(), EmailGridItem::setName)
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailsGrid.addColumn(EmailGridItem::getLastName)
                .setEditorComponent(new TextField(), EmailGridItem::setLastName)
                .setCaption(getLabel("email.grid.email.last.name"))
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailsGrid.addColumn(EmailGridItem::getEmail)
                .setCaption(getLabel("email.grid.email.email"))
                .setEditorComponent(new TextField(), EmailGridItem::setEmail)
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailsGrid.addColumn(email -> getEmailGridItemActiveCheckBox(email, view))
                .setExpandRatio(1)
                .setRenderer(new ComponentRenderer())
                .setCaption(getLabel("email.grid.email.active"))
                .setStyleGenerator(item -> CENTER_ALIGN);

        emailsGrid
                .addComponentColumn(item -> buildEmailGridDeleteButton(item, view))
                .setCaption(getLabel("email.grid.email.delete"))
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setStyleGenerator(item -> CENTER_ALIGN);
    }


    private void emailGroupSelected(EmailGroupGridItem group) {
        emailsProvider.getItems().forEach(item ->
                item.setActive(group.containsEmailTemplate(item.getId()))
        );

        emailsProvider.refreshAll();
    }

    private CheckBox getEmailGridItemActiveCheckBox(EmailGridItem email, EmailGroupView view) {
        CheckBox checkBoxIsActive = new CheckBox("", email.isActive());
        checkBoxIsActive.addValueChangeListener(e -> view.onEmailTemplateClicked(email.getId(), e.getValue()));
        return checkBoxIsActive;
    }

    private Button buildEmailGroupGridDeleteButton(EmailGroupGridItem item, OnDeleteEmailGroupCallback callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> callback.onDeleteEmailGroupClicked(item));
    }

    private Button buildEmailGridDeleteButton(EmailGridItem item, OnDeleteEmailCallback callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> callback.onDeleteEmailClicked(item));
    }


    private EmailGroupGridItem.List toEmailGroupGridItems(EmailGroup.List groups) {
        return groups
                .stream()
                .map(item -> new EmailGroupGridItem(item.getId(), item.getName(), toEmailGridItems(item.getEmails())))
                .collect(Collectors.toCollection(EmailGroupGridItem.List::new));
    }

    public EmailGridItem.List toEmailGridItems(EmailTemplate.List emails) {
        return emails
                .stream()
                .map(EmailGridItem::fromEmail)
                .collect(Collectors.toCollection(EmailGridItem.List::new));
    }

    public void showEmailGroupsInGrid(Grid<EmailGroupGridItem> emailsGrid, EmailGroup.List groups) {
        refreshEmailGroupProvider(toEmailGroupGridItems(groups));
        emailsGrid.setDataProvider(emailGroupsProvider);

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
    }

    public void removeEmailGroup(EmailGroupGridItem item) {
        emailGroupsProvider.getItems().remove(item);
        emailGroupsProvider.refreshAll();
    }

    public void removeEmailFromGroup(EmailGridItem item) {
        emailsProvider.getItems().remove(item);
        emailsProvider.refreshAll();
    }

    public void selectFirstEmailGroup(Grid<EmailGroupGridItem> grid) {
        emailGroupsProvider
                .getItems()
                .stream()
                .findFirst()
                .ifPresent(grid::select);
    }
}
