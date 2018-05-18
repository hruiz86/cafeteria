package com.mono.app.web.rest;

import com.mono.app.CafeteriaApp;

import com.mono.app.domain.Garzon;
import com.mono.app.repository.GarzonRepository;
import com.mono.app.service.GarzonService;
import com.mono.app.web.rest.errors.ExceptionTranslator;
import com.mono.app.service.dto.GarzonCriteria;
import com.mono.app.service.GarzonQueryService;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mono.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GarzonResource REST controller.
 *
 * @see GarzonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafeteriaApp.class)
public class GarzonResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private GarzonRepository garzonRepository;

    @Autowired
    private GarzonService garzonService;

    @Autowired
    private GarzonQueryService garzonQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGarzonMockMvc;

    private Garzon garzon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GarzonResource garzonResource = new GarzonResource(garzonService, garzonQueryService);
        this.restGarzonMockMvc = MockMvcBuilders.standaloneSetup(garzonResource)
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
    public static Garzon createEntity(EntityManager em) {
        Garzon garzon = new Garzon()
            .userId(DEFAULT_USER_ID)
            .picture(DEFAULT_PICTURE)
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return garzon;
    }

    @Before
    public void initTest() {
        garzon = createEntity(em);
    }

    @Test
    @Transactional
    public void createGarzon() throws Exception {
        int databaseSizeBeforeCreate = garzonRepository.findAll().size();

        // Create the Garzon
        restGarzonMockMvc.perform(post("/api/garzons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garzon)))
            .andExpect(status().isCreated());

        // Validate the Garzon in the database
        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeCreate + 1);
        Garzon testGarzon = garzonList.get(garzonList.size() - 1);
        assertThat(testGarzon.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testGarzon.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testGarzon.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGarzon.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    public void createGarzonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = garzonRepository.findAll().size();

        // Create the Garzon with an existing ID
        garzon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGarzonMockMvc.perform(post("/api/garzons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garzon)))
            .andExpect(status().isBadRequest());

        // Validate the Garzon in the database
        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = garzonRepository.findAll().size();
        // set the field null
        garzon.setUserId(null);

        // Create the Garzon, which fails.

        restGarzonMockMvc.perform(post("/api/garzons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garzon)))
            .andExpect(status().isBadRequest());

        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPictureIsRequired() throws Exception {
        int databaseSizeBeforeTest = garzonRepository.findAll().size();
        // set the field null
        garzon.setPicture(null);

        // Create the Garzon, which fails.

        restGarzonMockMvc.perform(post("/api/garzons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garzon)))
            .andExpect(status().isBadRequest());

        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = garzonRepository.findAll().size();
        // set the field null
        garzon.setName(null);

        // Create the Garzon, which fails.

        restGarzonMockMvc.perform(post("/api/garzons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garzon)))
            .andExpect(status().isBadRequest());

        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGarzons() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList
        restGarzonMockMvc.perform(get("/api/garzons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garzon.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGarzon() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get the garzon
        restGarzonMockMvc.perform(get("/api/garzons/{id}", garzon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(garzon.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllGarzonsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where userId equals to DEFAULT_USER_ID
        defaultGarzonShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the garzonList where userId equals to UPDATED_USER_ID
        defaultGarzonShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllGarzonsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultGarzonShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the garzonList where userId equals to UPDATED_USER_ID
        defaultGarzonShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllGarzonsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where userId is not null
        defaultGarzonShouldBeFound("userId.specified=true");

        // Get all the garzonList where userId is null
        defaultGarzonShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarzonsByPictureIsEqualToSomething() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where picture equals to DEFAULT_PICTURE
        defaultGarzonShouldBeFound("picture.equals=" + DEFAULT_PICTURE);

        // Get all the garzonList where picture equals to UPDATED_PICTURE
        defaultGarzonShouldNotBeFound("picture.equals=" + UPDATED_PICTURE);
    }

    @Test
    @Transactional
    public void getAllGarzonsByPictureIsInShouldWork() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where picture in DEFAULT_PICTURE or UPDATED_PICTURE
        defaultGarzonShouldBeFound("picture.in=" + DEFAULT_PICTURE + "," + UPDATED_PICTURE);

        // Get all the garzonList where picture equals to UPDATED_PICTURE
        defaultGarzonShouldNotBeFound("picture.in=" + UPDATED_PICTURE);
    }

    @Test
    @Transactional
    public void getAllGarzonsByPictureIsNullOrNotNull() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where picture is not null
        defaultGarzonShouldBeFound("picture.specified=true");

        // Get all the garzonList where picture is null
        defaultGarzonShouldNotBeFound("picture.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarzonsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where name equals to DEFAULT_NAME
        defaultGarzonShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the garzonList where name equals to UPDATED_NAME
        defaultGarzonShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGarzonsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGarzonShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the garzonList where name equals to UPDATED_NAME
        defaultGarzonShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGarzonsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where name is not null
        defaultGarzonShouldBeFound("name.specified=true");

        // Get all the garzonList where name is null
        defaultGarzonShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGarzonsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where lastName equals to DEFAULT_LAST_NAME
        defaultGarzonShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the garzonList where lastName equals to UPDATED_LAST_NAME
        defaultGarzonShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllGarzonsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultGarzonShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the garzonList where lastName equals to UPDATED_LAST_NAME
        defaultGarzonShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllGarzonsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        garzonRepository.saveAndFlush(garzon);

        // Get all the garzonList where lastName is not null
        defaultGarzonShouldBeFound("lastName.specified=true");

        // Get all the garzonList where lastName is null
        defaultGarzonShouldNotBeFound("lastName.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGarzonShouldBeFound(String filter) throws Exception {
        restGarzonMockMvc.perform(get("/api/garzons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garzon.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGarzonShouldNotBeFound(String filter) throws Exception {
        restGarzonMockMvc.perform(get("/api/garzons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingGarzon() throws Exception {
        // Get the garzon
        restGarzonMockMvc.perform(get("/api/garzons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarzon() throws Exception {
        // Initialize the database
        garzonService.save(garzon);

        int databaseSizeBeforeUpdate = garzonRepository.findAll().size();

        // Update the garzon
        Garzon updatedGarzon = garzonRepository.findOne(garzon.getId());
        // Disconnect from session so that the updates on updatedGarzon are not directly saved in db
        em.detach(updatedGarzon);
        updatedGarzon
            .userId(UPDATED_USER_ID)
            .picture(UPDATED_PICTURE)
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME);

        restGarzonMockMvc.perform(put("/api/garzons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGarzon)))
            .andExpect(status().isOk());

        // Validate the Garzon in the database
        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeUpdate);
        Garzon testGarzon = garzonList.get(garzonList.size() - 1);
        assertThat(testGarzon.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testGarzon.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testGarzon.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGarzon.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGarzon() throws Exception {
        int databaseSizeBeforeUpdate = garzonRepository.findAll().size();

        // Create the Garzon

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGarzonMockMvc.perform(put("/api/garzons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garzon)))
            .andExpect(status().isCreated());

        // Validate the Garzon in the database
        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGarzon() throws Exception {
        // Initialize the database
        garzonService.save(garzon);

        int databaseSizeBeforeDelete = garzonRepository.findAll().size();

        // Get the garzon
        restGarzonMockMvc.perform(delete("/api/garzons/{id}", garzon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Garzon> garzonList = garzonRepository.findAll();
        assertThat(garzonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Garzon.class);
        Garzon garzon1 = new Garzon();
        garzon1.setId(1L);
        Garzon garzon2 = new Garzon();
        garzon2.setId(garzon1.getId());
        assertThat(garzon1).isEqualTo(garzon2);
        garzon2.setId(2L);
        assertThat(garzon1).isNotEqualTo(garzon2);
        garzon1.setId(null);
        assertThat(garzon1).isNotEqualTo(garzon2);
    }
}
