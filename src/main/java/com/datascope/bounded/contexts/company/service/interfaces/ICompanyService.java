package com.datascope.bounded.contexts.company.service.interfaces;

public interface ICompanyService {

    void getCompanies(GetCompaniesCallback callback);

    void changeCompanyColor(int companyId, int alpha, int r, int g, int b);
}
