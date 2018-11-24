package rest.dao;

import rest.dao.entity.User;
import exception.LOException;
import rest.request.UserSignupRequest;

import java.util.List;

public interface IUserDAO {

    User idempotencyCheck(UserSignupRequest userSignupRequest) throws LOException;

    User createOrUpdate(User user) throws LOException;

    User findUserById(Long userId) throws LOException;

    User findUserByEmail(String email) throws LOException;

    List<User> findUsersByPattern(String pattern) throws LOException;
}
