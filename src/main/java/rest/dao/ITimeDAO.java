package rest.dao;

import exception.LOException;
import rest.dao.entity.Time;

public interface ITimeDAO {
    Time idempotencyCheck(rest.request.Time time) throws LOException;

    Time createOrUpdate(Time time) throws LOException;

    Time findTimeById(Long id) throws LOException;
}
