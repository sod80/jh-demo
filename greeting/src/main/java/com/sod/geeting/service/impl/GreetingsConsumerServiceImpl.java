package com.sod.geeting.service.impl;

import com.sod.geeting.domain.Greeting;
import com.sod.geeting.messaging.GreetingsConsumerChannel;
import com.sod.geeting.repository.GreetingRepository;
import com.sod.geeting.service.GreetingsConsumerService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class GreetingsConsumerServiceImpl implements GreetingsConsumerService {

    private final Logger log = LoggerFactory.getLogger(GreetingsConsumerServiceImpl.class);

    @Autowired
    GreetingRepository greetingRepository;

    @StreamListener(GreetingsConsumerChannel.CHANNEL)
    public void consume(Greeting message) {
        log.info("Consume Message");
        log.info("Received greeting message.toS: {}.", message.toString());
        log.info("Received greeting message: {}.", message.getMessage());
        String originalMessage = message.getMessage();
        Greeting greeting_message = new Greeting();
        greeting_message.setMessage(originalMessage + " : k2 : " + new SimpleDateFormat().format(new Date()));
        try {
            greetingRepository.save(greeting_message);
        } catch (Exception ex) {
            log.error("Consume Message ERROR - {}", ex.getMessage());
            //TODO add dead letter queue and Exception handling
            Greeting greeting_error = new Greeting();
            greeting_error.setMessage(greeting_message.getMessage().substring(0,45));
            greetingRepository.save(greeting_error);
            //throw
        }
    }
}
