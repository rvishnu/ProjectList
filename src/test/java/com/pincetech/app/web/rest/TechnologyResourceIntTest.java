package com.pincetech.app.web.rest;

import com.pincetech.app.ProjectListApp;

import com.pincetech.app.domain.Technology;
import com.pincetech.app.repository.TechnologyRepository;
import com.pincetech.app.service.TechnologyService;
import com.pincetech.app.repository.search.TechnologySearchRepository;
import com.pincetech.app.service.dto.TechnologyDTO;
import com.pincetech.app.service.mapper.TechnologyMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TechnologyResource REST controller.
 *
 * @see TechnologyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectListApp.class)
public class TechnologyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    @Inject
    private TechnologyRepository technologyRepository;

    @Inject
    private TechnologyMapper technologyMapper;

    @Inject
    private TechnologyService technologyService;

    @Inject
    private TechnologySearchRepository technologySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTechnologyMockMvc;

    private Technology technology;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TechnologyResource technologyResource = new TechnologyResource();
        ReflectionTestUtils.setField(technologyResource, "technologyService", technologyService);
        this.restTechnologyMockMvc = MockMvcBuilders.standaloneSetup(technologyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Technology createEntity(EntityManager em) {
        Technology technology = new Technology()
                .name(DEFAULT_NAME)
                .version(DEFAULT_VERSION);
        return technology;
    }

    @Before
    public void initTest() {
        technologySearchRepository.deleteAll();
        technology = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnology() throws Exception {
        int databaseSizeBeforeCreate = technologyRepository.findAll().size();

        // Create the Technology
        TechnologyDTO technologyDTO = technologyMapper.technologyToTechnologyDTO(technology);

        restTechnologyMockMvc.perform(post("/api/technologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technologyDTO)))
                .andExpect(status().isCreated());

        // Validate the Technology in the database
        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeCreate + 1);
        Technology testTechnology = technologies.get(technologies.size() - 1);
        assertThat(testTechnology.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTechnology.getVersion()).isEqualTo(DEFAULT_VERSION);

        // Validate the Technology in ElasticSearch
        Technology technologyEs = technologySearchRepository.findOne(testTechnology.getId());
        assertThat(technologyEs).isEqualToComparingFieldByField(testTechnology);
    }

    @Test
    @Transactional
    public void getAllTechnologies() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get all the technologies
        restTechnologyMockMvc.perform(get("/api/technologies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(technology.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", technology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(technology.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTechnology() throws Exception {
        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);
        technologySearchRepository.save(technology);
        int databaseSizeBeforeUpdate = technologyRepository.findAll().size();

        // Update the technology
        Technology updatedTechnology = technologyRepository.findOne(technology.getId());
        updatedTechnology
                .name(UPDATED_NAME)
                .version(UPDATED_VERSION);
        TechnologyDTO technologyDTO = technologyMapper.technologyToTechnologyDTO(updatedTechnology);

        restTechnologyMockMvc.perform(put("/api/technologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technologyDTO)))
                .andExpect(status().isOk());

        // Validate the Technology in the database
        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeUpdate);
        Technology testTechnology = technologies.get(technologies.size() - 1);
        assertThat(testTechnology.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTechnology.getVersion()).isEqualTo(UPDATED_VERSION);

        // Validate the Technology in ElasticSearch
        Technology technologyEs = technologySearchRepository.findOne(testTechnology.getId());
        assertThat(technologyEs).isEqualToComparingFieldByField(testTechnology);
    }

    @Test
    @Transactional
    public void deleteTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);
        technologySearchRepository.save(technology);
        int databaseSizeBeforeDelete = technologyRepository.findAll().size();

        // Get the technology
        restTechnologyMockMvc.perform(delete("/api/technologies/{id}", technology.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean technologyExistsInEs = technologySearchRepository.exists(technology.getId());
        assertThat(technologyExistsInEs).isFalse();

        // Validate the database is empty
        List<Technology> technologies = technologyRepository.findAll();
        assertThat(technologies).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);
        technologySearchRepository.save(technology);

        // Search the technology
        restTechnologyMockMvc.perform(get("/api/_search/technologies?query=id:" + technology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technology.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
}
