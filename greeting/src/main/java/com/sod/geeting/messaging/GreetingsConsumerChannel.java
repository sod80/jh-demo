package com.sod.geeting.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GreetingsConsumerChannel {

    String CHANNEL = "greetingsConsumerChannel";

    @Input
    SubscribableChannel greetingsConsumerChannel();
}
