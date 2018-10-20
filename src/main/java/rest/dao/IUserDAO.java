package rest.dao;

import rest.dao.entity.User;
import exception.LOException;
import rest.request.UserSignupRequest;

public interface IUserDAO {

    User idempotencyCheck(UserSignupRequest userSignupRequest) throws LOException;

    User createOrUpdate(User user) throws LOException;

    User findUserById(Long userId) throws LOException;

    User findUserByEmail(String email) throws LOException;
}
