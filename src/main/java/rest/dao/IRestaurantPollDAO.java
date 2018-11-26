package rest.dao;

import exception.LOException;
import rest.dao.entity.RestaurantPoll;

public interface IRestaurantPollDAO {
    RestaurantPoll createOrUpdate(RestaurantPoll restaurantPoll) throws LOException;
}
