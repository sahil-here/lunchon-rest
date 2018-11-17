package rest.dao.entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

public class ChatFetch implements Serializable {

    @Column(name = "timestamp")
    private Timestamp timestamp = null;  // NULL signifies the 'latest' message

    @Column(name = "event_id", nullable = false)
    private Long event_id;

    @Column(name = "event_id", nullable = false)
    private int limit = 100;


}
