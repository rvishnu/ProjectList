package com.pincetech.app.service.impl;

import com.pincetech.app.service.TechnologyCategoryService;
import com.pincetech.app.domain.TechnologyCategory;
import com.pincetech.app.repository.TechnologyCategoryRepository;
import com.pincetech.app.repository.search.TechnologyCategorySearchRepository;
import com.pincetech.app.service.dto.TechnologyCategoryDTO;
import com.pincetech.app.service.mapper.TechnologyCategoryMapper;
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
 * Service Implementation for managing TechnologyCategory.
 */
@Service
@Transactional
public class TechnologyCategoryServiceImpl implements TechnologyCategoryService{

    private final Logger log = LoggerFactory.getLogger(TechnologyCategoryServiceImpl.class);
    
    @Inject
    private TechnologyCategoryRepository technologyCategoryRepository;

    @Inject
    private TechnologyCategoryMapper technologyCategoryMapper;

    @Inject
    private TechnologyCategorySearchRepository technologyCategorySearchRepository;

    /**
     * Save a technologyCategory.
     *
     * @param technologyCategoryDTO the entity to save
     * @return the persisted entity
     */
    public TechnologyCategoryDTO save(TechnologyCategoryDTO technologyCategoryDTO) {
        log.debug("Request to save TechnologyCategory : {}", technologyCategoryDTO);
        TechnologyCategory technologyCategory = technologyCategoryMapper.technologyCategoryDTOToTechnologyCategory(technologyCategoryDTO);
        technologyCategory = technologyCategoryRepository.save(technologyCategory);
        TechnologyCategoryDTO result = technologyCategoryMapper.technologyCategoryToTechnologyCategoryDTO(technologyCategory);
        technologyCategorySearchRepository.save(technologyCategory);
        return result;
    }

    /**
     *  Get all the technologyCategories.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TechnologyCategoryDTO> findAll() {
        log.debug("Request to get all TechnologyCategories");
        List<TechnologyCategoryDTO> result = technologyCategoryRepository.findAll().stream()
            .map(technologyCategoryMapper::technologyCategoryToTechnologyCategoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one technologyCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TechnologyCategoryDTO findOne(Long id) {
        log.debug("Request to get TechnologyCategory : {}", id);
        TechnologyCategory technologyCategory = technologyCategoryRepository.findOne(id);
        TechnologyCategoryDTO technologyCategoryDTO = technologyCategoryMapper.technologyCategoryToTechnologyCategoryDTO(technologyCategory);
        return technologyCategoryDTO;
    }

    /**
     *  Delete the  technologyCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TechnologyCategory : {}", id);
        technologyCategoryRepository.delete(id);
        technologyCategorySearchRepository.delete(id);
    }

    /**
     * Search for the technologyCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TechnologyCategoryDTO> search(String query) {
        log.debug("Request to search TechnologyCategories for query {}", query);
        return StreamSupport
            .stream(technologyCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(technologyCategoryMapper::technologyCategoryToTechnologyCategoryDTO)
            .collect(Collectors.toList());
    }
}
