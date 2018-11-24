package rest.resources.manager;

import org.apache.commons.beanutils.BeanUtils;
import rest.dao.IUserDAO;
import rest.dao.entity.Event;
import rest.dao.entity.User;
import exception.LOErrorCode;
import exception.LOException;
import rest.response.GetEventDetailsResponse;
import rest.response.GetMinUserDetailsResponse;
import rest.response.GetUserDetailsResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserManager {

    @Inject
    protected IUserDAO userDAO;

    @Inject
    protected IEventManager eventManager;

    @Override
    public GetUserDetailsResponse getUserDetails(Long userId) throws LOException{
            User user = userDAO.findUserById(userId);
            if(user!=null){
                GetUserDetailsResponse userDetailsResponse = new GetUserDetailsResponse();
                userDetailsResponse.setId(user.getId());
                userDetailsResponse.setName(user.getName());
                userDetailsResponse.setContact(user.getContact());
                userDetailsResponse.setEmail(user.getEmail());
                List<GetEventDetailsResponse> events = new ArrayList<>();
                for(Event event: user.getEvents()){
                    events.add(eventManager.populateEventDetails(event));
                }
                userDetailsResponse.setEvents(events);
                return userDetailsResponse;
            }else{
                throw new LOException(400, LOErrorCode.USER_NOT_FOUND.getName());
            }
    }

    @Override
    public List<GetMinUserDetailsResponse> getUsersByPattern(String pattern) throws LOException{
        List<User> users = userDAO.findUsersByPattern(pattern);
        List<GetMinUserDetailsResponse> responses = new ArrayList<>();
        for(User user: users){
            GetMinUserDetailsResponse response = new GetMinUserDetailsResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setContact(user.getContact());
            responses.add(response);
        }
        return responses;
    }
}
