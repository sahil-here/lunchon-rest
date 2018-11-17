package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import lunchon.entity.EventStatusName;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.EventStatus;

import java.util.List;

public class EventStatusDAO extends AbstractDAO<EventStatus> implements IEventStatusDAO {

    @Inject
    public EventStatusDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public EventStatus idempotencyCheck(Long eventId, EventStatusName statusName) throws LOException {
        try{
            Query query = namedQuery("idempotencyCheckForEventStatus");
            query.setParameter("event_id",eventId);
            query.setParameter("status",statusName);
            return (EventStatus) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public EventStatus createOrUpdate(EventStatus eventStatus) throws LOException {
        try{
            return persist(eventStatus);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public List<EventStatus> getEventStatusForAnEvent(Long eventId) throws LOException{
        try{
            Query query = namedQuery("findStatusByEventId");
            query.setParameter("event_id",eventId);
            return (List<EventStatus>) query.list();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }
}
