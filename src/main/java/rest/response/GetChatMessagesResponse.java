package rest.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rest.dao.entity.ChatMessage;

import java.util.List;

public class GetChatMessagesResponse {

    private int numMessages;

    private List<ChatMessage> messages;

    public int getNumMessages() {
        return numMessages;
    }

    public void setNumMessages(int numMessages) {
        this.numMessages = numMessages;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public String toString(){

        ObjectMapper mapper = new ObjectMapper();
        String payload;
        try {
            payload = mapper.writeValueAsString(this);
        }catch (JsonProcessingException je){
            payload = "{error:" + je.toString() + "}";
        }

        return "GetChatMessagesResponse" + payload;
    }

}
