package com.user_service.user_service.interfaces;

import com.user_service.user_service.models.GetSessionToken;
import com.user_service.user_service.models.Response;
import com.user_service.user_service.models.userLoginParams;
import com.user_service.user_service.models.userRegister;

public interface IUserAuthentication {
    public Response<Integer> registerUser(userRegister userRegister);

    public Response<Integer> loginUser(userLoginParams userLogin);
    public Response<String> getSessionToken(GetSessionToken getSessionToken);
    // public Response<Boolean> validateSessionToken(String token);
}
