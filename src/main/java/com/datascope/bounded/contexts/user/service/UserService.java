package com.datascope.bounded.contexts.user.service;

import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.core.services.concrete.SuperRestService;
import com.datascope.bounded.contexts.user.service.interfaces.IUserService;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.GetUsersCallback;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.LoginUserCallback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class UserService extends SuperRestService implements IUserService {

    private static final String GET_USERS = "User/GetAllUsersWithPins";
    private static final String LOGIN = "UserSettings/LoginUser";



    public UserService(IRestClient rest) {
        super(rest);
    }

    @Override
    public void getUsers(GetUsersCallback callback) {
//        User.List users = rest.post(User.List.class, GET_USERS, GetUsersRequest.create(rest.getSiteId()));
//
//
//        if  (CollectionUtils.isNotEmpty(users))
//            callback.usersFound(users);
//        else
//            callback.usersNotFound();
    }

    public void login(int userId, String pin, LoginUserCallback callback) {
//        User user = rest.post(User.class, LOGIN, LoginUserRequest.create(userId, pin, rest.getSiteId()));
//
//        if (null == user)
//            callback.loginFailed();
//        else
//            callback.loginSuccess(user);
    }
}
