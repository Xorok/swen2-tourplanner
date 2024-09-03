package at.tiefenbrunner.swen2semesterprojekt.viewmodel.event;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class Publisher {

    private final Map<Event, List<Subscriber>> subscriberMap;

    public Publisher() {
        subscriberMap = new HashMap<>();
    }

    // subscribe(event, subscriber)
    public void subscribe(Event event, Subscriber subscriber) {
        List<Subscriber> subscribers = subscriberMap.get(event);

        if (null == subscribers) {
            subscribers = new ArrayList<>();
        }

        subscribers.add(subscriber);
        log.debug("Added subscriber {} for event {}", subscriber.getClass().getSimpleName(), event);

        subscriberMap.put(event, subscribers);
    }

    // publish(event, payload)
    public void publish(Event event, String payload) {
        List<Subscriber> subscribers = subscriberMap.get(event);

        if (subscribers == null) {
            log.debug("No subscribers for event {} with payload {}", event, payload);
            return;
        }

        for (Subscriber subscriber : subscribers) {
            subscriber.notify(payload);
            log.debug("Notified {} of event {} with payload {}", subscriber.getClass().getSimpleName(), event, payload);
        }
    }
}
