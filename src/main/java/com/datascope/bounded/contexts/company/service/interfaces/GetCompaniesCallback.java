package com.datascope.bounded.contexts.company.service.interfaces;

import com.datascope.bounded.contexts.company.domain.Company;

public interface GetCompaniesCallback {
    void companiesFound(Company.List companies);

    void companiesNotFound();
}
