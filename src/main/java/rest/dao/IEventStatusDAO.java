package rest.dao;

import exception.LOException;
import lunchon.entity.EventStatusName;
import rest.dao.entity.EventStatus;

import java.util.List;

public interface IEventStatusDAO {
    EventStatus idempotencyCheck(Long eventId, EventStatusName statusName) throws LOException;

    EventStatus createOrUpdate(EventStatus eventStatus) throws LOException;

    List<EventStatus> getEventStatusForAnEvent(Long eventId) throws LOException;
}
