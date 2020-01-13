package com.fabg21.mysport.financial.service;

import com.fabg21.mysport.financial.service.dto.SpendingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fabg21.mysport.financial.domain.Spending}.
 */
public interface SpendingService {

    /**
     * Save a spending.
     *
     * @param spendingDTO the entity to save.
     * @return the persisted entity.
     */
    SpendingDTO save(SpendingDTO spendingDTO);

    /**
     * Get all the spendings.
     *
     * @return the list of entities.
     */
    List<SpendingDTO> findAll();


    /**
     * Get the "id" spending.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpendingDTO> findOne(Long id);

    /**
     * Delete the "id" spending.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
