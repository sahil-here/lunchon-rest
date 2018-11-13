package rest.dao;

import rest.dao.entity.ChatFetch;
import rest.dao.entity.ChatMessage;
import exception.LOException;
import rest.request.ChatMessageFetchRequest;

import java.util.List;

public interface IChatMessageDAO {

    // same request gives the same response (idempotency)
    List<ChatMessage> idempotencyCheck(ChatMessageFetchRequest chatMessageFetchRequest) throws LOException;

    // list of messages before this timestamp (can be NULL)
    List<ChatMessage> findMessageByTimestamp(ChatFetch chatFetch) throws LOException;
}
