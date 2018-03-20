package com.datascope.ui.email.helpers;

import com.datascope.bounded.contexts.email.domain.EmailTemplate;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.datascope.ui.email.EmailGroupView;
import com.datascope.ui.email.callbacks.OnDeleteEmailCallback;
import com.datascope.ui.email.callbacks.OnDeleteEmailGroupCallback;
import com.datascope.ui.email.callbacks.OnEditEmailGroupCallback;
import com.datascope.ui.email.elements.EmailGridItem;
import com.datascope.ui.email.elements.EmailGroupGridItem;
import com.datascope.ui.utils.factories.ButtonFactory;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.SuperHelper;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.vaadin.icons.VaadinIcons.CLOSE;
import static com.vaadin.icons.VaadinIcons.EDIT;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_SMALL;

@Component
public class EmailGroupUiHelper extends SuperHelper {

    private static final int GRID_BUTTON_EXPAND_RATIO = 1;
    private static final int GRID_FIELDS_EXPAND_RATIO = 8;
    private ListDataProvider<EmailGroupGridItem> emailGroupsProvider;
    private ListDataProvider<EmailGridItem> emailsProvider;
    private String CENTER_ALIGN = "v-align-center";

    public EmailGroupUiHelper(Labels labels) {
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


    private Button buildEmailGroupGridDeleteButton(EmailGroupGridItem item, OnDeleteEmailGroupCallback callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> callback.onDeleteEmailGroupClicked(item));
    }

    private Button buildEmailGridDeleteButton(EmailGridItem item, OnDeleteEmailCallback callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> callback.onDeleteEmailClicked(item));
    }

    private Button buildEditButton(EmailGroupGridItem item, OnEditEmailGroupCallback callback) {
        return ButtonFactory.buildButton(EDIT, BUTTON_SMALL, e -> callback.onEditEmailGroupClicked(item));
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
}
