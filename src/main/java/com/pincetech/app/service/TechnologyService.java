package com.pincetech.app.service;

import com.pincetech.app.service.dto.TechnologyDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Technology.
 */
public interface TechnologyService {

    /**
     * Save a technology.
     *
     * @param technologyDTO the entity to save
     * @return the persisted entity
     */
    TechnologyDTO save(TechnologyDTO technologyDTO);

    /**
     *  Get all the technologies.
     *  
     *  @return the list of entities
     */
    List<TechnologyDTO> findAll();

    /**
     *  Get the "id" technology.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TechnologyDTO findOne(Long id);

    /**
     *  Delete the "id" technology.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the technology corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TechnologyDTO> search(String query);
}
