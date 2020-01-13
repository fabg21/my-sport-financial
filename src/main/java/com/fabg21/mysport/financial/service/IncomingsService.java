package com.fabg21.mysport.financial.service;

import com.fabg21.mysport.financial.service.dto.IncomingsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fabg21.mysport.financial.domain.Incomings}.
 */
public interface IncomingsService {

    /**
     * Save a incomings.
     *
     * @param incomingsDTO the entity to save.
     * @return the persisted entity.
     */
    IncomingsDTO save(IncomingsDTO incomingsDTO);

    /**
     * Get all the incomings.
     *
     * @return the list of entities.
     */
    List<IncomingsDTO> findAll();


    /**
     * Get the "id" incomings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncomingsDTO> findOne(Long id);

    /**
     * Delete the "id" incomings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
