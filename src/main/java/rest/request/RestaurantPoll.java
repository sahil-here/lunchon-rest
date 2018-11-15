package rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RestaurantPoll {

    @NotEmpty(message = "field is missing")
    @JsonProperty("restaurant_id")
    private String restaurantId;

    @NotEmpty(message = "field is missing")
    @JsonProperty("restaurant_name")
    private String restaurantName;

    @NotEmpty(message = "field is missing")
    @JsonProperty("voter_id")
    private Long voterId;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
