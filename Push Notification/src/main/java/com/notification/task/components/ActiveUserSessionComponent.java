package com.notification.task.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ActiveUserSessionComponent {

    private final SimpUserRegistry simpUserRegistry;

    private static ConcurrentMap<String, String> loggedInUsers = new ConcurrentHashMap<>();


    @Autowired
    public ActiveUserSessionComponent(SimpUserRegistry simpUserRegistry) {
        this.simpUserRegistry = simpUserRegistry;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
        loggedInUsers.put(event.getUser().getName(), simpUserRegistry.getUser(event.getUser().getName()).getSessions().iterator().next().getId());
    }

    @EventListener
    private void handleSessionDisConnected(SessionDisconnectEvent event) {
        loggedInUsers.remove(event.getUser().getName());
    }

    public static ConcurrentMap<String, String> getLoggedInUsers() {
        return loggedInUsers;
    }

    public static String getUserActiveSessionId(String username) {
        return loggedInUsers.get(username);
    }
}
