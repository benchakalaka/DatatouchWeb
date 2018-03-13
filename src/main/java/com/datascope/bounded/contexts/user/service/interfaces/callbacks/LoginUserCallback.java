package com.datascope.bounded.contexts.user.service.interfaces.callbacks;


import com.datascope.bounded.contexts.user.domain.User;

public interface LoginUserCallback {
    void loginFailed();
    void loginSuccess(User user);
}
