package rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CreateEventRequest {

    @NotEmpty(message = "field is missing")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "field is missing")
    @JsonProperty("email")
    private String organiserEmail;

    @NotEmpty(message = "field is missing")
    @JsonProperty("contact")
    private List<String> participantsEmail;

    @NotEmpty(message = "field is missing")
    @JsonProperty("password")
    private String password;


}
