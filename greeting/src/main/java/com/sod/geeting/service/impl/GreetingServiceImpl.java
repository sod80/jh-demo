package com.sod.geeting.service.impl;

import com.sod.geeting.service.GreetingService;
import com.sod.geeting.domain.Greeting;
import com.sod.geeting.repository.GreetingRepository;
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

    public GreetingServiceImpl(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
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
        //TODO

        // Read from kafka
        // TODO
        
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
