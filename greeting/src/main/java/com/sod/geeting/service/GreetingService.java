package com.sod.geeting.service;

import com.sod.geeting.domain.Greeting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Greeting.
 */
public interface GreetingService {

    /**
     * Save a greeting.
     *
     * @param greeting the entity to save
     * @return the persisted entity
     */
    Greeting save(Greeting greeting);

    /**
     * Get all the greetings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Greeting> findAll(Pageable pageable);


    /**
     * Get the "id" greeting.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Greeting> findOne(String id);

    /**
     * Delete the "id" greeting.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
