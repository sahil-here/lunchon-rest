package rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import exception.LOException;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.dao.entity.Cuisine;
import rest.request.CreateUpdateEventRequest;
import rest.resources.manager.IEventManager;
import rest.response.CreateUpdateEventResponse;
import rest.response.GetEventDetailsResponse;
import util.BeanValidator;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            @ApiParam(value = "Create New Event Request ", required = true) CreateUpdateEventRequest createUpdateEventRequest) throws LOException {
        BeanValidator.validate(createUpdateEventRequest);
        logger.info("Create Event Request: " + createUpdateEventRequest);
        CreateUpdateEventResponse response = eventManager.createOrUpdateEvent(createUpdateEventRequest,null);
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

    @POST
    @Timed
    @Path("/event/{eventId}")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update Event Request", response = CreateUpdateEventResponse.class)
    public Response updateEvent(
            @ApiParam(value = "Update Event Request ", required = true) CreateUpdateEventRequest createUpdateEventRequest, @NotNull @PathParam("eventId") Long eventId) throws LOException {
        BeanValidator.validate(createUpdateEventRequest);
        if(eventId==null){
            throw new LOException(400, "eventId cannot be empty");
        }
        logger.info("Update Event Request: " + createUpdateEventRequest);
        CreateUpdateEventResponse response = eventManager.createOrUpdateEvent(createUpdateEventRequest,eventId);
        return Response.ok().entity(response).build();
    }

    @GET
    @Timed
    @Path("/getAllCuisines")
    @UnitOfWork
    @ApiOperation(value = "Get All Cuisines ", response = Cuisine.class, responseContainer="List")
    public Response getUserDetails() throws LOException{
        logger.info("Get all cuisines.");
        List<Cuisine> response = eventManager.getAllCuisines();
        return Response.ok().entity(response).build();
    }

}
