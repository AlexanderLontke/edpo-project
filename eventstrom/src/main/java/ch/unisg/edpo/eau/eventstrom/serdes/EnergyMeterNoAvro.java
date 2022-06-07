package ch.unisg.edpo.eau.eventstrom.serdes;

public class EnergyMeterNoAvro {
    private String message_id;
    private String message_type;
    private Long time_start;
    private Long time_end;
    private double e_i_minus_one;
    private double e_i;
    private double delta_e;
    private String device_id;
    private String customer_id;


    public EnergyMeterNoAvro(String message_id, String messageType, Long time_start, Long time_end, double e_i_minus_one, double e_i, double delta_e, String device_id, String customer_id) {
        this.message_id = message_id;
        this.message_type = messageType;
        this.time_start = time_start;
        this.time_end = time_end;
        this.e_i_minus_one = e_i_minus_one;
        this.e_i = e_i;
        this.delta_e = delta_e;
        this.device_id = device_id;
        this.customer_id = customer_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public Long getTime_start() {
        return time_start;
    }

    public void setTime_start(Long time_start) {
        this.time_start = time_start;
    }

    public Long getTime_end() {
        return time_end;
    }

    public void setTime_end(Long time_end) {
        this.time_end = time_end;
    }

    public double getE_i_minus_one() {
        return e_i_minus_one;
    }

    public void setE_i_minus_one(double e_i_minus_one) {
        this.e_i_minus_one = e_i_minus_one;
    }

    public double getE_i() {
        return e_i;
    }

    public void setE_i(double e_i) {
        this.e_i = e_i;
    }

    public double getDelta_e() {
        return delta_e;
    }

    public void setDelta_e(double delta_e) {
        this.delta_e = delta_e;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "EnergyMeterNoAvro{" +
                "message_id='" + message_id + '\'' +
                ", message_type='" + message_type + '\'' +
                ", time_start=" + time_start +
                ", time_end=" + time_end +
                ", e_i_minus_one=" + e_i_minus_one +
                ", e_i=" + e_i +
                ", delta_e=" + delta_e +
                ", device_id='" + device_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                '}';
    }
}
