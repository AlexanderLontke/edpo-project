package ch.unisg.edpo.eau.eventstrom;

import ch.unisg.edpo.eau.eventstrom.model.Billing;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.kstream.Window;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Adapt to our needs
public class RestService {

    private final HostInfo hostInfo;
    private final KafkaStreams streams;

    private static final Logger log = LoggerFactory.getLogger(RestService.class);

    RestService(HostInfo hostInfo, KafkaStreams streams) {
        this.hostInfo = hostInfo;
        this.streams = streams;
    }

    ReadOnlyWindowStore<String, Billing> getBillingStore() {
        return streams.store(
                StoreQueryParameters.fromNameAndType("windowed-billing", QueryableStoreTypes.windowStore()));
    }

    void start() {
        Javalin app = Javalin.create().start(hostInfo.port());
        app.get("/customerInvoices/", this::getAll);
        app.get("/customerInvoices/:customerId", this::getBillingForCustomer);
    }

    void getAll(Context ctx) {
        List<String> billingList = new ArrayList<>();

        KeyValueIterator<Windowed<String>, Billing> range = getBillingStore().all();
        while (range.hasNext()) {
            KeyValue<Windowed<String>, Billing> next = range.next();
            Billing value = next.value;
            billingList.add(value.toString());
        }
        System.out.println(billingList);
        // close the iterator to avoid memory leaks
        range.close();
        // return a JSON response
        ctx.result(billingList.toString());
    }

    void getBillingForCustomer(Context ctx) {
        String billing ="";

        Instant timeFrom = Instant.ofEpochMilli(0);
        Instant timeTo = Instant.now();

        WindowStoreIterator<Billing> range = getBillingStore().fetch(ctx.pathParam("customerId"), timeFrom, timeTo);

        if (range.hasNext()) {
            KeyValue<Long, Billing> next = range.next();
            billing = next.value.toString();
        } else {
            ctx.status(404);
        }
        System.out.println(billing);
        // close the iterator to avoid memory leaks
        range.close();

        // return a JSON response
        ctx.result(billing);
    }
}
