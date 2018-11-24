package rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import rest.dao.entity.Cuisine;
import rest.request.Restaurant;
import rest.request.Time;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VoteResponse {

    @NotNull(message = "field is missing")
    @JsonProperty("voter_id")
    private Long userId;

    @NotNull(message = "field is missing")
    @JsonProperty("event_id")
    private Long eventId;

    @JsonProperty("time")
    private Time time;

    @JsonProperty("cuisine")
    private Cuisine cuisine;

    @JsonProperty("restaurant")
    private Restaurant restaurant;

    @JsonProperty("is_success")
    private boolean isSuccess;

    private String message;


}
