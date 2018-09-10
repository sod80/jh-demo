package com.sod.geeting.service.impl;

import com.sod.geeting.domain.Greeting;
import com.sod.geeting.messaging.GreetingsProducerChannel;
import com.sod.geeting.service.GreetingsProducerService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class GreetingsProducerServiceImpl implements GreetingsProducerService {

    private final Logger log = LoggerFactory.getLogger(GreetingsConsumerServiceImpl.class);

    private MessageChannel channel;


    public GreetingsProducerServiceImpl(GreetingsProducerChannel channel) {
        this.channel = channel.greetingsProducerChannel();
    }

    //@Scheduled(fixedDelay = 3000)
    public void produce(Greeting greeting) {
        log.warn("Create Message");

        String originalMessage = greeting.getMessage();
        Greeting newGreeting = new Greeting();
        newGreeting.setMessage(originalMessage + " : k1 : " + new SimpleDateFormat().format(new Date()));
        channel.send(MessageBuilder.withPayload(newGreeting).build());
    }

}
