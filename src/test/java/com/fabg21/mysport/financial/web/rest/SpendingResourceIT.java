package com.fabg21.mysport.financial.web.rest;

import com.fabg21.mysport.financial.MySportFinancialApp;
import com.fabg21.mysport.financial.domain.Spending;
import com.fabg21.mysport.financial.repository.SpendingRepository;
import com.fabg21.mysport.financial.service.SpendingService;
import com.fabg21.mysport.financial.service.dto.SpendingDTO;
import com.fabg21.mysport.financial.service.mapper.SpendingMapper;
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
 * Integration tests for the {@link SpendingResource} REST controller.
 */
@SpringBootTest(classes = MySportFinancialApp.class)
public class SpendingResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private SpendingMapper spendingMapper;

    @Autowired
    private SpendingService spendingService;

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

    private MockMvc restSpendingMockMvc;

    private Spending spending;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpendingResource spendingResource = new SpendingResource(spendingService);
        this.restSpendingMockMvc = MockMvcBuilders.standaloneSetup(spendingResource)
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
    public static Spending createEntity(EntityManager em) {
        Spending spending = new Spending()
            .label(DEFAULT_LABEL)
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS);
        return spending;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spending createUpdatedEntity(EntityManager em) {
        Spending spending = new Spending()
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS);
        return spending;
    }

    @BeforeEach
    public void initTest() {
        spending = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpending() throws Exception {
        int databaseSizeBeforeCreate = spendingRepository.findAll().size();

        // Create the Spending
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);
        restSpendingMockMvc.perform(post("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isCreated());

        // Validate the Spending in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeCreate + 1);
        Spending testSpending = spendingList.get(spendingList.size() - 1);
        assertThat(testSpending.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testSpending.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSpending.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSpendingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spendingRepository.findAll().size();

        // Create the Spending with an existing ID
        spending.setId(1L);
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpendingMockMvc.perform(post("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Spending in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSpendings() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);

        // Get all the spendingList
        restSpendingMockMvc.perform(get("/api/spendings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spending.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getSpending() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);

        // Get the spending
        restSpendingMockMvc.perform(get("/api/spendings/{id}", spending.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spending.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingSpending() throws Exception {
        // Get the spending
        restSpendingMockMvc.perform(get("/api/spendings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpending() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);

        int databaseSizeBeforeUpdate = spendingRepository.findAll().size();

        // Update the spending
        Spending updatedSpending = spendingRepository.findById(spending.getId()).get();
        // Disconnect from session so that the updates on updatedSpending are not directly saved in db
        em.detach(updatedSpending);
        updatedSpending
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS);
        SpendingDTO spendingDTO = spendingMapper.toDto(updatedSpending);

        restSpendingMockMvc.perform(put("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isOk());

        // Validate the Spending in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeUpdate);
        Spending testSpending = spendingList.get(spendingList.size() - 1);
        assertThat(testSpending.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testSpending.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSpending.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSpending() throws Exception {
        int databaseSizeBeforeUpdate = spendingRepository.findAll().size();

        // Create the Spending
        SpendingDTO spendingDTO = spendingMapper.toDto(spending);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpendingMockMvc.perform(put("/api/spendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spendingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Spending in the database
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpending() throws Exception {
        // Initialize the database
        spendingRepository.saveAndFlush(spending);

        int databaseSizeBeforeDelete = spendingRepository.findAll().size();

        // Delete the spending
        restSpendingMockMvc.perform(delete("/api/spendings/{id}", spending.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Spending> spendingList = spendingRepository.findAll();
        assertThat(spendingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
