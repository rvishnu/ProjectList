package com.pincetech.app.service.mapper;

import com.pincetech.app.domain.*;
import com.pincetech.app.service.dto.TechnologyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Technology and its DTO TechnologyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TechnologyMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "technologyCategory.id", target = "technologyCategoryId")
    TechnologyDTO technologyToTechnologyDTO(Technology technology);

    List<TechnologyDTO> technologiesToTechnologyDTOs(List<Technology> technologies);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "technologyCategoryId", target = "technologyCategory")
    Technology technologyDTOToTechnology(TechnologyDTO technologyDTO);

    List<Technology> technologyDTOsToTechnologies(List<TechnologyDTO> technologyDTOs);

    default Project projectFromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }

    default TechnologyCategory technologyCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        TechnologyCategory technologyCategory = new TechnologyCategory();
        technologyCategory.setId(id);
        return technologyCategory;
    }
}
