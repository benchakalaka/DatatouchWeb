package com.datascope.ui.email.controller;

import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.ui.email.EmailGroupView;
import com.datascope.ui.email.elements.EmailGridItem;
import com.datascope.ui.email.elements.EmailGroupGridItem;
import com.datascope.ui.utils.factories.ButtonFactory;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.vaadin.icons.VaadinIcons.CLOSE;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_SMALL;

@Component
public class EmailGroupViewController {

    private static final int GRID_BUTTON_EXPAND_RATIO = 1;
    private static final int GRID_FIELDS_EXPAND_RATIO = 8;
    private ListDataProvider<EmailGroupGridItem> emailGroupsProvider;
    private ListDataProvider<EmailGridItem> emailsProvider;
    private String CENTER_ALIGN = "v-align-center";

    @Value("${email.grid.group.name}")
    private String name;

    @Value("${email.grid.email.delete}")
    private String delete;

    @Value("${email.grid.email.active}")
    private String active;

    @Value("${email.grid.email.last.name}")
    private String lastName;

    @Value("${email.grid.email.email}")
    private String email;


    public EmailGroupViewController() {

    }

    public void initGroupGrid(Grid<EmailGroupGridItem> emailGroupsGrid,
                              Consumer<EmailGroupGridItem> onEdit,
                              Consumer<EmailGroupGridItem> onDelete,
                              Runnable onAddNewGroup) {

        emailGroupsGrid.removeAllColumns();

        emailGroupsGrid.getEditor().setEnabled(true);
        emailGroupsGrid.getEditor().addSaveListener(event -> onEdit.accept(event.getBean()));

        emailGroupsGrid.addColumn(EmailGroupGridItem::getName)
                .setId(name)
                .setEditorComponent(new TextField(), EmailGroupGridItem::setName)
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailGroupsGrid.addSelectionListener(event ->
                event.getFirstSelectedItem().ifPresent(this::selectActiveTemplates)
        );

        emailGroupsGrid
                .addComponentColumn(item -> buildEmailGroupGridDeleteButton(item, onDelete))
                .setCaption(delete)
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setHidable(true)
                .setStyleGenerator(item -> CENTER_ALIGN);

        Button buttonCreateNewGroup = new Button(name, VaadinIcons.PLUS);
        buttonCreateNewGroup.addClickListener(e -> onAddNewGroup.run());
        buttonCreateNewGroup.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        emailGroupsGrid
                .getDefaultHeaderRow()
                .getCell(name)
                .setComponent(buttonCreateNewGroup);
    }

    public void initEmailGrid(Grid<EmailGridItem> emailsGrid,
                              Consumer<EmailGridItem> onEditEmailTemplate,
                              Consumer<EmailGridItem> onDeleteEmailTemplate,
                              EmailGroupView view) {
        emailsGrid.removeAllColumns();

        emailsGrid.getEditor().setEnabled(true);
        emailsGrid.getEditor().addSaveListener(editorSaveEvent -> onEditEmailTemplate.accept(editorSaveEvent.getBean()));

        emailsGrid.addColumn(EmailGridItem::getName)
                .setCaption(name)
                .setHidable(true)
                .setEditorComponent(new TextField(), EmailGridItem::setName)
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailsGrid.addColumn(EmailGridItem::getLastName)
                .setEditorComponent(new TextField(), EmailGridItem::setLastName)
                .setCaption(lastName)
                .setHidable(true)
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailsGrid.addColumn(EmailGridItem::getEmail)
                .setCaption(email)
                .setEditorComponent(new TextField(), EmailGridItem::setEmail)
                .setExpandRatio(GRID_FIELDS_EXPAND_RATIO);

        emailsGrid.addColumn(email -> getEmailGridItemActiveCheckBox(email, view))
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setRenderer(new ComponentRenderer())
                .setCaption(active)
                .setHidable(true)
                .setStyleGenerator(item -> CENTER_ALIGN);

        emailsGrid
                .addComponentColumn(item -> buildEmailGridDeleteButton(item, onDeleteEmailTemplate))
                .setCaption(delete)
                .setHidable(true)
                .setExpandRatio(GRID_BUTTON_EXPAND_RATIO)
                .setStyleGenerator(item -> CENTER_ALIGN);
    }

    private Button buildEmailGridDeleteButton(EmailGridItem item, Consumer<EmailGridItem> callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> callback.accept(item));
    }


    private void selectActiveTemplates(EmailGroupGridItem group) {
        emailsProvider.getItems().forEach(item ->
                item.setActive(group.containsEmailTemplate(item.getId()))
        );

        emailsProvider.refreshAll();
    }

    private CheckBox getEmailGridItemActiveCheckBox(EmailGridItem email, EmailGroupView view) {
        CheckBox checkBoxIsActive = new CheckBox("", email.isActive());
        checkBoxIsActive.addValueChangeListener(e -> view.emailTemplateIsActiveChanged(email.getId(), e.getValue()));
        return checkBoxIsActive;
    }

    private Button buildEmailGroupGridDeleteButton(EmailGroupGridItem item, Consumer<EmailGroupGridItem> onDeleteEmailGroup) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> onDeleteEmailGroup.accept(item));
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

    public void addNewGroupToGrid(int groupId, String groupName) {
        emailGroupsProvider.getItems().add(new EmailGroupGridItem(groupId, groupName));
        emailGroupsProvider.refreshAll();
    }
}
