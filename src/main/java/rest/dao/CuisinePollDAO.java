package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.CuisinePoll;
import rest.dao.entity.Time;

import java.util.List;

public class CuisinePollDAO extends AbstractDAO<CuisinePoll> implements ICuisinePollDAO {

    @Inject
    public CuisinePollDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public CuisinePoll createOrUpdate(CuisinePoll cuisinePoll) throws LOException {
        try{
            return persist(cuisinePoll);
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

}
