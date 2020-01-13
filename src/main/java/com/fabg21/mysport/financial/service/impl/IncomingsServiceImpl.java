package com.fabg21.mysport.financial.service.impl;

import com.fabg21.mysport.financial.service.IncomingsService;
import com.fabg21.mysport.financial.domain.Incomings;
import com.fabg21.mysport.financial.repository.IncomingsRepository;
import com.fabg21.mysport.financial.service.dto.IncomingsDTO;
import com.fabg21.mysport.financial.service.mapper.IncomingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Incomings}.
 */
@Service
@Transactional
public class IncomingsServiceImpl implements IncomingsService {

    private final Logger log = LoggerFactory.getLogger(IncomingsServiceImpl.class);

    private final IncomingsRepository incomingsRepository;

    private final IncomingsMapper incomingsMapper;

    public IncomingsServiceImpl(IncomingsRepository incomingsRepository, IncomingsMapper incomingsMapper) {
        this.incomingsRepository = incomingsRepository;
        this.incomingsMapper = incomingsMapper;
    }

    /**
     * Save a incomings.
     *
     * @param incomingsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IncomingsDTO save(IncomingsDTO incomingsDTO) {
        log.debug("Request to save Incomings : {}", incomingsDTO);
        Incomings incomings = incomingsMapper.toEntity(incomingsDTO);
        incomings = incomingsRepository.save(incomings);
        return incomingsMapper.toDto(incomings);
    }

    /**
     * Get all the incomings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<IncomingsDTO> findAll() {
        log.debug("Request to get all Incomings");
        return incomingsRepository.findAll().stream()
            .map(incomingsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one incomings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IncomingsDTO> findOne(Long id) {
        log.debug("Request to get Incomings : {}", id);
        return incomingsRepository.findById(id)
            .map(incomingsMapper::toDto);
    }

    /**
     * Delete the incomings by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Incomings : {}", id);
        incomingsRepository.deleteById(id);
    }
}
