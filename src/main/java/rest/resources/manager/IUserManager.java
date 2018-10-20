package rest.resources.manager;

import exception.LOException;
import rest.response.GetUserDetailsResponse;

public interface IUserManager {

    GetUserDetailsResponse getUserDetails(Long userId) throws LOException;
}
