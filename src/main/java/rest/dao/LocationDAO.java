package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.Location;
import rest.dao.entity.Restaurant;
import rest.dao.entity.Time;

public class LocationDAO extends AbstractDAO<Location> implements ILocationDAO {

    @Inject
    public LocationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Location idempotencyCheck(rest.request.Location location) throws LOException {
        try{
            Query query = namedQuery("idempotencyCheckForLocation");
            query.setParameter("latitude",location.getLatitude());
            query.setParameter("longitude",location.getLongitude());
            query.setParameter("radius",location.getRadius());
            query.setParameter("radius_unit",location.getRadiusUnit());
            query.setParameter("zipcode",location.getZipcode());
            query.setParameter("city",location.getCity());
            query.setParameter("state",location.getState());
            query.setParameter("country",location.getCountry());
            query.setParameter("created_at",location.getCreatedAt());
            return (Location) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Location createOrUpdate(Location location) throws LOException {
        try{
            return persist(location);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Location findLocationById(Long id) throws LOException{
        try{
            Query query = namedQuery("findLocationById");
            query.setParameter("id", id);
            return (Location) query.uniqueResult();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }
}
