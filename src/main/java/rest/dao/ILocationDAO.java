package rest.dao;

import exception.LOException;
import rest.dao.entity.Location;

public interface ILocationDAO {
    Location idempotencyCheck(rest.request.Location location) throws LOException;

    Location createOrUpdate(Location location) throws LOException;

    Location findLocationById(Long id) throws LOException;
}
