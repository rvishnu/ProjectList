package com.pincetech.app.service.mapper;

import com.pincetech.app.domain.*;
import com.pincetech.app.service.dto.ProjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMapper {

    @Mapping(source = "department.id", target = "departmentId")
    ProjectDTO projectToProjectDTO(Project project);

    List<ProjectDTO> projectsToProjectDTOs(List<Project> projects);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "technologies", ignore = true)
    Project projectDTOToProject(ProjectDTO projectDTO);

    List<Project> projectDTOsToProjects(List<ProjectDTO> projectDTOs);

    default Department departmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
