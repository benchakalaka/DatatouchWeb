package com.datascope.bounded.contexts.company.service;

import com.datascope.application.ui.company.requests.ChangeCompanyColorRequest;
import com.datascope.bounded.contexts.company.domain.Company;
import com.datascope.bounded.contexts.company.service.interfaces.GetCompaniesCallback;
import com.datascope.bounded.contexts.company.service.interfaces.ICompanyService;
import com.datascope.core.services.IRestClient;
import com.datascope.core.services.concrete.SuperRestService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends SuperRestService implements ICompanyService {


    private static final String GET_COMPANIES = "CompanySettings/GetAllCompanies";
    private static final String EDIT_COMPANY_COLOR = "CompanySettings/EditCompanyColor";

    public CompanyService(IRestClient unirestClient) {
        super(unirestClient);
    }


    @Override
    public void getCompanies(GetCompaniesCallback callback) {
        Company.List companies = rest.post(Company.List.class, GET_COMPANIES);
        if (CollectionUtils.isNotEmpty(companies))
            callback.companiesFound(companies);
        else
            callback.companiesNotFound();
    }

    @Override
    public void changeCompanyColor(int companyId, int alpha, int r, int g, int b) {
        ChangeCompanyColorRequest request = new ChangeCompanyColorRequest(companyId, alpha, r, g, b);
        rest.post(Integer.class, EDIT_COMPANY_COLOR, request);
    }
}
