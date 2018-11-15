package rest.dao;

import exception.LOException;
import rest.dao.entity.Event;
import rest.request.CreateEventRequest;

public interface IEventDAO {
    Event idempotencyCheck(CreateEventRequest createEventRequest) throws LOException;

    Event createOrUpdate(Event event) throws LOException;

    Event findEventById(Long id) throws LOException;

    Event findEventByName(String name) throws LOException;
}
