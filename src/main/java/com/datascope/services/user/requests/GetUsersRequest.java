package com.datascope.services.user.requests;

import com.datascope.services.core.BaseRequestView;

public class GetUsersRequest extends BaseRequestView {



    public GetUsersRequest() {
    }



    public static GetUsersRequest create(int siteId){
        GetUsersRequest view = new GetUsersRequest();
        view.setSiteId(siteId);
        return view;
    }
}
