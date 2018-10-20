package exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class LOExceptionMapper implements ExceptionMapper<LOException>{
    @Override
    public Response toResponse(LOException e) {
        return Response.status(e.getCode()).entity(new LOErrorMessage(e.getCode().toString(), e.getMessage())).build();
    }
}
