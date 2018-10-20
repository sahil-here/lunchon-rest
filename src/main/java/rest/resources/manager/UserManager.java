package rest.resources.manager;

import org.apache.commons.beanutils.BeanUtils;
import rest.dao.IUserDAO;
import rest.dao.entity.User;
import exception.LOErrorCode;
import exception.LOException;
import rest.response.GetUserDetailsResponse;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

public class UserManager implements IUserManager {

    @Inject
    protected IUserDAO userDAO;

    @Override
    public GetUserDetailsResponse getUserDetails(Long userId) throws LOException{
            User user = userDAO.findUserById(userId);
            if(user!=null){
                GetUserDetailsResponse userDetailsResponse = new GetUserDetailsResponse();
                userDetailsResponse.setId(user.getId());
                userDetailsResponse.setName(user.getName());
                userDetailsResponse.setContact(user.getContact());
                userDetailsResponse.setEmail(user.getEmail());
                return userDetailsResponse;
            }else{
                throw new LOException(400, LOErrorCode.USER_NOT_FOUND.getName());
            }
    }
}
