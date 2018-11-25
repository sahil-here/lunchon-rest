package rest.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ChatMessageFetchRequest {

    @NotEmpty(message = "missing eventId")
    @QueryParam("event_id")
    private Long eventId;

    @QueryParam("last_seen")
   // @DefaultValue("12/30/9999 23:59:59")
    private Timestamp timestamp;

    @QueryParam("limit")
    @DefaultValue("100")
    private int limit;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString(){
        return  "ChatMessageFetchRequest{" +
                "eventId='" + this.eventId.toString() + "'," +
                "timeStamp='" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp)) + "'," +
                "limit='" + Integer.toString(this.limit) + "'}";
    }
}
