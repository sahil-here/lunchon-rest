package rest.resources;

import authentication.BasicAuth;
import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exception.LOException;
import rest.request.UserLoginRequest;
import rest.request.UserSignupRequest;
import rest.resources.manager.ILoginManager;
import rest.response.GetUserDetailsResponse;
import util.BeanValidator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/lunchon")
@Api(value = "login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private static final Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @Inject
    protected ILoginManager loginManager;

    @Inject
    protected BasicAuth basicauth;

    @POST
    @Timed
    @Path("/user/signup")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "User Signup Request", response = GetUserDetailsResponse.class)
    public Response userSignup(
            @ApiParam(value = "User Signup Request ", required = true) UserSignupRequest userSignupRequest) throws LOException {
        BeanValidator.validate(userSignupRequest);
        logger.info("User Signup Request: " + userSignupRequest);
        GetUserDetailsResponse response = loginManager.userSignup(userSignupRequest);
        return Response.ok().entity(response).build();
    }

    @POST
    @Timed
    @Path("/user/login")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "User Login Request", response = GetUserDetailsResponse.class)
    public Response userLogin(
            @ApiParam(value = "User Login Request ", required = true) UserLoginRequest userLoginRequest) throws LOException {
        BeanValidator.validate(userLoginRequest);
        logger.info("User Login Request: " + userLoginRequest);
        GetUserDetailsResponse response = loginManager.userLogin(userLoginRequest);
        response.setToken(basicauth.generateToken(response.getId()));
        return Response.ok().entity(response).build();
    }

}
