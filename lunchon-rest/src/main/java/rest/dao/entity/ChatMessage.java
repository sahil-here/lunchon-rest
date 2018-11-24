package rest.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "ChatMessages", indexes = { @Index(columnList = "event_id, timestamp", name = "EVENT_ID_AND_TIMESTAMP")})

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "idempotencyCheckForMessage", query = "FROM Message WHERE event_id = :event_id AND author_id = :author_id AND timestamp=:timestamp"),
        @org.hibernate.annotations.NamedQuery(name = "findMessageByEventId", query = "FROM Message WHERE event_id= :event_id AND timestamp< :timestamp LIMIT :limit")})

public class ChatMessage  implements Serializable{

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "author_id", nullable = false)
    private User author;

    @Column(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "content_type")
    private int content_type = 0;  // 0 for text, 1 for URL

    @Column(name = "content")
    private String content = "";

    @Override
    public String toString(){
        return  "{'author_name' : " + this.author.getName() +
                "'timestamp : '" + (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.timestamp)) +
                "'event_id : '" + Long.toString(this.event.getId()) +
                "'content_type : '" + Integer.toString(this.content_type) +
                "'content' : " + this.content + "}";
    }
}