package com.datascope.ui.usermanagement.callbacks;

public interface IChangeUserPinCallback {
    void onUserPinUpdate(int userId, String newPin);
    void onUserPinUnassign(int userId);
}
