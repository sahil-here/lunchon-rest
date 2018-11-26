package rest.resources;

import authentication.BasicAuth;
import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import exception.LOErrorCode;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exception.LOException;
import rest.resources.manager.IUserManager;
import rest.response.GetMinUserDetailsResponse;
import rest.response.GetUserDetailsResponse;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/lunchon")
@Api(value = "user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Inject
    protected IUserManager userManager;

    @GET
    @Timed
    @Path("/user")
    @UnitOfWork
    @ApiOperation(value = "Get details of a user ", response = GetUserDetailsResponse.class)
    public Response getUserDetails(
            @ApiParam(required = true) @NotNull @HeaderParam("Auth") String token,
            @ApiParam(value = "Service Request for fetching details of a user", required = true)@NotNull @QueryParam("userId") Long userId) throws LOException{
        BasicAuth.validateToken(token);
        Long userIdFromToken = BasicAuth.getUserId(token);
        if(userIdFromToken.equals(userId)){
            logger.info("Get details of a user with userId : " + userId);
            GetUserDetailsResponse response = userManager.getUserDetails(userId);
            return Response.ok().entity(response).build();
        }else{
            throw new LOException(400, LOErrorCode.TOKEN_MISMATCH.getName());
        }
    }

    @GET
    @Timed
    @Path("/getusers")
    @UnitOfWork
    @ApiOperation(value = "Get users starting with given pattern ", response = GetMinUserDetailsResponse.class, responseContainer="List")
    public Response getUsers(
            @ApiParam(required = true) @NotNull @HeaderParam("Auth") String token,
            @ApiParam(value = "Service Request for getting users starting with given pattern", required = true)@NotNull @QueryParam("pattern") String pattern) throws LOException{
        BasicAuth.validateToken(token);
        logger.info("Get users starting with pattern : " + pattern);
        List<GetMinUserDetailsResponse> response = userManager.getUsersByPattern(pattern);
        return Response.ok().entity(response).build();
    }


}
