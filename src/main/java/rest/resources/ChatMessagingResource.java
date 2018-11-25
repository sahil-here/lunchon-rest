package rest.resources;


import authentication.BasicAuth;
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
    private static final Logger logger = LoggerFactory.getLogger(ChatMessagingResource.class);

    @Inject
    protected IChatManager chatManager;

    @Inject
    protected BasicAuth basicauth;

    @GET
    @Timed
    @Path("/chat")
    @UnitOfWork
    @ApiOperation(value = "Get persisted chat messages for an event", response = GetChatMessagesResponse.class)
    public Response getChatMessages(
            @ApiParam(required = true) @NotNull @HeaderParam("Auth") String token,
            @ApiParam(required = true) @NotNull @QueryParam("event_id") Long eventId,
            @ApiParam(required = true) @NotNull @QueryParam("user_id") Long userId,
            @QueryParam("last_seen") @DefaultValue("253399043869000") Long lastSeen,
            @QueryParam("limit") @DefaultValue("100") int limit) throws LOException {
                basicauth.validateToken(token, userId);
                Timestamp timestamp = new Timestamp(lastSeen);  // milli-second epoch
                logger.info("Fetch messages eventId : " + eventId + ", timeStamp : " + timestamp.toString() + ", limit : " + limit);
                GetChatMessagesResponse response = chatManager.getChatMessages(userId, eventId, timestamp, limit);
                return Response.ok().entity(response.toString()).build();
            }
}
