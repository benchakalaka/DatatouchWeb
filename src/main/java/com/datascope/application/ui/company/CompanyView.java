package com.datascope.application.ui.company;

import com.datascope.application.ui.DatatouchUI;
import com.datascope.application.ui.company.callbacks.CompanyColorChangedCallback;
import com.datascope.application.ui.company.callbacks.OnCompanySelectedCallback;
import com.datascope.application.ui.company.elements.CompanyGridItem;
import com.datascope.application.ui.generated.CompanyDesign;
import com.datascope.application.ui.utils.common.ColorUtils;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.bounded.contexts.company.domain.Company;
import com.datascope.bounded.contexts.company.service.interfaces.ICompanyService;
import com.datascope.bounded.contexts.company.service.interfaces.GetCompaniesCallback;
import com.vaadin.navigator.View;
import com.vaadin.sass.internal.util.ColorUtil;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static com.datascope.application.ui.utils.common.ColorUtils.*;
import static java.util.Optional.*;

@UIScope
@SpringView(name = CompanyView.NAME, ui = {DatatouchUI.class})
public class CompanyView extends CompanyDesign implements View, GetCompaniesCallback, OnCompanySelectedCallback, CompanyColorChangedCallback {

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
        helper.setOnColorPicker(getCompanyColorPicker(), this);
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
        getCompanyColorPicker().setValue(new Color(getR(item.getColour()), getG(item.getColour()), getB(item.getColour())));
    }

    @Override
    public void companyColorChanged(int alpha, int r, int g, int b) {
        service.changeCompanyColor(getSelectedCompanyId(), alpha, r, g, b);
    }

    // TODO: PEREKATAT'
    private int getSelectedCompanyId() {
        if (!getCompaniesGrid().getSelectedItems().isEmpty())
            return getCompaniesGrid().getSelectedItems().iterator().next().getId();
        return 0;
    }
}
