package com.datascope.bounded.contexts.user.service;

import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.core.services.concrete.SuperRestService;
import com.datascope.bounded.contexts.core.services.concrete.UnirestClient;
import com.datascope.bounded.contexts.user.domain.User;
import com.datascope.bounded.contexts.user.service.interfaces.IUserService;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.GetUsersCallback;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.LoginUserCallback;
import com.datascope.bounded.contexts.user.service.requests.LoginUserRequest;
import com.datascope.bounded.contexts.user.service.requests.UnassignPinRequest;
import com.datascope.bounded.contexts.user.service.requests.UpdateUserPinRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class UserService extends SuperRestService implements IUserService {

    private static final String GET_USERS = "User/GetAllUsersWithPins";
    private static final String LOGIN = "UserSettings/LoginUser";
    private static final String UPDATE_USER_PIN = "User/UpdateUserPin";
    private static final String UNASSIGN_PIN = "User/UnassignPin";


    public UserService(IRestClient rest) {
        super(rest);
    }

    private int getSiteId() {
        return ((UnirestClient)rest).getSiteId();
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
        User user = rest.post(User.class, LOGIN, LoginUserRequest.create(userId, pin, getSiteId()));

        if (null == user) {
            callback.loginFailed();
        } else {
            callback.loginSuccess(user);
        }
    }

    @Override
    public void updateUserPin(int userId, String userPin) {
        UpdateUserPinRequest request = new UpdateUserPinRequest(userId, userPin, getSiteId());
        rest.post(Integer.class, UPDATE_USER_PIN, request);
    }

    @Override
    public void unassignPin(int userId) {
        UnassignPinRequest request = new UnassignPinRequest(userId, getSiteId());
        rest.post(Integer.class, UNASSIGN_PIN, request);
    }
}
