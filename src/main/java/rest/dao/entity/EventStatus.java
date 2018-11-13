package rest.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lunchon.entity.EventStatusName;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "EventStatus", indexes = { @Index(columnList = "event_id", name = "event_id_index_on_eventstatus")})

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "idempotencyCheckForEventStatus", query = "FROM EventStatus WHERE event_id = :event_id and status = :status"),
        @org.hibernate.annotations.NamedQuery(name = "findStatusByEventId", query = "FROM EventStatus WHERE event_id= :event_id")})
public class EventStatus implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EventStatusName status;

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

    public EventStatusName getStatus() {
        return status;
    }

    public void setStatus(EventStatusName status) {
        this.status = status;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
