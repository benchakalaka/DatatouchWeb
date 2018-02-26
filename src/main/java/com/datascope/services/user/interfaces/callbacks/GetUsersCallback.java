package com.datascope.services.user.interfaces.callbacks;

import com.datascope.domain.user.User;

public interface GetUsersCallback {
    void usersNotFound();
    void usersFound(User.List users);
}
