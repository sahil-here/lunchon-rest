package rest.resources;

import authentication.BasicAuth;
import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import exception.LOErrorMessage;
import exception.LOException;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.dao.entity.Cuisine;
import rest.dao.entity.Event;
import rest.request.CreateUpdateEventRequest;
import rest.request.VoteRequest;
import rest.resources.manager.IEventManager;
import rest.resources.manager.IUserManager;
import rest.response.CreateUpdateEventResponse;
import rest.response.GetEventDetailsResponse;
import util.BeanValidator;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/lunchon")
@Api(value = "event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

    private static final Logger logger = LoggerFactory.getLogger(EventResource.class);

    @Inject
    protected IEventManager eventManager;

    @Inject
    protected IUserManager userManager;

    @Inject
    protected HttpClient httpClient;

    @POST
    @Timed
    @Path("/event")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create New Event Request", response = CreateUpdateEventResponse.class)
    public Response createEvent(
            @ApiParam(required = true) @NotNull @HeaderParam("Auth") String token,
            @ApiParam(value = "Create New Event Request ", required = true) CreateUpdateEventRequest createUpdateEventRequest) throws LOException {
        BasicAuth.validateToken(token);
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
    public Response getEventDetails(
            @ApiParam(required = true) @NotNull @HeaderParam("Auth") String token,
            @ApiParam(value = "Service Request for fetching details of an event", required = true)@NotNull @QueryParam("eventId") Long eventId) throws LOException{
        BasicAuth.validateToken(token);
        Long userId = BasicAuth.getUserId(token);
        logger.info("Get details of an event with eventId : " + eventId);
        for(GetEventDetailsResponse event: userManager.getUserDetails(userId).getEvents()){
            if(event.getId().equals(eventId)){
                return Response.ok().entity(event).build();
            }
        }
        throw new LOException(400,"User with userId "+userId+" cannot get details of event with eventId "+ eventId);
    }

    @POST
    @Timed
    @Path("/event/{eventId}")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update Event Request", response = CreateUpdateEventResponse.class)
    public Response updateEvent(
            @ApiParam(required = true) @NotNull @HeaderParam("Auth") String token,
            @ApiParam(value = "Update Event Request ", required = true) CreateUpdateEventRequest createUpdateEventRequest, @NotNull @PathParam("eventId") Long eventId) throws LOException {
        BasicAuth.validateToken(token);
        BeanValidator.validate(createUpdateEventRequest);
        Long userId = BasicAuth.getUserId(token);
        for(GetEventDetailsResponse event: userManager.getUserDetails(userId).getEvents()){
            if(event.getId().equals(eventId)){
                logger.info("Update Event Request: " + createUpdateEventRequest);
                CreateUpdateEventResponse response = eventManager.createOrUpdateEvent(createUpdateEventRequest,eventId);
                return Response.ok().entity(response).build();
            }
        }
        throw new LOException(400,"User with userId "+userId+" cannot update event with eventId "+ eventId);
    }

    @GET
    @Timed
    @Path("/cuisines/all")
    @UnitOfWork
    @ApiOperation(value = "Get All Cuisines ", response = Cuisine.class, responseContainer="List")
    public Response getUserDetails(@ApiParam(required = true) @NotNull @HeaderParam("Auth") String token) throws LOException{
        BasicAuth.validateToken(token);
        logger.info("Get all cuisines.");
        List<Cuisine> response = eventManager.getAllCuisines();
        return Response.ok().entity(response).build();
    }

    @POST
    @Timed
    @Path("/event/vote")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Vote Request", response = String.class)
    public Response vote(
            @ApiParam(required = true) @NotNull @HeaderParam("Auth") String token,
            @ApiParam(value = "Update Event Request ", required = true) VoteRequest voteRequest) throws LOException {
        BasicAuth.validateToken(token);
        Long userId = BasicAuth.getUserId(token);
        voteRequest.setUserId(userId);
        BeanValidator.validate(voteRequest);
        logger.info("Vote Request: " + voteRequest);
        eventManager.vote(voteRequest);
        LOErrorMessage successResponse = new LOErrorMessage();
        successResponse.setCode("200");
        successResponse.setMessage("OK");
        return Response.ok().entity(successResponse).build();
    }

    @GET
    @Timed
    @Path("/event/yelp")
    @UnitOfWork
    @ApiOperation(value = "Vote Request", response = String.class)
    public Response queryYelp(
            @ApiParam(value = "Yelp query string", required = true)@NotNull @QueryParam("query") String queryUrl) throws LOException {

        URI uri = URI.create(queryUrl);
        HttpGet httpGet = new HttpGet(uri);

        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("Authorization", "Bearer JLADzFnvJ6DFPZrD4KAR4SES9Xl9z3glmrTLGKrE0rpZzlQ7Q_GYyZcegh4SxASAi8RG710lt584LH-JLxFK14KmO8_DfCjDENU9-vIyELmhgkhmDNPSSRq0Pdn0W3Yx"));
        headerList.add(new BasicHeader("Content-Type","text/html"));
        httpGet.setHeaders(headerList.toArray(new Header[headerList.size()]));
        logger.info("Making " + httpGet.getMethod() + " Call: API=>" + uri);
        try{
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            logger.info("ResponseCode: " + responseCode);
            if (responseCode != HttpStatus.SC_OK) {
                logger.error("Call failed with status code : " + responseCode +
                        " and status message : " + httpResponse.getStatusLine().getReasonPhrase());
                logger.info("Response : " + EntityUtils.toString(httpResponse.getEntity()));
                throw new LOException(responseCode, httpResponse.getStatusLine().getReasonPhrase());
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            String responseBody = EntityUtils.toString(httpEntity);
            logger.info("ResponseBody: " + responseBody);
            return Response.ok().entity(responseBody).build();
        }catch (IOException ex){
            throw new LOException(500,"IOException while calling Yelp API");
        }
    }


}
