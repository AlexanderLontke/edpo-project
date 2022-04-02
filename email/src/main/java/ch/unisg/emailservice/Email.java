package ch.unisg.emailservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class Email {
    public static final String MEDIA_TYPE = "application/json";

    @Getter
    @Setter
    private String recipient;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String content;


    public Email() {  }

    public Email(String recipient, String title, String content) {
        this.recipient = recipient;
        this.title = title;
        this.content = content;
    }

    public static String serialize(Email email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(email);
    }

    public static Email deserialize(String emailString) throws JsonProcessingException {
        JsonNode emailData = new ObjectMapper().readTree(emailString);

        return new Email(
                emailData.get("recipient").textValue(),
                emailData.get("title").textValue(),
                emailData.get("content").textValue());
    }
}
