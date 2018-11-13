package rest.dao;

import com.google.inject.Inject;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rest.dao.entity.ChatMessage;
import rest.dao.entity.Event;
import rest.request.ChatMessageFetchRequest;

import java.sql.Timestamp;
import java.util.List;

public class ChatMessageDAO extends AbstractDAO<ChatMessage> implements IChatMessageDAO {

    @Inject
    public ChatMessageDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public List<ChatMessage> idempotencyCheck(ChatMessageFetchRequest chatMessageFetchRequest) throws LOException {
        return null;
    }

    @Override
    public List<ChatMessage> findMessageByEvent(Event event, Timestamp timestamp, int limit) throws LOException {
        return null;
    }


}
