package com.mono.app.web.rest;

import com.mono.app.CafeteriaApp;

import com.mono.app.domain.Orden;
import com.mono.app.domain.Garzon;
import com.mono.app.domain.Product;
import com.mono.app.repository.OrdenRepository;
import com.mono.app.service.OrdenService;
import com.mono.app.web.rest.errors.ExceptionTranslator;
import com.mono.app.service.dto.OrdenCriteria;
import com.mono.app.service.OrdenQueryService;

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

import com.mono.app.domain.enumeration.State;
/**
 * Test class for the OrdenResource REST controller.
 *
 * @see OrdenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafeteriaApp.class)
public class OrdenResourceIntTest {

    private static final State DEFAULT_STATE = State.PENDING_APPROVAL;
    private static final State UPDATED_STATE = State.APROVED;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private OrdenQueryService ordenQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdenMockMvc;

    private Orden orden;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdenResource ordenResource = new OrdenResource(ordenService, ordenQueryService);
        this.restOrdenMockMvc = MockMvcBuilders.standaloneSetup(ordenResource)
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
    public static Orden createEntity(EntityManager em) {
        Orden orden = new Orden()
            .state(DEFAULT_STATE)
            .total(DEFAULT_TOTAL);
        return orden;
    }

    @Before
    public void initTest() {
        orden = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrden() throws Exception {
        int databaseSizeBeforeCreate = ordenRepository.findAll().size();

        // Create the Orden
        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isCreated());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate + 1);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testOrden.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createOrdenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordenRepository.findAll().size();

        // Create the Orden with an existing ID
        orden.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordenRepository.findAll().size();
        // set the field null
        orden.setState(null);

        // Create the Orden, which fails.

        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isBadRequest());

        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordenRepository.findAll().size();
        // set the field null
        orden.setTotal(null);

        // Create the Orden, which fails.

        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isBadRequest());

        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdens() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList
        restOrdenMockMvc.perform(get("/api/ordens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orden.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get the orden
        restOrdenMockMvc.perform(get("/api/ordens/{id}", orden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orden.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllOrdensByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList where state equals to DEFAULT_STATE
        defaultOrdenShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the ordenList where state equals to UPDATED_STATE
        defaultOrdenShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllOrdensByStateIsInShouldWork() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList where state in DEFAULT_STATE or UPDATED_STATE
        defaultOrdenShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the ordenList where state equals to UPDATED_STATE
        defaultOrdenShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllOrdensByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList where state is not null
        defaultOrdenShouldBeFound("state.specified=true");

        // Get all the ordenList where state is null
        defaultOrdenShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdensByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList where total equals to DEFAULT_TOTAL
        defaultOrdenShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the ordenList where total equals to UPDATED_TOTAL
        defaultOrdenShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdensByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultOrdenShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the ordenList where total equals to UPDATED_TOTAL
        defaultOrdenShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdensByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList where total is not null
        defaultOrdenShouldBeFound("total.specified=true");

        // Get all the ordenList where total is null
        defaultOrdenShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdensByGarzonIsEqualToSomething() throws Exception {
        // Initialize the database
        Garzon garzon = GarzonResourceIntTest.createEntity(em);
        em.persist(garzon);
        em.flush();
        orden.addGarzon(garzon);
        ordenRepository.saveAndFlush(orden);
        Long garzonId = garzon.getId();

        // Get all the ordenList where garzon equals to garzonId
        defaultOrdenShouldBeFound("garzonId.equals=" + garzonId);

        // Get all the ordenList where garzon equals to garzonId + 1
        defaultOrdenShouldNotBeFound("garzonId.equals=" + (garzonId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdensByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        orden.addProduct(product);
        ordenRepository.saveAndFlush(orden);
        Long productId = product.getId();

        // Get all the ordenList where product equals to productId
        defaultOrdenShouldBeFound("productId.equals=" + productId);

        // Get all the ordenList where product equals to productId + 1
        defaultOrdenShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOrdenShouldBeFound(String filter) throws Exception {
        restOrdenMockMvc.perform(get("/api/ordens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orden.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOrdenShouldNotBeFound(String filter) throws Exception {
        restOrdenMockMvc.perform(get("/api/ordens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingOrden() throws Exception {
        // Get the orden
        restOrdenMockMvc.perform(get("/api/ordens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrden() throws Exception {
        // Initialize the database
        ordenService.save(orden);

        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Update the orden
        Orden updatedOrden = ordenRepository.findOne(orden.getId());
        // Disconnect from session so that the updates on updatedOrden are not directly saved in db
        em.detach(updatedOrden);
        updatedOrden
            .state(UPDATED_STATE)
            .total(UPDATED_TOTAL);

        restOrdenMockMvc.perform(put("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrden)))
            .andExpect(status().isOk());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testOrden.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Create the Orden

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdenMockMvc.perform(put("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isCreated());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrden() throws Exception {
        // Initialize the database
        ordenService.save(orden);

        int databaseSizeBeforeDelete = ordenRepository.findAll().size();

        // Get the orden
        restOrdenMockMvc.perform(delete("/api/ordens/{id}", orden.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orden.class);
        Orden orden1 = new Orden();
        orden1.setId(1L);
        Orden orden2 = new Orden();
        orden2.setId(orden1.getId());
        assertThat(orden1).isEqualTo(orden2);
        orden2.setId(2L);
        assertThat(orden1).isNotEqualTo(orden2);
        orden1.setId(null);
        assertThat(orden1).isNotEqualTo(orden2);
    }
}
