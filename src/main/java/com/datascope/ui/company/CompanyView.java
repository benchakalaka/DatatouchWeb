package com.datascope.ui.company;

import com.datascope.bounded.contexts.company.domain.Company;
import com.datascope.bounded.contexts.company.service.interfaces.GetCompaniesCallback;
import com.datascope.bounded.contexts.company.service.interfaces.ICompanyService;
import com.datascope.ui.company.callbacks.CompanyColorChangedCallback;
import com.datascope.ui.company.callbacks.OnCompanySelectedCallback;
import com.datascope.ui.company.controller.CompanyViewController;
import com.datascope.ui.company.elements.CompanyGridItem;
import com.datascope.ui.generated.CompanyDesign;
import com.datascope.ui.utils.notifications.Messages;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.util.Optional;


@UIScope


@MenuCaption("Company")
@MenuIcon(VaadinIcons.COMPILE)
@NavigatorViewName(CompanyView.NAME)
@SpringView(name = CompanyView.NAME )
public class CompanyView extends CompanyDesign implements
        View,
        GetCompaniesCallback,
        OnCompanySelectedCallback,
        CompanyColorChangedCallback {

    static final String NAME = "CompanyView";
    private ICompanyService service;
    private Messages messages;
    private CompanyViewController controller;

    public CompanyView(
            ICompanyService service,
            Messages messages,
            CompanyViewController controller) {
        this.service = service;
        this.messages = messages;
        this.controller = controller;
    }

    @PostConstruct
    public void init() {
        controller.initGrid(getCompaniesGrid(), this);
        controller.setOnColorPicker(getColorPickerArea(), this);
        service.getCompanies(this);
    }


    @Override
    public void companiesFound(Company.List companies) {
        CompanyGridItem.List items = controller.toGridItems(companies);
        getCompaniesGrid().setItems(items);
        Optional.ofNullable(companies).ifPresent(item -> getCompaniesGrid().select(items.get(0)));
    }

    @Override
    public void companiesNotFound() {
        messages.warn("no.companies.found");
    }

    @Override
    public void companySelected(CompanyGridItem item) {
        Color color = controller.getColor(item.getColour());
        getColorPickerArea().setValue(color);
    }

    @Override
    public void companyColorChanged(int alpha, int r, int g, int b) {
        if (isCompanySelected())
            service.changeCompanyColor(getSelectedCompanyId(), alpha, r, g, b);
    }

    private int getSelectedCompanyId() {
        return getCompaniesGrid().getSelectedItems().iterator().next().getId();
    }

    private boolean isCompanySelected() {
        return !getCompaniesGrid().getSelectedItems().isEmpty();
    }
}
