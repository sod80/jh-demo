package com.sod.geeting.service;

import com.sod.geeting.domain.Greeting;

public interface GreetingsProducerService {

    void produce(Greeting greeting);
}
