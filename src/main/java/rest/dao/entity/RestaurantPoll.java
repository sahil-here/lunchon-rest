package rest.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "RestaurantPoll", indexes = { @Index(columnList = "event_id", name = "event_id_index_on_restaurantpoll")})

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "idempotencyCheckForRestaurantPoll", query = "FROM RestaurantPoll WHERE event_id = :event_id " +
                "and voter_id=:voter_id and restaurant_id=:restaurant_id"),
        @org.hibernate.annotations.NamedQuery(name = "findRestaurantPollByEventId", query = "FROM RestaurantPoll WHERE event_id= :event_id")})
public class RestaurantPoll implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name="voter_id")
    private User voter;

    @OneToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
