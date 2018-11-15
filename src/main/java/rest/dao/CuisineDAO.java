package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.Cuisine;

import java.util.List;

public class CuisineDAO extends AbstractDAO<Cuisine> implements ICuisineDAO {

    @Inject
    public CuisineDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Cuisine findCuisineById(Long id) throws LOException{
        try{
            Query query = namedQuery("findCuisineById");
            query.setParameter("id", id);
            return (Cuisine) query.uniqueResult();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public Cuisine findCuisineByName(String name) throws LOException{
        try{
            Query query = namedQuery("findCuisineById");
            query.setParameter("name", name);
            return (Cuisine) query.uniqueResult();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public List<Cuisine> findAllCuisine() throws LOException{
        try{
            Query query = namedQuery("findAllCuisine");
            return (List<Cuisine>) query.list();
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }
}
