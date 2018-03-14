package com.datascope.application.ui.company;

import com.datascope.application.ui.DatatouchUI;
import com.datascope.application.ui.company.callbacks.CompanyColorChangedCallback;
import com.datascope.application.ui.company.callbacks.OnCompanySelectedCallback;
import com.datascope.application.ui.company.elements.CompanyGridItem;
import com.datascope.application.ui.generated.CompanyDesign;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.bounded.contexts.company.domain.Company;
import com.datascope.bounded.contexts.company.service.interfaces.ICompanyService;
import com.datascope.bounded.contexts.company.service.interfaces.GetCompaniesCallback;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

import static java.util.Optional.*;

@UIScope
@SpringView(name = CompanyView.NAME, ui = {DatatouchUI.class})

public class CompanyView extends CompanyDesign implements
        View,
        GetCompaniesCallback,
        OnCompanySelectedCallback,
        CompanyColorChangedCallback {

    public static final String NAME = "CompanyView";
    private ICompanyService service;
    private DatatouchNotification notification;
    private CompanyViewUiHelper helper = new CompanyViewUiHelper();

    public CompanyView(ICompanyService service, DatatouchNotification notification) {
        this.service = service;
        this.notification = notification;
    }

    @PostConstruct
    public void init() {
        helper.initGrid(getCompaniesGrid(), this);
        helper.setOnColorPicker(getColorPickerArea(), this);
        getCompanies();
    }

    private void getCompanies() {
        service.getCompanies(this);
    }

    @Override
    public void companiesFound(Company.List companies) {
        CompanyGridItem.List items = helper.toGridItems(companies);
        getCompaniesGrid().setItems(items);
        ofNullable(companies).ifPresent(item -> getCompaniesGrid().select(items.get(0)));
    }

    @Override
    public void companiesNotFound() {
        notification.warn("no.companies.found");
    }

    @Override
    public void companySelected(CompanyGridItem item) {
        Color color = helper.getColor(item.getColour());
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
