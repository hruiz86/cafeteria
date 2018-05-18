package com.mono.app.web.rest;

import com.mono.app.CafeteriaApp;

import com.mono.app.domain.AttendanceRecord;
import com.mono.app.domain.Garzon;
import com.mono.app.repository.AttendanceRecordRepository;
import com.mono.app.service.AttendanceRecordService;
import com.mono.app.web.rest.errors.ExceptionTranslator;
import com.mono.app.service.dto.AttendanceRecordCriteria;
import com.mono.app.service.AttendanceRecordQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mono.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mono.app.domain.enumeration.AttendanceType;
/**
 * Test class for the AttendanceRecordResource REST controller.
 *
 * @see AttendanceRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafeteriaApp.class)
public class AttendanceRecordResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AttendanceType DEFAULT_TYPE = AttendanceType.ON;
    private static final AttendanceType UPDATED_TYPE = AttendanceType.OFF;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @Autowired
    private AttendanceRecordQueryService attendanceRecordQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttendanceRecordMockMvc;

    private AttendanceRecord attendanceRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendanceRecordResource attendanceRecordResource = new AttendanceRecordResource(attendanceRecordService, attendanceRecordQueryService);
        this.restAttendanceRecordMockMvc = MockMvcBuilders.standaloneSetup(attendanceRecordResource)
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
    public static AttendanceRecord createEntity(EntityManager em) {
        AttendanceRecord attendanceRecord = new AttendanceRecord()
            .date(DEFAULT_DATE)
            .type(DEFAULT_TYPE);
        return attendanceRecord;
    }

    @Before
    public void initTest() {
        attendanceRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendanceRecord() throws Exception {
        int databaseSizeBeforeCreate = attendanceRecordRepository.findAll().size();

        // Create the AttendanceRecord
        restAttendanceRecordMockMvc.perform(post("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isCreated());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceRecord testAttendanceRecord = attendanceRecordList.get(attendanceRecordList.size() - 1);
        assertThat(testAttendanceRecord.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAttendanceRecord.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createAttendanceRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceRecordRepository.findAll().size();

        // Create the AttendanceRecord with an existing ID
        attendanceRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceRecordMockMvc.perform(post("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRecordRepository.findAll().size();
        // set the field null
        attendanceRecord.setDate(null);

        // Create the AttendanceRecord, which fails.

        restAttendanceRecordMockMvc.perform(post("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isBadRequest());

        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRecordRepository.findAll().size();
        // set the field null
        attendanceRecord.setType(null);

        // Create the AttendanceRecord, which fails.

        restAttendanceRecordMockMvc.perform(post("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isBadRequest());

        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttendanceRecords() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAttendanceRecord() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get the attendanceRecord
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records/{id}", attendanceRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceRecord.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllAttendanceRecordsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList where date equals to DEFAULT_DATE
        defaultAttendanceRecordShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the attendanceRecordList where date equals to UPDATED_DATE
        defaultAttendanceRecordShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceRecordsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList where date in DEFAULT_DATE or UPDATED_DATE
        defaultAttendanceRecordShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the attendanceRecordList where date equals to UPDATED_DATE
        defaultAttendanceRecordShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceRecordsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList where date is not null
        defaultAttendanceRecordShouldBeFound("date.specified=true");

        // Get all the attendanceRecordList where date is null
        defaultAttendanceRecordShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceRecordsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList where type equals to DEFAULT_TYPE
        defaultAttendanceRecordShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the attendanceRecordList where type equals to UPDATED_TYPE
        defaultAttendanceRecordShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendanceRecordsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAttendanceRecordShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the attendanceRecordList where type equals to UPDATED_TYPE
        defaultAttendanceRecordShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendanceRecordsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRecordRepository.saveAndFlush(attendanceRecord);

        // Get all the attendanceRecordList where type is not null
        defaultAttendanceRecordShouldBeFound("type.specified=true");

        // Get all the attendanceRecordList where type is null
        defaultAttendanceRecordShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceRecordsByGarzonIsEqualToSomething() throws Exception {
        // Initialize the database
        Garzon garzon = GarzonResourceIntTest.createEntity(em);
        em.persist(garzon);
        em.flush();
        attendanceRecord.addGarzon(garzon);
        attendanceRecordRepository.saveAndFlush(attendanceRecord);
        Long garzonId = garzon.getId();

        // Get all the attendanceRecordList where garzon equals to garzonId
        defaultAttendanceRecordShouldBeFound("garzonId.equals=" + garzonId);

        // Get all the attendanceRecordList where garzon equals to garzonId + 1
        defaultAttendanceRecordShouldNotBeFound("garzonId.equals=" + (garzonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAttendanceRecordShouldBeFound(String filter) throws Exception {
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAttendanceRecordShouldNotBeFound(String filter) throws Exception {
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingAttendanceRecord() throws Exception {
        // Get the attendanceRecord
        restAttendanceRecordMockMvc.perform(get("/api/attendance-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendanceRecord() throws Exception {
        // Initialize the database
        attendanceRecordService.save(attendanceRecord);

        int databaseSizeBeforeUpdate = attendanceRecordRepository.findAll().size();

        // Update the attendanceRecord
        AttendanceRecord updatedAttendanceRecord = attendanceRecordRepository.findOne(attendanceRecord.getId());
        // Disconnect from session so that the updates on updatedAttendanceRecord are not directly saved in db
        em.detach(updatedAttendanceRecord);
        updatedAttendanceRecord
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE);

        restAttendanceRecordMockMvc.perform(put("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendanceRecord)))
            .andExpect(status().isOk());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeUpdate);
        AttendanceRecord testAttendanceRecord = attendanceRecordList.get(attendanceRecordList.size() - 1);
        assertThat(testAttendanceRecord.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAttendanceRecord.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendanceRecord() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRecordRepository.findAll().size();

        // Create the AttendanceRecord

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttendanceRecordMockMvc.perform(put("/api/attendance-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceRecord)))
            .andExpect(status().isCreated());

        // Validate the AttendanceRecord in the database
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttendanceRecord() throws Exception {
        // Initialize the database
        attendanceRecordService.save(attendanceRecord);

        int databaseSizeBeforeDelete = attendanceRecordRepository.findAll().size();

        // Get the attendanceRecord
        restAttendanceRecordMockMvc.perform(delete("/api/attendance-records/{id}", attendanceRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        assertThat(attendanceRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceRecord.class);
        AttendanceRecord attendanceRecord1 = new AttendanceRecord();
        attendanceRecord1.setId(1L);
        AttendanceRecord attendanceRecord2 = new AttendanceRecord();
        attendanceRecord2.setId(attendanceRecord1.getId());
        assertThat(attendanceRecord1).isEqualTo(attendanceRecord2);
        attendanceRecord2.setId(2L);
        assertThat(attendanceRecord1).isNotEqualTo(attendanceRecord2);
        attendanceRecord1.setId(null);
        assertThat(attendanceRecord1).isNotEqualTo(attendanceRecord2);
    }
}
