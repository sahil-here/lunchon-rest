package rest.dao;

import rest.dao.entity.ChatMessage;
import exception.LOException;
import rest.dao.entity.Event;
import rest.request.ChatMessageFetchRequest;

import java.sql.Timestamp;
import java.util.List;

public interface IChatMessageDAO {
    List<ChatMessage> idempotencyCheck(ChatMessageFetchRequest chatMessageFetchRequest) throws LOException;

    // list of messages before this timestamp (can be NULL)
    List<ChatMessage> findMessagesByEvent(Long eventId, Timestamp timestamp, int limit) throws LOException;
}
