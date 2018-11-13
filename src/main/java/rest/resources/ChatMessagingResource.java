package rest.resources;


import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import exception.LOException;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.resources.manager.IChatManager;
import rest.response.GetChatMessagesResponse;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("lo")
@Api(value = "chat")
@Produces(MediaType.APPLICATION_JSON)
public class ChatMessagingResource {
    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Inject
    protected IChatManager chatManager;

    @GET
    @Timed
    @Path("/chat")
    @UnitOfWork
    @ApiOperation(value = "Get persisted chat messages for an event", response = GetChatMessagesResponse.class)
    public Response getChatMessages(
            @ApiParam(required = true) @NotNull @QueryParam("eventId") Long eventId,
            @QueryParam("timeStamp") @DefaultValue("12/30/9999 23:59:59") Timestamp timestamp,
            @QueryParam("limit") @DefaultValue("100") int limit) throws LOException {
                logger.info("Fetch messages eventId : " + eventId + ", timeStamp : " + timestamp.toString() + ", limit : " + limit);
                GetChatMessagesResponse response = chatManager.getChatMessages(eventId, timestamp, limit);
                return Response.ok().entity(response).build();
            }
}
