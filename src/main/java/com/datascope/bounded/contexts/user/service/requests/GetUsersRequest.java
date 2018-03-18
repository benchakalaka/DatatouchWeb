package com.datascope.bounded.contexts.user.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;

public class GetUsersRequest extends SuperRequestView {



    public GetUsersRequest() {
    }



    public static GetUsersRequest create(int siteId){
        GetUsersRequest view = new GetUsersRequest();
        view.setSiteId(siteId);
        return view;
    }
}
