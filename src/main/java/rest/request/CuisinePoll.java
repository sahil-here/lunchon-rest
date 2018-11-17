package rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CuisinePoll {

    @NotEmpty(message = "field is missing")
    @JsonProperty("cuisine_id")
    private Long cuisineId;

    @NotEmpty(message = "field is missing")
    @JsonProperty("cuisine_name")
    private String cuisineName;

    @NotEmpty(message = "field is missing")
    @JsonProperty("voter_id")
    private Long voterId;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    public Long getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(Long cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
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
