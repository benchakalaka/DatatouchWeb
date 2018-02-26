package com.datascope.services.user.interfaces;

import com.datascope.services.user.interfaces.callbacks.GetUsersCallback;
import com.datascope.services.user.interfaces.callbacks.LoginUserCallback;

public interface IUserService {
    void getUsers(GetUsersCallback callback);
    void login(int userId, String pin, LoginUserCallback callback);
}
