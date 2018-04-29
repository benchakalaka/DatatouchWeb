package com.datascope.ui.company.callbacks;

import com.datascope.ui.company.elements.CompanyGridItem;

public interface CompanyColorChangedCallback {
    void companyColorChanged(CompanyGridItem item, int alpha, int r, int g, int b);
}
