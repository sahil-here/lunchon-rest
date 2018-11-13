package rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import exception.LOException;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.request.UserSignupRequest;
import rest.response.GetUserDetailsResponse;
import util.BeanValidator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("lo")
@Api(value = "event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

    private static final Logger logger = LoggerFactory.getLogger(EventResource.class);

  /*  @POST
    @Timed
    @Path("/event")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create New Event Request", response = GetUserDetailsResponse.class)
    public Response userSignup(
            @ApiParam(value = "User Signup Request ", required = true) UserSignupRequest userSignupRequest) throws LOException {
        BeanValidator.validate(userSignupRequest);
        logger.info("User Signup Request: " + userSignupRequest);
        GetUserDetailsResponse response = loginManager.userSignup(userSignupRequest);
        return Response.ok().entity(response).build();
    }*/
}
