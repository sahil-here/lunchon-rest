package rest.resources.manager;

import exception.LOException;
import rest.request.CreateEventRequest;
import rest.response.CreateUpdateEventResponse;
import rest.response.GetEventDetailsResponse;

public interface IEventManager {
    CreateUpdateEventResponse createEvent(CreateEventRequest createEventRequest) throws LOException;

    GetEventDetailsResponse getEventDetails(Long eventId) throws LOException;
}
