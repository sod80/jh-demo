package com.sod.geeting.web.rest;

import com.sod.geeting.GreetingApp;

import com.sod.geeting.domain.Greeting;
import com.sod.geeting.repository.GreetingRepository;
import com.sod.geeting.service.GreetingService;
import com.sod.geeting.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static com.sod.geeting.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GreetingResource REST controller.
 *
 * @see GreetingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreetingApp.class)
public class GreetingResourceIntTest {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private GreetingRepository greetingRepository;

    

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restGreetingMockMvc;

    private Greeting greeting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GreetingResource greetingResource = new GreetingResource(greetingService);
        this.restGreetingMockMvc = MockMvcBuilders.standaloneSetup(greetingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Greeting createEntity() {
        Greeting greeting = new Greeting()
            .message(DEFAULT_MESSAGE);
        return greeting;
    }

    @Before
    public void initTest() {
        greetingRepository.deleteAll();
        greeting = createEntity();
    }

    @Test
    public void createGreeting() throws Exception {
        int databaseSizeBeforeCreate = greetingRepository.findAll().size();

        // Create the Greeting
        restGreetingMockMvc.perform(post("/api/greetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greeting)))
            .andExpect(status().isCreated());

        // Validate the Greeting in the database
        List<Greeting> greetingList = greetingRepository.findAll();
        assertThat(greetingList).hasSize(databaseSizeBeforeCreate + 1);
        Greeting testGreeting = greetingList.get(greetingList.size() - 1);
        assertThat(testGreeting.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    public void createGreetingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = greetingRepository.findAll().size();

        // Create the Greeting with an existing ID
        greeting.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGreetingMockMvc.perform(post("/api/greetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greeting)))
            .andExpect(status().isBadRequest());

        // Validate the Greeting in the database
        List<Greeting> greetingList = greetingRepository.findAll();
        assertThat(greetingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = greetingRepository.findAll().size();
        // set the field null
        greeting.setMessage(null);

        // Create the Greeting, which fails.

        restGreetingMockMvc.perform(post("/api/greetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greeting)))
            .andExpect(status().isBadRequest());

        List<Greeting> greetingList = greetingRepository.findAll();
        assertThat(greetingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllGreetings() throws Exception {
        // Initialize the database
        greetingRepository.save(greeting);

        // Get all the greetingList
        restGreetingMockMvc.perform(get("/api/greetings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(greeting.getId())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }
    

    @Test
    public void getGreeting() throws Exception {
        // Initialize the database
        greetingRepository.save(greeting);

        // Get the greeting
        restGreetingMockMvc.perform(get("/api/greetings/{id}", greeting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(greeting.getId()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }
    @Test
    public void getNonExistingGreeting() throws Exception {
        // Get the greeting
        restGreetingMockMvc.perform(get("/api/greetings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGreeting() throws Exception {
        // Initialize the database
        greetingService.save(greeting);

        int databaseSizeBeforeUpdate = greetingRepository.findAll().size();

        // Update the greeting
        Greeting updatedGreeting = greetingRepository.findById(greeting.getId()).get();
        updatedGreeting
            .message(UPDATED_MESSAGE);

        restGreetingMockMvc.perform(put("/api/greetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGreeting)))
            .andExpect(status().isOk());

        // Validate the Greeting in the database
        List<Greeting> greetingList = greetingRepository.findAll();
        assertThat(greetingList).hasSize(databaseSizeBeforeUpdate);
        Greeting testGreeting = greetingList.get(greetingList.size() - 1);
        assertThat(testGreeting.getMessage()).isSubstringOf(UPDATED_MESSAGE);
    }

    @Test
    public void updateNonExistingGreeting() throws Exception {
        int databaseSizeBeforeUpdate = greetingRepository.findAll().size();

        // Create the Greeting

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restGreetingMockMvc.perform(put("/api/greetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greeting)))
            .andExpect(status().isBadRequest());

        // Validate the Greeting in the database
        List<Greeting> greetingList = greetingRepository.findAll();
        assertThat(greetingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteGreeting() throws Exception {
        // Initialize the database
        greetingService.save(greeting);

        int databaseSizeBeforeDelete = greetingRepository.findAll().size();

        // Get the greeting
        restGreetingMockMvc.perform(delete("/api/greetings/{id}", greeting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Greeting> greetingList = greetingRepository.findAll();
        assertThat(greetingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Greeting.class);
        Greeting greeting1 = new Greeting();
        greeting1.setId("id1");
        Greeting greeting2 = new Greeting();
        greeting2.setId(greeting1.getId());
        assertThat(greeting1).isEqualTo(greeting2);
        greeting2.setId("id2");
        assertThat(greeting1).isNotEqualTo(greeting2);
        greeting1.setId(null);
        assertThat(greeting1).isNotEqualTo(greeting2);
    }
}
