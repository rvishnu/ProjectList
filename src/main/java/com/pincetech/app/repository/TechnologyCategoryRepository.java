package com.pincetech.app.repository;

import com.pincetech.app.domain.TechnologyCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TechnologyCategory entity.
 */
@SuppressWarnings("unused")
public interface TechnologyCategoryRepository extends JpaRepository<TechnologyCategory,Long> {

}
