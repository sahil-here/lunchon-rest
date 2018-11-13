package rest.dao;

import com.google.inject.Inject;
import exception.LOException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rest.dao.entity.ChatFetch;
import rest.dao.entity.ChatMessage;
import rest.request.ChatMessageFetchRequest;

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
    public List<ChatMessage> findMessageByTimestamp(ChatFetch chatFetch) throws LOException {
        return null;
    }
}
