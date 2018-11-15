package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.Restaurant;

public class RestaurantDAO extends AbstractDAO<Restaurant> implements IRestaurantDAO {

    @Inject
    public RestaurantDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Restaurant idempotencyCheck(rest.request.Restaurant restaurant) throws LOException {
        try{
            Query query = namedQuery("idempotencyCheckForRestaurant");
            query.setParameter("id", restaurant.getId());
            return (Restaurant) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Restaurant createOrUpdate(Restaurant restaurant) throws LOException {
        try{
            return persist(restaurant);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Restaurant findRestautantById(String id) throws LOException{
        try{
            Query query = namedQuery("findRestaurantById");
            query.setParameter("id", id);
            return (Restaurant) query.uniqueResult();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }


}
