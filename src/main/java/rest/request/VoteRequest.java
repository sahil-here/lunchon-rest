package rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VoteRequest {

    @JsonProperty("voter_id")
    private Long userId;

    @NotNull(message = "field is missing")
    @JsonProperty("event_id")
    private Long eventId;

    @JsonProperty("time_id")
    private Long timeChoiceId;

    @JsonProperty("cuisine_id")
    private Long cuisineChoiceId;

    @JsonProperty("restaurant_id")
    private String restaurantChoiceId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTimeChoiceId() {
        return timeChoiceId;
    }

    public void setTimeChoiceId(Long timeChoiceId) {
        this.timeChoiceId = timeChoiceId;
    }

    public Long getCuisineChoiceId() {
        return cuisineChoiceId;
    }

    public void setCuisineChoiceId(Long cuisineChoiceId) {
        this.cuisineChoiceId = cuisineChoiceId;
    }

    public String getRestaurantChoiceId() {
        return restaurantChoiceId;
    }

    public void setRestaurantChoiceId(String restaurantChoiceId) {
        this.restaurantChoiceId = restaurantChoiceId;
    }
}
