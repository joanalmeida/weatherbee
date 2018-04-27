package com.redbee.weatherbee.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import static com.redbee.weatherbee.Services.WebSocketConfiguration.MESSAGE_PREFIX;

@Component
@RepositoryEventHandler
public class EventHandler implements ApplicationListener<LocationEvent>{

    private final SimpMessagingTemplate websocket;

    @Override
    public void onApplicationEvent(LocationEvent event) {
        System.out.println("Sending data to users");
        this.websocket.convertAndSend(MESSAGE_PREFIX + "/updateLocation", event.getLocations());
    }

    @Autowired
    public EventHandler(SimpMessagingTemplate websocket) {
        this.websocket = websocket;
    }
}
