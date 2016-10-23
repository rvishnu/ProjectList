package com.pincetech.app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Technology entity.
 */
public class TechnologyDTO implements Serializable {

    private Long id;

    private String name;

    private String version;


    private Long projectId;
    
    private Long technologyCategoryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTechnologyCategoryId() {
        return technologyCategoryId;
    }

    public void setTechnologyCategoryId(Long technologyCategoryId) {
        this.technologyCategoryId = technologyCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TechnologyDTO technologyDTO = (TechnologyDTO) o;

        if ( ! Objects.equals(id, technologyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TechnologyDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", version='" + version + "'" +
            '}';
    }
}
