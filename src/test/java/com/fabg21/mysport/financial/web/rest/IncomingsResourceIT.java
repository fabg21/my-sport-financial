package com.fabg21.mysport.financial.web.rest;

import com.fabg21.mysport.financial.MySportFinancialApp;
import com.fabg21.mysport.financial.domain.Incomings;
import com.fabg21.mysport.financial.repository.IncomingsRepository;
import com.fabg21.mysport.financial.service.IncomingsService;
import com.fabg21.mysport.financial.service.dto.IncomingsDTO;
import com.fabg21.mysport.financial.service.mapper.IncomingsMapper;
import com.fabg21.mysport.financial.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fabg21.mysport.financial.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IncomingsResource} REST controller.
 */
@SpringBootTest(classes = MySportFinancialApp.class)
public class IncomingsResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private IncomingsRepository incomingsRepository;

    @Autowired
    private IncomingsMapper incomingsMapper;

    @Autowired
    private IncomingsService incomingsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIncomingsMockMvc;

    private Incomings incomings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncomingsResource incomingsResource = new IncomingsResource(incomingsService);
        this.restIncomingsMockMvc = MockMvcBuilders.standaloneSetup(incomingsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incomings createEntity(EntityManager em) {
        Incomings incomings = new Incomings()
            .label(DEFAULT_LABEL)
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS)
            .category(DEFAULT_CATEGORY);
        return incomings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incomings createUpdatedEntity(EntityManager em) {
        Incomings incomings = new Incomings()
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .category(UPDATED_CATEGORY);
        return incomings;
    }

    @BeforeEach
    public void initTest() {
        incomings = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomings() throws Exception {
        int databaseSizeBeforeCreate = incomingsRepository.findAll().size();

        // Create the Incomings
        IncomingsDTO incomingsDTO = incomingsMapper.toDto(incomings);
        restIncomingsMockMvc.perform(post("/api/incomings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomingsDTO)))
            .andExpect(status().isCreated());

        // Validate the Incomings in the database
        List<Incomings> incomingsList = incomingsRepository.findAll();
        assertThat(incomingsList).hasSize(databaseSizeBeforeCreate + 1);
        Incomings testIncomings = incomingsList.get(incomingsList.size() - 1);
        assertThat(testIncomings.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testIncomings.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testIncomings.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testIncomings.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createIncomingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomingsRepository.findAll().size();

        // Create the Incomings with an existing ID
        incomings.setId(1L);
        IncomingsDTO incomingsDTO = incomingsMapper.toDto(incomings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomingsMockMvc.perform(post("/api/incomings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incomings in the database
        List<Incomings> incomingsList = incomingsRepository.findAll();
        assertThat(incomingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIncomings() throws Exception {
        // Initialize the database
        incomingsRepository.saveAndFlush(incomings);

        // Get all the incomingsList
        restIncomingsMockMvc.perform(get("/api/incomings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomings.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));
    }
    
    @Test
    @Transactional
    public void getIncomings() throws Exception {
        // Initialize the database
        incomingsRepository.saveAndFlush(incomings);

        // Get the incomings
        restIncomingsMockMvc.perform(get("/api/incomings/{id}", incomings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incomings.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY));
    }

    @Test
    @Transactional
    public void getNonExistingIncomings() throws Exception {
        // Get the incomings
        restIncomingsMockMvc.perform(get("/api/incomings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomings() throws Exception {
        // Initialize the database
        incomingsRepository.saveAndFlush(incomings);

        int databaseSizeBeforeUpdate = incomingsRepository.findAll().size();

        // Update the incomings
        Incomings updatedIncomings = incomingsRepository.findById(incomings.getId()).get();
        // Disconnect from session so that the updates on updatedIncomings are not directly saved in db
        em.detach(updatedIncomings);
        updatedIncomings
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .category(UPDATED_CATEGORY);
        IncomingsDTO incomingsDTO = incomingsMapper.toDto(updatedIncomings);

        restIncomingsMockMvc.perform(put("/api/incomings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomingsDTO)))
            .andExpect(status().isOk());

        // Validate the Incomings in the database
        List<Incomings> incomingsList = incomingsRepository.findAll();
        assertThat(incomingsList).hasSize(databaseSizeBeforeUpdate);
        Incomings testIncomings = incomingsList.get(incomingsList.size() - 1);
        assertThat(testIncomings.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testIncomings.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testIncomings.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIncomings.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomings() throws Exception {
        int databaseSizeBeforeUpdate = incomingsRepository.findAll().size();

        // Create the Incomings
        IncomingsDTO incomingsDTO = incomingsMapper.toDto(incomings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomingsMockMvc.perform(put("/api/incomings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incomings in the database
        List<Incomings> incomingsList = incomingsRepository.findAll();
        assertThat(incomingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomings() throws Exception {
        // Initialize the database
        incomingsRepository.saveAndFlush(incomings);

        int databaseSizeBeforeDelete = incomingsRepository.findAll().size();

        // Delete the incomings
        restIncomingsMockMvc.perform(delete("/api/incomings/{id}", incomings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Incomings> incomingsList = incomingsRepository.findAll();
        assertThat(incomingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
