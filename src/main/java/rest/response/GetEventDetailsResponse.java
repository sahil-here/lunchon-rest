package rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import rest.dao.entity.Cuisine;
import rest.request.*;

import java.util.List;

public class GetEventDetailsResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("budget")
    private String budget;

    @JsonProperty("organiser_id")
    private Long organiserId;

    @JsonProperty("participants")
    private List<Long> participantIds;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("time_choices")
    private List<Time> timeChoices;

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

    @JsonProperty("cuisine_polls")
    private List<CuisinePoll> cuisinePolls;

    @JsonProperty("restaurant_polls")
    private List<RestaurantPoll> restaurantPolls;

    @JsonProperty("time_polls")
    private List<TimePoll> timePolls;

    @JsonProperty("statuses")
    private List<EventStatus> statuses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Time> getTimeChoices() {
        return timeChoices;
    }

    public void setTimeChoices(List<Time> timeChoices) {
        this.timeChoices = timeChoices;
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

    public List<CuisinePoll> getCuisinePolls() {
        return cuisinePolls;
    }

    public void setCuisinePolls(List<CuisinePoll> cuisinePolls) {
        this.cuisinePolls = cuisinePolls;
    }

    public List<RestaurantPoll> getRestaurantPolls() {
        return restaurantPolls;
    }

    public void setRestaurantPolls(List<RestaurantPoll> restaurantPolls) {
        this.restaurantPolls = restaurantPolls;
    }

    public List<TimePoll> getTimePolls() {
        return timePolls;
    }

    public void setTimePolls(List<TimePoll> timePolls) {
        this.timePolls = timePolls;
    }

    public List<EventStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<EventStatus> statuses) {
        this.statuses = statuses;
    }
}
