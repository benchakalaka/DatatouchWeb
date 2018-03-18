package com.datascope.application.ui.email;

import com.datascope.application.ui.email.callbacks.OnDeleteEmailCallback;
import com.datascope.application.ui.email.callbacks.OnEditEmailCallback;
import com.datascope.application.ui.email.callbacks.OnEmailGroupSelectedCallback;
import com.datascope.application.ui.email.elements.EmailGridItem;
import com.datascope.application.ui.email.elements.EmailGroupGridItem;
import com.datascope.application.ui.utils.factories.ButtonFactory;
import com.datascope.bounded.contexts.email.domain.Email;
import com.datascope.bounded.contexts.email.domain.EmailGroup;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.vaadin.icons.VaadinIcons.CLOSE;
import static com.vaadin.icons.VaadinIcons.EDIT;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_SMALL;

@Component
public class EmailGroupUiHelper {

    private String CENTER_ALIGN = "v-align-center";
    private ListDataProvider<EmailGroupGridItem> emailGroupsProvider;
    private ListDataProvider<EmailGridItem> emailsProvider;

    public void initEmailGroupGrid(Grid<EmailGroupGridItem> emailGroupsGrid, OnEmailGroupSelectedCallback callback) {
        emailGroupsGrid.removeAllColumns();

        emailGroupsGrid.addColumn(EmailGroupGridItem::getName)
                .setCaption(EmailGroupGridItem.NAME);

        emailGroupsGrid.addSelectionListener(event ->
                event.getFirstSelectedItem().ifPresent(callback::emailGroupSelected)
        );
    }

    public void initEmailGrid(Grid<EmailGridItem> emailsGrid, EmailGroupView view) {
        emailsGrid.removeAllColumns();

        emailsGrid.addColumn(EmailGridItem::getEmail)
                .setCaption(EmailGridItem.EMAIL)
                .setExpandRatio(6);

        emailsGrid
                .addComponentColumn(item -> buildEmailGridDeleteButton(item, view))
                .setCaption(EmailGridItem.DELETE)
                .setExpandRatio(1)
                .setStyleGenerator(item -> CENTER_ALIGN);

        emailsGrid
                .addComponentColumn(item -> buildEditButton(item, view))
                .setCaption(EmailGridItem.EDIT)
                .setExpandRatio(1)
                .setStyleGenerator(item -> CENTER_ALIGN);
    }


    private Button buildEmailGridDeleteButton(EmailGridItem item, OnDeleteEmailCallback callback) {
        return ButtonFactory.buildButton(CLOSE, BUTTON_SMALL, e -> {

            emailsProvider.getItems().remove(item);
            emailsProvider.refreshAll();

            callback.onDeleteEmailClicked(item);
        });
    }

    private Button buildEditButton(EmailGridItem item, OnEditEmailCallback callback) {
        return ButtonFactory.buildButton(EDIT, BUTTON_SMALL, e -> callback.onEditEmailClicked(item));
    }

    private EmailGroupGridItem.List toEmailGroupGridItems(EmailGroup.List groups) {
        return groups
                .stream()
                .map(item -> new EmailGroupGridItem(item.getId(), item.getName(), toEmailGridItems(item.getEmails())))
                .collect(Collectors.toCollection(EmailGroupGridItem.List::new));
    }

    private EmailGridItem.List toEmailGridItems(Email.List emails) {
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
}
