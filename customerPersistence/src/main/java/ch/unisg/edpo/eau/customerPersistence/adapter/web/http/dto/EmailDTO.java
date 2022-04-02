package ch.unisg.edpo.eau.customerPersistence.adapter.web.http.dto;

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

    public static EmailDTO deserialize(String EmailDTOString) throws JsonProcessingException {
        JsonNode EmailDTOData = new ObjectMapper().readTree(EmailDTOString);

        return new EmailDTO(
                EmailDTOData.get("recipient").textValue(),
                EmailDTOData.get("title").textValue(),
                EmailDTOData.get("content").textValue());
    }
}
