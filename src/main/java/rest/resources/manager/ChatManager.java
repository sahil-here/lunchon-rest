package rest.resources.manager;

import exception.LOErrorCode;
import exception.LOException;
import rest.dao.IChatMessageDAO;
import rest.dao.entity.ChatMessage;
import rest.response.GetChatMessagesResponse;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

public class ChatManager implements IChatManager {

    @Inject
    protected IChatMessageDAO chatMessageDAO;

    @Override
    public GetChatMessagesResponse getChatMessages(Long eventId, Timestamp timestamp, int limit) throws LOException {
        List<ChatMessage> messages = chatMessageDAO.findMessageByEvent(eventId, timestamp, limit);
        if(messages!=null){
            GetChatMessagesResponse chatMessagesResponse = new GetChatMessagesResponse();
            chatMessagesResponse.setNumMessages(messages.size());
            chatMessagesResponse.setMessages(messages);
        }else{
            throw new LOException(400, LOErrorCode.EVENT_NOT_FOUND.getName());
        }

        return null;
    }
}
