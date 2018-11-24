package rest.resources.manager;

import exception.LOException;
import rest.response.GetMinUserDetailsResponse;
import rest.response.GetUserDetailsResponse;

import java.util.List;

public interface IUserManager {

    GetUserDetailsResponse getUserDetails(Long userId) throws LOException;

    List<GetMinUserDetailsResponse> getUsersByPattern(String pattern) throws LOException;
}
