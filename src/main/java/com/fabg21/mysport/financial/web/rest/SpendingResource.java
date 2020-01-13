package com.fabg21.mysport.financial.web.rest;

import com.fabg21.mysport.financial.service.SpendingService;
import com.fabg21.mysport.financial.web.rest.errors.BadRequestAlertException;
import com.fabg21.mysport.financial.service.dto.SpendingDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fabg21.mysport.financial.domain.Spending}.
 */
@RestController
@RequestMapping("/api")
public class SpendingResource {

    private final Logger log = LoggerFactory.getLogger(SpendingResource.class);

    private static final String ENTITY_NAME = "mySportFinancialSpending";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpendingService spendingService;

    public SpendingResource(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    /**
     * {@code POST  /spendings} : Create a new spending.
     *
     * @param spendingDTO the spendingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spendingDTO, or with status {@code 400 (Bad Request)} if the spending has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spendings")
    public ResponseEntity<SpendingDTO> createSpending(@RequestBody SpendingDTO spendingDTO) throws URISyntaxException {
        log.debug("REST request to save Spending : {}", spendingDTO);
        if (spendingDTO.getId() != null) {
            throw new BadRequestAlertException("A new spending cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpendingDTO result = spendingService.save(spendingDTO);
        return ResponseEntity.created(new URI("/api/spendings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /spendings} : Updates an existing spending.
     *
     * @param spendingDTO the spendingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spendingDTO,
     * or with status {@code 400 (Bad Request)} if the spendingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spendingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spendings")
    public ResponseEntity<SpendingDTO> updateSpending(@RequestBody SpendingDTO spendingDTO) throws URISyntaxException {
        log.debug("REST request to update Spending : {}", spendingDTO);
        if (spendingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpendingDTO result = spendingService.save(spendingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spendingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /spendings} : get all the spendings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spendings in body.
     */
    @GetMapping("/spendings")
    public List<SpendingDTO> getAllSpendings() {
        log.debug("REST request to get all Spendings");
        return spendingService.findAll();
    }

    /**
     * {@code GET  /spendings/:id} : get the "id" spending.
     *
     * @param id the id of the spendingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spendingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/spendings/{id}")
    public ResponseEntity<SpendingDTO> getSpending(@PathVariable Long id) {
        log.debug("REST request to get Spending : {}", id);
        Optional<SpendingDTO> spendingDTO = spendingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spendingDTO);
    }

    /**
     * {@code DELETE  /spendings/:id} : delete the "id" spending.
     *
     * @param id the id of the spendingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/spendings/{id}")
    public ResponseEntity<Void> deleteSpending(@PathVariable Long id) {
        log.debug("REST request to delete Spending : {}", id);
        spendingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
