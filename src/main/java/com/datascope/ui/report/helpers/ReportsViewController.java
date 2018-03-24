package com.datascope.ui.report.helpers;

import com.datascope.bounded.contexts.settingslab.domain.SettingsModule;
import com.datascope.ui.report.callbacks.ReportSelectedCallback;
import com.datascope.ui.report.callbacks.SelectReportGeneratedDateCallback;
import com.datascope.ui.report.elements.ModuleComboBoxItem;
import com.datascope.ui.report.elements.ReportGroupGridItem;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TreeGrid;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Optional.*;

@Component
public class ReportsViewController extends UiHelper {
    private TreeData<ReportGroupGridItem> data = new TreeData<>();
    private TreeDataProvider<ReportGroupGridItem> reportGroupGridItemTreeDataProvider = new TreeDataProvider<>(data);
    private static final int DEFAULT_EXPAND_RATIO = 3;


    public ReportsViewController(Labels labels) {
        super(labels);
    }

    public void initComboBox(ComboBox<ModuleComboBoxItem> cb, Runnable r) {
        cb.addValueChangeListener(valueChangeEvent -> r.run());
    }

    public void initTree(TreeGrid<ReportGroupGridItem> treeGrid, ReportSelectedCallback callback) {
        treeGrid.setSizeFull();
        treeGrid.addColumn(ReportGroupGridItem::getCaption)
                .setCaption(getLabel("report.grid.reports"))
                .setExpandRatio(DEFAULT_EXPAND_RATIO);

        treeGrid.addItemClickListener(click -> {
            if (click.getItem().hasUrls())
                callback.onReportSelected(click.getItem().getUrl());
        });

        treeGrid.setDataProvider(reportGroupGridItemTreeDataProvider);
    }

    public void setReports(ReportGroupGridItem.List reports) {
        data.clear();
        refreshTree.accept(reports);
    }

    public void initDatePicker(DateField datePicker, Runnable callback) {
        datePicker.setValue(LocalDate.now());
        datePicker.addValueChangeListener(valueChangeEvent -> callback.run());
    }

    public void clearReportsTree() {
        setReports(ReportGroupGridItem.List.empty());
    }

    private Consumer<ReportGroupGridItem.List> refreshTree = reports -> {
        for (ReportGroupGridItem root : reports) {
            data.addItem(null, root);
            data.addItems(root, root.getReportsAdGridItems());
        }

        reportGroupGridItemTreeDataProvider.refreshAll();
    };


    public void showModules(ComboBox<ModuleComboBoxItem> cbModules, SettingsModule.List modules) {
        ofNullable(modules)
                .ifPresent(item ->  {
                    ModuleComboBoxItem.List items = toComboBoxItems(modules);
                    cbModules.setItems(items);
                    cbModules.setSelectedItem(items.get(0));
                });
    }

    private ModuleComboBoxItem.List toComboBoxItems(SettingsModule.List modules) {
        return modules.stream().map(ModuleComboBoxItem::from)
                .collect(Collectors.toCollection(ModuleComboBoxItem.List::new));
    }

    public void expandAll(TreeGrid<ReportGroupGridItem> tree) {
        tree.expand(reportGroupGridItemTreeDataProvider.getTreeData().getRootItems());
    }
}
