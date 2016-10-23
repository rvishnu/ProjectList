package com.pincetech.app.service.mapper;

import com.pincetech.app.domain.*;
import com.pincetech.app.service.dto.TechnologyCategoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TechnologyCategory and its DTO TechnologyCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TechnologyCategoryMapper {

    TechnologyCategoryDTO technologyCategoryToTechnologyCategoryDTO(TechnologyCategory technologyCategory);

    List<TechnologyCategoryDTO> technologyCategoriesToTechnologyCategoryDTOs(List<TechnologyCategory> technologyCategories);

    TechnologyCategory technologyCategoryDTOToTechnologyCategory(TechnologyCategoryDTO technologyCategoryDTO);

    List<TechnologyCategory> technologyCategoryDTOsToTechnologyCategories(List<TechnologyCategoryDTO> technologyCategoryDTOs);
}
