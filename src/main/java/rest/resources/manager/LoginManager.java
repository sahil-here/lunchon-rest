package rest.resources.manager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.dao.IUserDAO;
import rest.dao.entity.User;
import exception.LOErrorCode;
import exception.LOException;
import rest.request.UserLoginRequest;
import rest.request.UserSignupRequest;
import rest.response.GetUserDetailsResponse;
import util.PasswordHash;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginManager implements ILoginManager {

    private static final Logger logger = LoggerFactory.getLogger(LoginManager.class);

    @Inject
    protected IUserDAO userDAO;

    @Override
    public GetUserDetailsResponse userSignup(UserSignupRequest userSignupRequest) throws LOException {
        User user = new User();
        GetUserDetailsResponse userSignupResponse = new GetUserDetailsResponse();
        try{
            User existing = userDAO.idempotencyCheck(userSignupRequest);
            if(existing==null){
                user.setName(userSignupRequest.getName());
                user.setContact(userSignupRequest.getContact());
                user.setEmail(userSignupRequest.getEmail());
                user.setPassword(PasswordHash.createHash(userSignupRequest.getPassword()));
                User newUser = userDAO.createOrUpdate(user);
                userSignupResponse.setEmail(newUser.getEmail());
                userSignupResponse.setId(newUser.getId());
                userSignupResponse.setContact(newUser.getContact());
                userSignupResponse.setName(newUser.getName());
                return userSignupResponse;
            }else{
                throw new LOException(400, LOErrorCode.USER_ALREADY_PRESENT.getName());
            }
        }catch(NoSuchAlgorithmException | InvalidKeySpecException ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public GetUserDetailsResponse userLogin(UserLoginRequest userLoginRequest) throws LOException{
        try{
            User existing = userDAO.findUserByEmail(userLoginRequest.getEmail());
            String passwordHash = existing.getPassword();
            if(!StringUtils.isEmpty(passwordHash)){
                if(PasswordHash.validatePassword(userLoginRequest.getPassword(), passwordHash)){
                    GetUserDetailsResponse userDetailsResponse = new GetUserDetailsResponse();
                    userDetailsResponse.setId(existing.getId());
                    userDetailsResponse.setName(existing.getName());
                    userDetailsResponse.setEmail(existing.getEmail());
                    userDetailsResponse.setContact(existing.getContact());
                    return userDetailsResponse;
                }else{
                    throw new LOException(400, LOErrorCode.INVALID_USER_CREDENTIALS.getName());
                }
            }else{
                throw new LOException(500, LOErrorCode.PASSWORD_ERROR.getName());
            }
        }catch(NoSuchAlgorithmException | InvalidKeySpecException ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }
}
