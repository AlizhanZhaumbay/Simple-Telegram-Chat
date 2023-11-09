package com.example.messagingstompwebsocket;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class ChatDestinations {
    static final String TOPIC_DESTINATION_PREFIX = "/topic";
    static final String USER_DESTINATION_PREFIX = "/user";
    static final String SEND_MESSAGE_TO_ALL = "/topic/messages";
    static final String SEND_MESSAGE_TO_SPECIFIC = "/topic/messages/private";
    static final String FETCH_PUBLIC_MESSAGES = "/messages";
    static final String FETCH_PRIVATE_MESSAGES = "/messages/private";
    static final String REGISTRATION_ENDPOINT = "/ws";
}
