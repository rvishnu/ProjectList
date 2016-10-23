package com.pincetech.app.service;

import com.pincetech.app.service.dto.TechnologyCategoryDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TechnologyCategory.
 */
public interface TechnologyCategoryService {

    /**
     * Save a technologyCategory.
     *
     * @param technologyCategoryDTO the entity to save
     * @return the persisted entity
     */
    TechnologyCategoryDTO save(TechnologyCategoryDTO technologyCategoryDTO);

    /**
     *  Get all the technologyCategories.
     *  
     *  @return the list of entities
     */
    List<TechnologyCategoryDTO> findAll();

    /**
     *  Get the "id" technologyCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TechnologyCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" technologyCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the technologyCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TechnologyCategoryDTO> search(String query);
}
