package rest.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

public class ChatMessage  implements Serializable{

    /*
    chat message ->
           timestamp
           sender_id
           sender_name
           event_id
           content_type
           content
     */

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "author_id", nullable = false)
    private Long author_id;

    @Column(name = "author_name", nullable = false)
    private String author_name;

    @Column(name = "event_id", nullable = false)
    private Long event_id;

    @Column(name = "content_type")
    private int content_type = 0;  // 0 for text, 1 for URL

    @Column(name = "content")
    private String content = "";

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
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