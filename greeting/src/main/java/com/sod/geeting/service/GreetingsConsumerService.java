package com.sod.geeting.service;

import com.sod.geeting.domain.Greeting;

public interface GreetingsConsumerService {

    void consume(Greeting message);
}
