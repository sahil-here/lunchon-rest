package rest.dao;

import exception.LOException;
import rest.dao.entity.Cuisine;

import java.util.List;

public interface ICuisineDAO {
    Cuisine findCuisineById(Long id) throws LOException;

    Cuisine findCuisineByName(String name) throws LOException;

    List<Cuisine> findAllCuisine() throws LOException;
}
