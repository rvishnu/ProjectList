package com.pincetech.app.repository.search;

import com.pincetech.app.domain.Technology;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Technology entity.
 */
public interface TechnologySearchRepository extends ElasticsearchRepository<Technology, Long> {
}
