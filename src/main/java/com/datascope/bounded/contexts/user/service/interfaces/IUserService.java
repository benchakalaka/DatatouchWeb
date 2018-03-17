package com.datascope.bounded.contexts.user.service.interfaces;

import com.datascope.bounded.contexts.user.service.interfaces.callbacks.GetUsersCallback;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.LoginUserCallback;

public interface IUserService {
    void getUsers(GetUsersCallback callback);
    void login(int userId, String pin, LoginUserCallback callback);
}
