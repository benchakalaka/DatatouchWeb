package com.datascope.services.user.interfaces.callbacks;

import com.datascope.domain.user.User;

public interface LoginUserCallback {
    void loginFailed();
    void loginSuccess(User user);
}
