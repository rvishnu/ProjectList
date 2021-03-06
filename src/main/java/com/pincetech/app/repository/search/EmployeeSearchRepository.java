package com.pincetech.app.repository.search;

import com.pincetech.app.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Employee entity.
 */
public interface EmployeeSearchRepository extends ElasticsearchRepository<Employee, Long> {
}
