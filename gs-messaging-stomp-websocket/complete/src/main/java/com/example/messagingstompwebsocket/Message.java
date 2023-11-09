package com.example.messagingstompwebsocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Message {
    private String from;
    private String content;
    private String recipient;

}

