package com.sod.geeting.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sod.geeting.domain.Greeting;
import com.sod.geeting.service.GreetingService;
import com.sod.geeting.web.rest.errors.BadRequestAlertException;
import com.sod.geeting.web.rest.util.HeaderUtil;
import com.sod.geeting.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Greeting.
 */
@RestController
@RequestMapping("/api")
public class GreetingResource {

    private final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    private static final String ENTITY_NAME = "greeting";

    private final GreetingService greetingService;

    public GreetingResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    /**
     * POST  /greetings : Create a new greeting.
     *
     * @param greeting the greeting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new greeting, or with status 400 (Bad Request) if the greeting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/greetings")
    @Timed
    public ResponseEntity<Greeting> createGreeting(@Valid @RequestBody Greeting greeting) throws URISyntaxException {
        log.debug("REST request to save Greeting : {}", greeting);
        if (greeting.getId() != null) {
            throw new BadRequestAlertException("A new greeting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Greeting result = greetingService.save(greeting);
        return ResponseEntity.created(new URI("/api/greetings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /greetings : Updates an existing greeting.
     *
     * @param greeting the greeting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated greeting,
     * or with status 400 (Bad Request) if the greeting is not valid,
     * or with status 500 (Internal Server Error) if the greeting couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/greetings")
    @Timed
    public ResponseEntity<Greeting> updateGreeting(@Valid @RequestBody Greeting greeting) throws URISyntaxException {
        log.debug("REST request to update Greeting : {}", greeting);
        if (greeting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Greeting result = greetingService.save(greeting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, greeting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /greetings : get all the greetings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of greetings in body
     */
    @GetMapping("/greetings")
    @Timed
    public ResponseEntity<List<Greeting>> getAllGreetings(Pageable pageable) {
        log.debug("REST request to get a page of Greetings");
        Page<Greeting> page = greetingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/greetings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /greetings/:id : get the "id" greeting.
     *
     * @param id the id of the greeting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the greeting, or with status 404 (Not Found)
     */
    @GetMapping("/greetings/{id}")
    @Timed
    public ResponseEntity<Greeting> getGreeting(@PathVariable String id) {
        log.debug("REST request to get Greeting : {}", id);
        Optional<Greeting> greeting = greetingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(greeting);
    }

    /**
     * DELETE  /greetings/:id : delete the "id" greeting.
     *
     * @param id the id of the greeting to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/greetings/{id}")
    @Timed
    public ResponseEntity<Void> deleteGreeting(@PathVariable String id) {
        log.debug("REST request to delete Greeting : {}", id);
        greetingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
