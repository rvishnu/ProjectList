package com.pincetech.app.repository.search;

import com.pincetech.app.domain.TechnologyCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TechnologyCategory entity.
 */
public interface TechnologyCategorySearchRepository extends ElasticsearchRepository<TechnologyCategory, Long> {
}
