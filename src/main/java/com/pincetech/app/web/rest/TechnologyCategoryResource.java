package com.pincetech.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pincetech.app.service.TechnologyCategoryService;
import com.pincetech.app.web.rest.util.HeaderUtil;
import com.pincetech.app.service.dto.TechnologyCategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TechnologyCategory.
 */
@RestController
@RequestMapping("/api")
public class TechnologyCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TechnologyCategoryResource.class);
        
    @Inject
    private TechnologyCategoryService technologyCategoryService;

    /**
     * POST  /technology-categories : Create a new technologyCategory.
     *
     * @param technologyCategoryDTO the technologyCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new technologyCategoryDTO, or with status 400 (Bad Request) if the technologyCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/technology-categories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TechnologyCategoryDTO> createTechnologyCategory(@RequestBody TechnologyCategoryDTO technologyCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save TechnologyCategory : {}", technologyCategoryDTO);
        if (technologyCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("technologyCategory", "idexists", "A new technologyCategory cannot already have an ID")).body(null);
        }
        TechnologyCategoryDTO result = technologyCategoryService.save(technologyCategoryDTO);
        return ResponseEntity.created(new URI("/api/technology-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("technologyCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /technology-categories : Updates an existing technologyCategory.
     *
     * @param technologyCategoryDTO the technologyCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated technologyCategoryDTO,
     * or with status 400 (Bad Request) if the technologyCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the technologyCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/technology-categories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TechnologyCategoryDTO> updateTechnologyCategory(@RequestBody TechnologyCategoryDTO technologyCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update TechnologyCategory : {}", technologyCategoryDTO);
        if (technologyCategoryDTO.getId() == null) {
            return createTechnologyCategory(technologyCategoryDTO);
        }
        TechnologyCategoryDTO result = technologyCategoryService.save(technologyCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("technologyCategory", technologyCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /technology-categories : get all the technologyCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of technologyCategories in body
     */
    @RequestMapping(value = "/technology-categories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TechnologyCategoryDTO> getAllTechnologyCategories() {
        log.debug("REST request to get all TechnologyCategories");
        return technologyCategoryService.findAll();
    }

    /**
     * GET  /technology-categories/:id : get the "id" technologyCategory.
     *
     * @param id the id of the technologyCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the technologyCategoryDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/technology-categories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TechnologyCategoryDTO> getTechnologyCategory(@PathVariable Long id) {
        log.debug("REST request to get TechnologyCategory : {}", id);
        TechnologyCategoryDTO technologyCategoryDTO = technologyCategoryService.findOne(id);
        return Optional.ofNullable(technologyCategoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /technology-categories/:id : delete the "id" technologyCategory.
     *
     * @param id the id of the technologyCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/technology-categories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTechnologyCategory(@PathVariable Long id) {
        log.debug("REST request to delete TechnologyCategory : {}", id);
        technologyCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("technologyCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/technology-categories?query=:query : search for the technologyCategory corresponding
     * to the query.
     *
     * @param query the query of the technologyCategory search 
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/technology-categories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TechnologyCategoryDTO> searchTechnologyCategories(@RequestParam String query) {
        log.debug("REST request to search TechnologyCategories for query {}", query);
        return technologyCategoryService.search(query);
    }


}
