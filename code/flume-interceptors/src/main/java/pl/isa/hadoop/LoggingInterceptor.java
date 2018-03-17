package pl.isa.hadoop;

import java.util.List;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.apache.log4j.Logger;

public class LoggingInterceptor implements Interceptor {
    private static Logger log = Logger.getLogger(LoggingInterceptor.class);
    private final String logPrefix;

    private LoggingInterceptor(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    @Override
    public void initialize() {
        log.info("Starting logging interceptor with prefix " + logPrefix);
    }

    @Override
    public Event intercept(Event event) {
        log.info(logPrefix + " " + new String(event.getBody()));
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
        log.info("Closing logging interceptor");
    }

    public static class Builder implements Interceptor.Builder {
        private String logPrefix;

        @Override
        public Interceptor build() {
            return new LoggingInterceptor(logPrefix);
        }

        @Override
        public void configure(Context context) {
            logPrefix = context.getString("logPrefix");
        }
    }
}
