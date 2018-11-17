package rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import rest.dao.entity.Cuisine;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CreateUpdateEventRequest {

    @NotEmpty(message = "field is missing")
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @NotEmpty(message = "field is missing")
    @JsonProperty("budget")
    private String budget;

    @NotNull(message = "field is missing")
    @JsonProperty("organiser_id")
    private Long organiserId;

    @JsonProperty("participants")
    private List<Long> participantIds;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("resturant_choices")
    private List<Restaurant> resturantChoices;

    @JsonProperty("cuisine_choices")
    private List<Cuisine> cuisineChoices;

    @JsonProperty("final_resturant_id")
    private String finalRestaurantId;

    @JsonProperty("final_cuisine_id")
    private Long finalCuisineId;

    @JsonProperty("final_time_id")
    private Long finalTimeId;

    @JsonProperty("time_choices")
    private List<Time> timeChoices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Long getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(Long organiserId) {
        this.organiserId = organiserId;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Restaurant> getResturantChoices() {
        return resturantChoices;
    }

    public void setResturantChoices(List<Restaurant> resturantChoices) {
        this.resturantChoices = resturantChoices;
    }

    public List<Cuisine> getCuisineChoices() {
        return cuisineChoices;
    }

    public void setCuisineChoices(List<Cuisine> cuisineChoices) {
        this.cuisineChoices = cuisineChoices;
    }

    public String getFinalRestaurantId() {
        return finalRestaurantId;
    }

    public void setFinalRestaurantId(String finalRestaurantId) {
        this.finalRestaurantId = finalRestaurantId;
    }

    public Long getFinalCuisineId() {
        return finalCuisineId;
    }

    public void setFinalCuisineId(Long finalCuisineId) {
        this.finalCuisineId = finalCuisineId;
    }

    public Long getFinalTimeId() {
        return finalTimeId;
    }

    public void setFinalTimeId(Long finalTimeId) {
        this.finalTimeId = finalTimeId;
    }

    public List<Time> getTimeChoices() {
        return timeChoices;
    }

    public void setTimeChoices(List<Time> timeChoices) {
        this.timeChoices = timeChoices;
    }


    @Override
    public String toString() {
        return "CreateUpdateEventRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", budget='" + budget + '\'' +
                ", organiserId=" + organiserId +
                ", participantIds=" + participantIds +
                ", location=" + location +
                ", resturantChoices=" + resturantChoices +
                ", cuisineChoices=" + cuisineChoices +
                ", finalRestaurantId='" + finalRestaurantId + '\'' +
                ", finalCuisineId=" + finalCuisineId +
                ", finalTimeId=" + finalTimeId +
                ", timeChoices=" + timeChoices +
                '}';
    }
}
