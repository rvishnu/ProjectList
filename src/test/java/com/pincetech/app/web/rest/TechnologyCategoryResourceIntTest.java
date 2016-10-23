package com.pincetech.app.web.rest;

import com.pincetech.app.ProjectListApp;

import com.pincetech.app.domain.TechnologyCategory;
import com.pincetech.app.repository.TechnologyCategoryRepository;
import com.pincetech.app.service.TechnologyCategoryService;
import com.pincetech.app.repository.search.TechnologyCategorySearchRepository;
import com.pincetech.app.service.dto.TechnologyCategoryDTO;
import com.pincetech.app.service.mapper.TechnologyCategoryMapper;

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
 * Test class for the TechnologyCategoryResource REST controller.
 *
 * @see TechnologyCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectListApp.class)
public class TechnologyCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_ID = "AAAAA";
    private static final String UPDATED_CATEGORY_ID = "BBBBB";

    private static final String DEFAULT_PARENT_CATEGORY_ID = "AAAAA";
    private static final String UPDATED_PARENT_CATEGORY_ID = "BBBBB";

    private static final String DEFAULT_PATH = "AAAAA";
    private static final String UPDATED_PATH = "BBBBB";

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private TechnologyCategoryRepository technologyCategoryRepository;

    @Inject
    private TechnologyCategoryMapper technologyCategoryMapper;

    @Inject
    private TechnologyCategoryService technologyCategoryService;

    @Inject
    private TechnologyCategorySearchRepository technologyCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTechnologyCategoryMockMvc;

    private TechnologyCategory technologyCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TechnologyCategoryResource technologyCategoryResource = new TechnologyCategoryResource();
        ReflectionTestUtils.setField(technologyCategoryResource, "technologyCategoryService", technologyCategoryService);
        this.restTechnologyCategoryMockMvc = MockMvcBuilders.standaloneSetup(technologyCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechnologyCategory createEntity(EntityManager em) {
        TechnologyCategory technologyCategory = new TechnologyCategory()
                .categoryId(DEFAULT_CATEGORY_ID)
                .parentCategoryId(DEFAULT_PARENT_CATEGORY_ID)
                .path(DEFAULT_PATH)
                .name(DEFAULT_NAME);
        return technologyCategory;
    }

    @Before
    public void initTest() {
        technologyCategorySearchRepository.deleteAll();
        technologyCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnologyCategory() throws Exception {
        int databaseSizeBeforeCreate = technologyCategoryRepository.findAll().size();

        // Create the TechnologyCategory
        TechnologyCategoryDTO technologyCategoryDTO = technologyCategoryMapper.technologyCategoryToTechnologyCategoryDTO(technologyCategory);

        restTechnologyCategoryMockMvc.perform(post("/api/technology-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technologyCategoryDTO)))
                .andExpect(status().isCreated());

        // Validate the TechnologyCategory in the database
        List<TechnologyCategory> technologyCategories = technologyCategoryRepository.findAll();
        assertThat(technologyCategories).hasSize(databaseSizeBeforeCreate + 1);
        TechnologyCategory testTechnologyCategory = technologyCategories.get(technologyCategories.size() - 1);
        assertThat(testTechnologyCategory.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testTechnologyCategory.getParentCategoryId()).isEqualTo(DEFAULT_PARENT_CATEGORY_ID);
        assertThat(testTechnologyCategory.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testTechnologyCategory.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the TechnologyCategory in ElasticSearch
        TechnologyCategory technologyCategoryEs = technologyCategorySearchRepository.findOne(testTechnologyCategory.getId());
        assertThat(technologyCategoryEs).isEqualToComparingFieldByField(testTechnologyCategory);
    }

    @Test
    @Transactional
    public void getAllTechnologyCategories() throws Exception {
        // Initialize the database
        technologyCategoryRepository.saveAndFlush(technologyCategory);

        // Get all the technologyCategories
        restTechnologyCategoryMockMvc.perform(get("/api/technology-categories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(technologyCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
                .andExpect(jsonPath("$.[*].parentCategoryId").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.toString())))
                .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTechnologyCategory() throws Exception {
        // Initialize the database
        technologyCategoryRepository.saveAndFlush(technologyCategory);

        // Get the technologyCategory
        restTechnologyCategoryMockMvc.perform(get("/api/technology-categories/{id}", technologyCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(technologyCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.parentCategoryId").value(DEFAULT_PARENT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTechnologyCategory() throws Exception {
        // Get the technologyCategory
        restTechnologyCategoryMockMvc.perform(get("/api/technology-categories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnologyCategory() throws Exception {
        // Initialize the database
        technologyCategoryRepository.saveAndFlush(technologyCategory);
        technologyCategorySearchRepository.save(technologyCategory);
        int databaseSizeBeforeUpdate = technologyCategoryRepository.findAll().size();

        // Update the technologyCategory
        TechnologyCategory updatedTechnologyCategory = technologyCategoryRepository.findOne(technologyCategory.getId());
        updatedTechnologyCategory
                .categoryId(UPDATED_CATEGORY_ID)
                .parentCategoryId(UPDATED_PARENT_CATEGORY_ID)
                .path(UPDATED_PATH)
                .name(UPDATED_NAME);
        TechnologyCategoryDTO technologyCategoryDTO = technologyCategoryMapper.technologyCategoryToTechnologyCategoryDTO(updatedTechnologyCategory);

        restTechnologyCategoryMockMvc.perform(put("/api/technology-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(technologyCategoryDTO)))
                .andExpect(status().isOk());

        // Validate the TechnologyCategory in the database
        List<TechnologyCategory> technologyCategories = technologyCategoryRepository.findAll();
        assertThat(technologyCategories).hasSize(databaseSizeBeforeUpdate);
        TechnologyCategory testTechnologyCategory = technologyCategories.get(technologyCategories.size() - 1);
        assertThat(testTechnologyCategory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testTechnologyCategory.getParentCategoryId()).isEqualTo(UPDATED_PARENT_CATEGORY_ID);
        assertThat(testTechnologyCategory.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testTechnologyCategory.getName()).isEqualTo(UPDATED_NAME);

        // Validate the TechnologyCategory in ElasticSearch
        TechnologyCategory technologyCategoryEs = technologyCategorySearchRepository.findOne(testTechnologyCategory.getId());
        assertThat(technologyCategoryEs).isEqualToComparingFieldByField(testTechnologyCategory);
    }

    @Test
    @Transactional
    public void deleteTechnologyCategory() throws Exception {
        // Initialize the database
        technologyCategoryRepository.saveAndFlush(technologyCategory);
        technologyCategorySearchRepository.save(technologyCategory);
        int databaseSizeBeforeDelete = technologyCategoryRepository.findAll().size();

        // Get the technologyCategory
        restTechnologyCategoryMockMvc.perform(delete("/api/technology-categories/{id}", technologyCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean technologyCategoryExistsInEs = technologyCategorySearchRepository.exists(technologyCategory.getId());
        assertThat(technologyCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<TechnologyCategory> technologyCategories = technologyCategoryRepository.findAll();
        assertThat(technologyCategories).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTechnologyCategory() throws Exception {
        // Initialize the database
        technologyCategoryRepository.saveAndFlush(technologyCategory);
        technologyCategorySearchRepository.save(technologyCategory);

        // Search the technologyCategory
        restTechnologyCategoryMockMvc.perform(get("/api/_search/technology-categories?query=id:" + technologyCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technologyCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].parentCategoryId").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
