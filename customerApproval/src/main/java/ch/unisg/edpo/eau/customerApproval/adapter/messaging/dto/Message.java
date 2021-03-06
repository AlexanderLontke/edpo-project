package ch.unisg.edpo.eau.customerApproval.adapter.messaging.dto;

import java.util.UUID;

public class Message<T> {

    // Cloud Events attributes (https://github.com/cloudevents/spec/blob/v1.0/spec.md)
    private String type;
    private String id = UUID.randomUUID().toString(); // unique id of this message
    private String source = "Cloud Stream Test";
    private T data;
    private String datacontenttype="application/json";
    private String specversion="1.0";

    public Message() {
    }

    public Message(String type, T payload) {
        this.type = type;
        this.data = payload;
    }

    @Override
    public String toString() {
        return "Message [type=" + type + ", id=" + id + "," +
                "data=" + data + "]";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public String getDatacontenttype() {
        return datacontenttype;
    }

    public String getSpecversion() {
        return specversion;
    }
}




