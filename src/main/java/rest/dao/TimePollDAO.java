package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rest.dao.entity.TimePoll;

public class TimePollDAO extends AbstractDAO<TimePoll> implements ITimePollDAO {

    @Inject
    public TimePollDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public TimePoll createOrUpdate(TimePoll timePoll) throws LOException {
        try{
            return persist(timePoll);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

}
