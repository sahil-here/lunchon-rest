package rest.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Event", indexes = { @Index(columnList = "id", name = "event_id_index_on_events"),
        @Index(columnList = "name", name = "name_index_on_events")})

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "idempotencyCheckForEventCreation", query = "FROM Event WHERE name = :name"),
        @org.hibernate.annotations.NamedQuery(name = "findEventById", query = "FROM Event WHERE id= :id"),
        @org.hibernate.annotations.NamedQuery(name = "findEventByName", query = "FROM Event WHERE name= :name")})
public class Event implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "budget")
    private String budget;

    @OneToOne
    @JoinColumn(name="organiser_id")
    private User organiser;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Participant", joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> participants;

    @OneToMany( mappedBy="event",cascade = CascadeType.ALL)
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<EventStatus> statuses;

    @OneToOne
    @JoinColumn(name="location_id")
    private Location location;

    @OneToMany( mappedBy="event",cascade = CascadeType.ALL)
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TimePoll> timePolls;

    @OneToMany( mappedBy="event",cascade = CascadeType.ALL)
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RestaurantPoll> restaurantPolls;

    @OneToMany( mappedBy="event",cascade = CascadeType.ALL)
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CuisinePoll> cuisinePolls;

    @OneToOne
    @JoinColumn(name="final_time_id")
    private Time finalTime;

    @OneToOne
    @JoinColumn(name="final_cuisine_id")
    private Cuisine finalCuisine;

    @OneToOne
    @JoinColumn(name="final_restaurant_id")
    private Restaurant finalRestaurant;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="CuisineChoice", joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="cuisine_id"))
    private List<Cuisine> cuisineChoices;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="RestaurantChoice", joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="restaurant_id"))
    private List<Restaurant> restaurantChoices;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="TimeChoice", joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="time_id"))
    private List<Time> timeChoices;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

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

    public User getOrganiser() {
        return organiser;
    }

    public void setOrganiser(User organiser) {
        this.organiser = organiser;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<EventStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<EventStatus> statuses) {
        this.statuses = statuses;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<TimePoll> getTimePolls() {
        return timePolls;
    }

    public void setTimePolls(List<TimePoll> timePolls) {
        this.timePolls = timePolls;
    }

    public List<RestaurantPoll> getRestaurantPolls() {
        return restaurantPolls;
    }

    public void setRestaurantPolls(List<RestaurantPoll> restaurantPolls) {
        this.restaurantPolls = restaurantPolls;
    }

    public List<CuisinePoll> getCuisinePolls() {
        return cuisinePolls;
    }

    public void setCuisinePolls(List<CuisinePoll> cuisinePolls) {
        this.cuisinePolls = cuisinePolls;
    }

    public Time getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(Time finalTime) {
        this.finalTime = finalTime;
    }

    public Cuisine getFinalCuisine() {
        return finalCuisine;
    }

    public void setFinalCuisine(Cuisine finalCuisine) {
        this.finalCuisine = finalCuisine;
    }

    public Restaurant getFinalRestaurant() {
        return finalRestaurant;
    }

    public void setFinalRestaurant(Restaurant finalRestaurant) {
        this.finalRestaurant = finalRestaurant;
    }

    public List<Cuisine> getCuisineChoices() {
        return cuisineChoices;
    }

    public void setCuisineChoices(List<Cuisine> cuisineChoices) {
        this.cuisineChoices = cuisineChoices;
    }

    public List<Restaurant> getRestaurantChoices() {
        return restaurantChoices;
    }

    public void setRestaurantChoices(List<Restaurant> restaurantChoices) {
        this.restaurantChoices = restaurantChoices;
    }

    public List<Time> getTimeChoices() {
        return timeChoices;
    }

    public void setTimeChoices(List<Time> timeChoices) {
        this.timeChoices = timeChoices;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
