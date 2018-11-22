package rest.response;

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
        String messages = "";
        for (ChatMessage msg: this.messages)
            messages = messages.concat(msg.toString() + ",");
        if (this.getNumMessages() > 0)  // delete the last ","
            messages = messages.substring(0, messages.length()-1);

        return  "GetChatMessagesResponse{" +
                "numMessages : " + Integer.toString(this.getNumMessages()) + "," +
                "messages : [" + messages + "]}";
    }





}