package ch.unisg.edpo.eau.emailservice;

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
}
