package rest.dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.User;
import exception.LOErrorCode;
import exception.LOException;
import rest.request.UserSignupRequest;

public class UserDAO extends AbstractDAO<User> implements IUserDAO {

    @Inject
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User idempotencyCheck(UserSignupRequest userSignupRequest) throws LOException {
        try{
            Query query = namedQuery("idempotencyCheckForUserCreation");
            query.setParameter("email", userSignupRequest.getEmail());
            return (User) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public User createOrUpdate(User user) throws LOException {
        try{
            return persist(user);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public User findUserById(Long userId) throws LOException {
        try{
            Query query = namedQuery("findUserById");
            query.setParameter("id", userId);
            return (User) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public User findUserByEmail(String email) throws LOException {
        try{
            Query query = namedQuery("findUserByEmail");
            query.setParameter("email", email);
            return (User) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }


}
