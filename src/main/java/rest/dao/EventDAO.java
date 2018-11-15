package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.Event;
import rest.request.CreateUpdateEventRequest;

public class EventDAO extends AbstractDAO<Event> implements IEventDAO {

    @Inject
    public EventDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Event idempotencyCheck(CreateUpdateEventRequest createUpdateEventRequest) throws LOException {
        try{
            Query query = namedQuery("idempotencyCheckForEventCreation");
            query.setParameter("name", createUpdateEventRequest.getName());
            return (Event) query.uniqueResult();
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Event createOrUpdate(Event event) throws LOException {
        try{
            return persist(event);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Event findEventById(Long id) throws LOException{
        try{
            Query query = namedQuery("findEventById");
            query.setParameter("id", id);
            return (Event) query.uniqueResult();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Event findEventByName(String name) throws LOException{
        try{
            Query query = namedQuery("findEventByName");
            query.setParameter("name", name);
            return (Event) query.uniqueResult();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

}
