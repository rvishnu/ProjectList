package com.pincetech.app.service.impl;

import com.pincetech.app.service.TechnologyService;
import com.pincetech.app.domain.Technology;
import com.pincetech.app.repository.TechnologyRepository;
import com.pincetech.app.repository.search.TechnologySearchRepository;
import com.pincetech.app.service.dto.TechnologyDTO;
import com.pincetech.app.service.mapper.TechnologyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Technology.
 */
@Service
@Transactional
public class TechnologyServiceImpl implements TechnologyService{

    private final Logger log = LoggerFactory.getLogger(TechnologyServiceImpl.class);
    
    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private TechnologyMapper technologyMapper;

    @Inject
    private TechnologySearchRepository technologySearchRepository;

    /**
     * Save a technology.
     *
     * @param technologyDTO the entity to save
     * @return the persisted entity
     */
    public TechnologyDTO save(TechnologyDTO technologyDTO) {
        log.debug("Request to save Technology : {}", technologyDTO);
        Technology technology = technologyMapper.technologyDTOToTechnology(technologyDTO);
        technology = technologyRepository.save(technology);
        TechnologyDTO result = technologyMapper.technologyToTechnologyDTO(technology);
        technologySearchRepository.save(technology);
        return result;
    }

    /**
     *  Get all the technologies.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TechnologyDTO> findAll() {
        log.debug("Request to get all Technologies");
        List<TechnologyDTO> result = technologyRepository.findAll().stream()
            .map(technologyMapper::technologyToTechnologyDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one technology by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TechnologyDTO findOne(Long id) {
        log.debug("Request to get Technology : {}", id);
        Technology technology = technologyRepository.findOne(id);
        TechnologyDTO technologyDTO = technologyMapper.technologyToTechnologyDTO(technology);
        return technologyDTO;
    }

    /**
     *  Delete the  technology by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Technology : {}", id);
        technologyRepository.delete(id);
        technologySearchRepository.delete(id);
    }

    /**
     * Search for the technology corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TechnologyDTO> search(String query) {
        log.debug("Request to search Technologies for query {}", query);
        return StreamSupport
            .stream(technologySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(technologyMapper::technologyToTechnologyDTO)
            .collect(Collectors.toList());
    }
}
