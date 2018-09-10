package com.sod.geeting.messaging;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GreetingsProducerChannel {

    String CHANNEL = "greetingsProducerChannel";

    @Output
    MessageChannel greetingsProducerChannel();
}
