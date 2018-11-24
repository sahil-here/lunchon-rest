package rest.response;

import java.util.List;

public class GetUserDetailsResponse extends GetMinUserDetailsResponse {

    private List<GetEventDetailsResponse> events;

    public List<GetEventDetailsResponse> getEvents() {
        return events;
    }

    public void setEvents(List<GetEventDetailsResponse> events) {
        this.events = events;
    }
}
