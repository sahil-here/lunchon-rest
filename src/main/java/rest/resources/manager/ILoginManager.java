package rest.resources.manager;

import exception.LOException;
import rest.request.UserLoginRequest;
import rest.request.UserSignupRequest;
import rest.response.GetUserDetailsResponse;

public interface ILoginManager {
    GetUserDetailsResponse userSignup(UserSignupRequest userSignupRequest) throws LOException;
    GetUserDetailsResponse userLogin(UserLoginRequest userLoginRequest) throws LOException;
}
