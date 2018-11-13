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
    private User author;

    @Column(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "content_type")
    private int content_type = 0;  // 0 for text, 1 for URL

    @Column(name = "content")
    private String content = "";
}