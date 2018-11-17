package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.Time;

public class TimeDAO extends AbstractDAO<Time> implements ITimeDAO {

    @Inject
    public TimeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Time idempotencyCheck(rest.request.Time time) throws LOException {
        try{
            Query query = namedQuery("idempotencyCheckForTime");
            query.setParameter("start_time", time.getStartTime());
            query.setParameter("end_time", time.getEndTime());
            return (Time) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Time createOrUpdate(Time time) throws LOException {
        try{
            return persist(time);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Time findTimeById(Long id) throws LOException{
        try{
            Query query = namedQuery("findTimeById");
            query.setParameter("id", id);
            return (Time) query.uniqueResult();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }


}
