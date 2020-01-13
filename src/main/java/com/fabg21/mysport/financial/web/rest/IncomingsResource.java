package com.fabg21.mysport.financial.web.rest;

import com.fabg21.mysport.financial.service.IncomingsService;
import com.fabg21.mysport.financial.web.rest.errors.BadRequestAlertException;
import com.fabg21.mysport.financial.service.dto.IncomingsDTO;

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
 * REST controller for managing {@link com.fabg21.mysport.financial.domain.Incomings}.
 */
@RestController
@RequestMapping("/api")
public class IncomingsResource {

    private final Logger log = LoggerFactory.getLogger(IncomingsResource.class);

    private static final String ENTITY_NAME = "mySportFinancialIncomings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomingsService incomingsService;

    public IncomingsResource(IncomingsService incomingsService) {
        this.incomingsService = incomingsService;
    }

    /**
     * {@code POST  /incomings} : Create a new incomings.
     *
     * @param incomingsDTO the incomingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomingsDTO, or with status {@code 400 (Bad Request)} if the incomings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incomings")
    public ResponseEntity<IncomingsDTO> createIncomings(@RequestBody IncomingsDTO incomingsDTO) throws URISyntaxException {
        log.debug("REST request to save Incomings : {}", incomingsDTO);
        if (incomingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomingsDTO result = incomingsService.save(incomingsDTO);
        return ResponseEntity.created(new URI("/api/incomings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incomings} : Updates an existing incomings.
     *
     * @param incomingsDTO the incomingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomingsDTO,
     * or with status {@code 400 (Bad Request)} if the incomingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incomings")
    public ResponseEntity<IncomingsDTO> updateIncomings(@RequestBody IncomingsDTO incomingsDTO) throws URISyntaxException {
        log.debug("REST request to update Incomings : {}", incomingsDTO);
        if (incomingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomingsDTO result = incomingsService.save(incomingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incomingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incomings} : get all the incomings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomings in body.
     */
    @GetMapping("/incomings")
    public List<IncomingsDTO> getAllIncomings() {
        log.debug("REST request to get all Incomings");
        return incomingsService.findAll();
    }

    /**
     * {@code GET  /incomings/:id} : get the "id" incomings.
     *
     * @param id the id of the incomingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incomings/{id}")
    public ResponseEntity<IncomingsDTO> getIncomings(@PathVariable Long id) {
        log.debug("REST request to get Incomings : {}", id);
        Optional<IncomingsDTO> incomingsDTO = incomingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomingsDTO);
    }

    /**
     * {@code DELETE  /incomings/:id} : delete the "id" incomings.
     *
     * @param id the id of the incomingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incomings/{id}")
    public ResponseEntity<Void> deleteIncomings(@PathVariable Long id) {
        log.debug("REST request to delete Incomings : {}", id);
        incomingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
