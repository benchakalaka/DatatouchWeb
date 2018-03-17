package com.datascope.bounded.contexts.user.service.interfaces.callbacks;


import com.datascope.bounded.contexts.user.domain.User;

public interface GetUsersCallback {
    void usersNotFound();
    void usersFound(User.List users);
}
