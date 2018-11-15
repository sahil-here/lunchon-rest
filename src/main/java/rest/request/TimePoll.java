package rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TimePoll {

    @NotEmpty(message = "field is missing")
    @JsonProperty("time_id")
    private Long timeId;

    @NotEmpty(message = "field is missing")
    @JsonProperty("start_time")
    private Timestamp startTime;

    @NotEmpty(message = "field is missing")
    @JsonProperty("end_time")
    private Timestamp endTime;

    @NotEmpty(message = "field is missing")
    @JsonProperty("voter_id")
    private Long voterId;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
