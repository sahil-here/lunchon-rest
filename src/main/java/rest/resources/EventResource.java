package rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import exception.LOException;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.request.CreateEventRequest;
import rest.request.Time;
import rest.resources.manager.IEventManager;
import rest.response.CreateUpdateEventResponse;
import rest.response.GetEventDetailsResponse;
import util.BeanValidator;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("lo")
@Api(value = "event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

    private static final Logger logger = LoggerFactory.getLogger(EventResource.class);

    @Inject
    protected IEventManager eventManager;

    @POST
    @Timed
    @Path("/event")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create New Event Request", response = CreateUpdateEventResponse.class)
    public Response createEvent(
            @ApiParam(value = "Create New Event Request ", required = true) CreateEventRequest createEventRequest) throws LOException {
        BeanValidator.validate(createEventRequest);
        for(Time time:createEventRequest.getTimeChoices()){
            time.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        createEventRequest.getLocation().setCreatedAt(new Timestamp(System.currentTimeMillis()));
        logger.info("Create Event Request: " + createEventRequest);
        CreateUpdateEventResponse response = eventManager.createEvent(createEventRequest);
        return Response.ok().entity(response).build();
    }

    @GET
    @Timed
    @Path("/event")
    @UnitOfWork
    @ApiOperation(value = "Get details of an event ", response = GetEventDetailsResponse.class)
    public Response getUserDetails(
            @ApiParam(value = "Service Request for fetching details of an event", required = true)@NotNull @QueryParam("eventId") Long eventId) throws LOException{
        logger.info("Get details of an event with eventId : " + eventId);
        GetEventDetailsResponse response = eventManager.getEventDetails(eventId);
        return Response.ok().entity(response).build();
    }


}
