package rest.dao;

import exception.LOException;
import rest.dao.entity.Restaurant;

public interface IRestaurantDAO {
    Restaurant idempotencyCheck(rest.request.Restaurant restaurant) throws LOException;

    Restaurant createOrUpdate(Restaurant restaurant) throws LOException;

    Restaurant findRestautantById(String id) throws LOException;
}
