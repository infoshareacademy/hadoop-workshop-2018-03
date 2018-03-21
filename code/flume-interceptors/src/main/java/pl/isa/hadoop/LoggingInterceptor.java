package pl.isa.hadoop;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggingInterceptor implements Interceptor {
    private static Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public void initialize() {
        log.info("starting logging interceptor");
    }

    @Override
    public Event intercept(Event event) {
        String body = new String(event.getBody());
        log.info(body);

        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(event);
        }

        return list;
    }

    @Override
    public void close() {
        log.info("closing logging interceptor");
    }

    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new LoggingInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
