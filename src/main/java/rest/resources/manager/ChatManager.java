package rest.resources.manager;

import exception.LOErrorCode;
import exception.LOException;
import rest.dao.IChatMessageDAO;
import rest.dao.IEventDAO;
import rest.dao.entity.ChatMessage;
import rest.dao.entity.Event;
import rest.dao.entity.User;
import rest.response.GetChatMessagesResponse;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

public class ChatManager implements IChatManager {

    @Inject
    protected IChatMessageDAO chatMessageDAO;

    @Inject
    protected IEventDAO eventDAO;

    @Override
    public GetChatMessagesResponse getChatMessages(Long userId, Long eventId, Timestamp timestamp, int limit) throws LOException {

        Event event = eventDAO.findEventById(eventId);
        List<User> participants = event.getParticipants();
        Boolean found = Boolean.FALSE;
        for(User temp: participants)
            if (temp.getId().equals(userId)){
                found = Boolean.TRUE;
                break;
            }

        if(!found)
            throw new LOException(403, "Token does not have permission");

        List<ChatMessage> messages = chatMessageDAO.findMessagesByEvent(eventId, timestamp, limit);
        if(messages!=null){
            GetChatMessagesResponse chatMessagesResponse = new GetChatMessagesResponse();
            chatMessagesResponse.setNumMessages(messages.size());
            chatMessagesResponse.setMessages(messages);
            return chatMessagesResponse;
        }else{
            throw new LOException(400, LOErrorCode.EVENT_NOT_FOUND.getName());
        }


    }
}
