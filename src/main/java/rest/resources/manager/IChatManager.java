package rest.resources.manager;

import exception.LOException;
import rest.response.GetChatMessagesResponse;

import java.sql.Timestamp;

public interface IChatManager {

    GetChatMessagesResponse getChatMessages(Long eventId, Timestamp timestamp, int limit) throws LOException;

}
