package rest.resources.manager;

import exception.LOException;
import rest.dao.entity.Cuisine;
import rest.dao.entity.Event;
import rest.request.CreateUpdateEventRequest;
import rest.response.CreateUpdateEventResponse;
import rest.response.GetEventDetailsResponse;

import java.util.List;

public interface IEventManager {
    CreateUpdateEventResponse createOrUpdateEvent(CreateUpdateEventRequest createUpdateEventRequest, Long eventId) throws LOException;

    GetEventDetailsResponse getEventDetails(Long eventId) throws LOException;

    List<Cuisine> getAllCuisines() throws LOException;

    GetEventDetailsResponse populateEventDetails(Event event);
}
