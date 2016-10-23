package com.pincetech.app.repository;

import com.pincetech.app.domain.Technology;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Technology entity.
 */
@SuppressWarnings("unused")
public interface TechnologyRepository extends JpaRepository<Technology,Long> {

}
