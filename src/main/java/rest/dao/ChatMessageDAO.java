package rest.dao;

import com.google.inject.Inject;
import exception.LOErrorCode;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rest.dao.entity.ChatMessage;
import rest.dao.entity.Event;
import rest.request.ChatMessageFetchRequest;

import javax.ws.rs.QueryParam;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatMessageDAO extends AbstractDAO<ChatMessage> implements IChatMessageDAO {

    @Inject
    public ChatMessageDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<ChatMessage> idempotencyCheck(ChatMessageFetchRequest chatMessageFetchRequest) throws LOException {
        List<ChatMessage> messages = new ArrayList<ChatMessage>();
        try {
            Query query = namedQuery("idempotencyCheckForMessage");
            query.setParameter("event_id", chatMessageFetchRequest.getEventId());
            query.setParameter("timestamp", chatMessageFetchRequest.getTimestamp());
            query.setParameter("limit", chatMessageFetchRequest.getLimit());
            Iterator it = query.iterate();
            while (it.hasNext()){
                messages.add((ChatMessage) it.next());
            }
            return messages;
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

    @Override
    public List<ChatMessage> findMessagesByEvent(Long eventId, Timestamp timestamp, int limit) throws LOException {
        List<ChatMessage> messages = new ArrayList<ChatMessage>();
        try {
            Query query = namedQuery("findMessageByEventId");
            query.setParameter("event_id", eventId);
            query.setParameter("timestamp", timestamp);
            query.setParameter("limit", limit);
            Iterator it = query.iterate();
            while (it.hasNext()){
                messages.add((ChatMessage) it.next());
            }
            return messages;
        }catch (Exception ex){
            throw new LOException(500, LOErrorCode.INTERNAL_SERVER_ERROR.getName(),ex);
        }
    }

}
