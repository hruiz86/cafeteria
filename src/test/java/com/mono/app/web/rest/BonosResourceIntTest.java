package com.mono.app.web.rest;

import com.mono.app.CafeteriaApp;

import com.mono.app.domain.Bonos;
import com.mono.app.domain.Garzon;
import com.mono.app.repository.BonosRepository;
import com.mono.app.service.BonosService;
import com.mono.app.web.rest.errors.ExceptionTranslator;
import com.mono.app.service.dto.BonosCriteria;
import com.mono.app.service.BonosQueryService;

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

import com.mono.app.domain.enumeration.BonoType;
/**
 * Test class for the BonosResource REST controller.
 *
 * @see BonosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafeteriaApp.class)
public class BonosResourceIntTest {

    private static final BonoType DEFAULT_TYPE = BonoType.DAYLY;
    private static final BonoType UPDATED_TYPE = BonoType.MONTHLY;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private BonosRepository bonosRepository;

    @Autowired
    private BonosService bonosService;

    @Autowired
    private BonosQueryService bonosQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBonosMockMvc;

    private Bonos bonos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BonosResource bonosResource = new BonosResource(bonosService, bonosQueryService);
        this.restBonosMockMvc = MockMvcBuilders.standaloneSetup(bonosResource)
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
    public static Bonos createEntity(EntityManager em) {
        Bonos bonos = new Bonos()
            .type(DEFAULT_TYPE)
            .amount(DEFAULT_AMOUNT);
        return bonos;
    }

    @Before
    public void initTest() {
        bonos = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonos() throws Exception {
        int databaseSizeBeforeCreate = bonosRepository.findAll().size();

        // Create the Bonos
        restBonosMockMvc.perform(post("/api/bonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonos)))
            .andExpect(status().isCreated());

        // Validate the Bonos in the database
        List<Bonos> bonosList = bonosRepository.findAll();
        assertThat(bonosList).hasSize(databaseSizeBeforeCreate + 1);
        Bonos testBonos = bonosList.get(bonosList.size() - 1);
        assertThat(testBonos.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBonos.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createBonosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonosRepository.findAll().size();

        // Create the Bonos with an existing ID
        bonos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonosMockMvc.perform(post("/api/bonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonos)))
            .andExpect(status().isBadRequest());

        // Validate the Bonos in the database
        List<Bonos> bonosList = bonosRepository.findAll();
        assertThat(bonosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonosRepository.findAll().size();
        // set the field null
        bonos.setType(null);

        // Create the Bonos, which fails.

        restBonosMockMvc.perform(post("/api/bonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonos)))
            .andExpect(status().isBadRequest());

        List<Bonos> bonosList = bonosRepository.findAll();
        assertThat(bonosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonosRepository.findAll().size();
        // set the field null
        bonos.setAmount(null);

        // Create the Bonos, which fails.

        restBonosMockMvc.perform(post("/api/bonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonos)))
            .andExpect(status().isBadRequest());

        List<Bonos> bonosList = bonosRepository.findAll();
        assertThat(bonosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBonos() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get all the bonosList
        restBonosMockMvc.perform(get("/api/bonos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonos.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getBonos() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get the bonos
        restBonosMockMvc.perform(get("/api/bonos/{id}", bonos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bonos.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllBonosByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get all the bonosList where type equals to DEFAULT_TYPE
        defaultBonosShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the bonosList where type equals to UPDATED_TYPE
        defaultBonosShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBonosByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get all the bonosList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultBonosShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the bonosList where type equals to UPDATED_TYPE
        defaultBonosShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBonosByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get all the bonosList where type is not null
        defaultBonosShouldBeFound("type.specified=true");

        // Get all the bonosList where type is null
        defaultBonosShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonosByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get all the bonosList where amount equals to DEFAULT_AMOUNT
        defaultBonosShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the bonosList where amount equals to UPDATED_AMOUNT
        defaultBonosShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonosByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get all the bonosList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultBonosShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the bonosList where amount equals to UPDATED_AMOUNT
        defaultBonosShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonosByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonosRepository.saveAndFlush(bonos);

        // Get all the bonosList where amount is not null
        defaultBonosShouldBeFound("amount.specified=true");

        // Get all the bonosList where amount is null
        defaultBonosShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonosByGarzonIsEqualToSomething() throws Exception {
        // Initialize the database
        Garzon garzon = GarzonResourceIntTest.createEntity(em);
        em.persist(garzon);
        em.flush();
        bonos.setGarzon(garzon);
        bonosRepository.saveAndFlush(bonos);
        Long garzonId = garzon.getId();

        // Get all the bonosList where garzon equals to garzonId
        defaultBonosShouldBeFound("garzonId.equals=" + garzonId);

        // Get all the bonosList where garzon equals to garzonId + 1
        defaultBonosShouldNotBeFound("garzonId.equals=" + (garzonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBonosShouldBeFound(String filter) throws Exception {
        restBonosMockMvc.perform(get("/api/bonos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonos.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBonosShouldNotBeFound(String filter) throws Exception {
        restBonosMockMvc.perform(get("/api/bonos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBonos() throws Exception {
        // Get the bonos
        restBonosMockMvc.perform(get("/api/bonos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonos() throws Exception {
        // Initialize the database
        bonosService.save(bonos);

        int databaseSizeBeforeUpdate = bonosRepository.findAll().size();

        // Update the bonos
        Bonos updatedBonos = bonosRepository.findOne(bonos.getId());
        // Disconnect from session so that the updates on updatedBonos are not directly saved in db
        em.detach(updatedBonos);
        updatedBonos
            .type(UPDATED_TYPE)
            .amount(UPDATED_AMOUNT);

        restBonosMockMvc.perform(put("/api/bonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBonos)))
            .andExpect(status().isOk());

        // Validate the Bonos in the database
        List<Bonos> bonosList = bonosRepository.findAll();
        assertThat(bonosList).hasSize(databaseSizeBeforeUpdate);
        Bonos testBonos = bonosList.get(bonosList.size() - 1);
        assertThat(testBonos.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBonos.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingBonos() throws Exception {
        int databaseSizeBeforeUpdate = bonosRepository.findAll().size();

        // Create the Bonos

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBonosMockMvc.perform(put("/api/bonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonos)))
            .andExpect(status().isCreated());

        // Validate the Bonos in the database
        List<Bonos> bonosList = bonosRepository.findAll();
        assertThat(bonosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBonos() throws Exception {
        // Initialize the database
        bonosService.save(bonos);

        int databaseSizeBeforeDelete = bonosRepository.findAll().size();

        // Get the bonos
        restBonosMockMvc.perform(delete("/api/bonos/{id}", bonos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bonos> bonosList = bonosRepository.findAll();
        assertThat(bonosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bonos.class);
        Bonos bonos1 = new Bonos();
        bonos1.setId(1L);
        Bonos bonos2 = new Bonos();
        bonos2.setId(bonos1.getId());
        assertThat(bonos1).isEqualTo(bonos2);
        bonos2.setId(2L);
        assertThat(bonos1).isNotEqualTo(bonos2);
        bonos1.setId(null);
        assertThat(bonos1).isNotEqualTo(bonos2);
    }
}
