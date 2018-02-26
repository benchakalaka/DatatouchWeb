package com.datascope.services.user;

import com.datascope.domain.user.User;
import com.datascope.services.core.Rest;
import com.datascope.services.user.interfaces.IUserService;
import com.datascope.services.user.interfaces.callbacks.GetUsersCallback;
import com.datascope.services.user.interfaces.callbacks.LoginUserCallback;
import com.datascope.services.user.requests.GetUsersRequest;
import com.datascope.services.user.requests.LoginUserRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class UserService implements IUserService {

    private static final String GET_USERS = "User/GetAllUsersWithPins";
    private static final String LOGIN = "UserSettings/LoginUser";

    private Rest rest;

    public UserService(Rest rest) {
        this.rest = rest;
    }

    @Override
    public void getUsers(GetUsersCallback callback) {
        User.List users = rest.execute(User.List.class, GET_USERS, GetUsersRequest.create(rest.getSiteId()));

        // TODO: isnullOrEmpty
        if  (null == users || users.isEmpty())
            callback.usersNotFound();
        else
            callback.usersFound(users);
    }

    public void login(int userId, String pin, LoginUserCallback callback) {
        User user = rest.execute(User.class, LOGIN, LoginUserRequest.create(userId, pin, rest.getSiteId()));

        if (null == user)
            callback.loginFailed();
        else
            callback.loginSuccess(user);
    }
}
