package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rest.dao.entity.RestaurantPoll;

public class RestaurantPollDAO extends AbstractDAO<RestaurantPoll> implements IRestaurantPollDAO {

    @Inject
    public RestaurantPollDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public RestaurantPoll createOrUpdate(RestaurantPoll restaurantPoll) throws LOException {
        try{
            return persist(restaurantPoll);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }
}
