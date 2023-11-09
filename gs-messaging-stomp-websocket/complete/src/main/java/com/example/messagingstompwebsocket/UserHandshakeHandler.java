package com.example.messagingstompwebsocket;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final static Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//        String name = UUID.randomUUID().toString();
//        return new UserPrincipal(name);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        LOG.info("User with name {} entered", authentication.getName());
        return new UserPrincipal(authentication.getName());
    }
}
