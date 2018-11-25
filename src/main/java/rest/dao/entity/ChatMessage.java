package rest.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "ChatMessage", indexes = { @Index(columnList = "event_id", name = "event_id_index_on_messages"),
        @Index(columnList = "timestamp", name = "time_index_on_messages")})

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "idempotencyCheckForMessage", query = "FROM ChatMessage WHERE event_id= :event_id AND timestamp= :timestamp"),
        @org.hibernate.annotations.NamedQuery(name = "findMessageByEventId", query = "FROM ChatMessage WHERE event_id= :event_id AND timestamp < :timestamp ORDER BY timestamp ASC")})


public class ChatMessage  implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long event_id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "author_name", nullable = false)
    private String author_name = "";

    @Column(name = "content_type")
    private int content_type = 0;  // 0 for text, 1 for URL

    @Column(name = "content")
    private String content = "";

    @Override
    public String toString(){
        return  "{\"author_name\": " + this.author_name +
                ", \"timestamp\":" + Long.toString(this.timestamp.getTime()) +
                ", \"event_id\":" + Long.toString(this.event_id) +
                ", \"content_type\":" + Integer.toString(this.content_type) +
                ", \"content:\"\"" + this.content + "\"}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}