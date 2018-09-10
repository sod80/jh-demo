package com.sod.geeting.service.impl;

import com.sod.geeting.service.GreetingService;
import com.sod.geeting.domain.Greeting;
import com.sod.geeting.repository.GreetingRepository;
import com.sod.geeting.service.GreetingsConsumerService;
import com.sod.geeting.service.GreetingsProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing Greeting.
 */
@Service
public class GreetingServiceImpl implements GreetingService {

    private final Logger log = LoggerFactory.getLogger(GreetingServiceImpl.class);

    private final GreetingRepository greetingRepository;

    private final GreetingsProducerService greetingsProducerService;

    private final GreetingsConsumerService greetingsConsumerService;

    public GreetingServiceImpl(
        GreetingRepository greetingRepository,
        GreetingsConsumerService greetingsConsumerService,
        GreetingsProducerService greetingsProducerService) {
        this.greetingRepository = greetingRepository;
        this.greetingsConsumerService = greetingsConsumerService;
        this.greetingsProducerService = greetingsProducerService;
    }

    /**
     * Save a greeting.
     *
     * @param greeting the entity to save
     * @return the persisted entity
     */
    @Override
    public Greeting save(Greeting greeting) {
        log.debug("Request to save Greeting : {}", greeting);

        // Send to kafka
        greetingsProducerService.produce(greeting);

        // Save to Repo
        return greetingRepository.save(greeting);
    }

    /**
     * Get all the greetings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Greeting> findAll(Pageable pageable) {
        log.debug("Request to get all Greetings");
        return greetingRepository.findAll(pageable);
    }


    /**
     * Get one greeting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Greeting> findOne(String id) {
        log.debug("Request to get Greeting : {}", id);
        return greetingRepository.findById(id);
    }

    /**
     * Delete the greeting by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Greeting : {}", id);
        greetingRepository.deleteById(id);
    }
}
