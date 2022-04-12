package ch.unisg.edpo.eau.adapter.out.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class EmailDTO {
    public static final String MEDIA_TYPE = "application/json";

    @Getter
    @Setter
    private String recipient;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String content;


    public EmailDTO() {}

    public EmailDTO(String recipient, String title, String content) {
        this.recipient = recipient;
        this.title = title;
        this.content = content;
    }

    public static String serialize(EmailDTO EmailDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(EmailDTO);
    }
}
