package com.pincetech.app.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TechnologyCategory entity.
 */
public class TechnologyCategoryDTO implements Serializable {

    private Long id;

    private String categoryId;

    private String parentCategoryId;

    private String path;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TechnologyCategoryDTO technologyCategoryDTO = (TechnologyCategoryDTO) o;

        if ( ! Objects.equals(id, technologyCategoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TechnologyCategoryDTO{" +
            "id=" + id +
            ", categoryId='" + categoryId + "'" +
            ", parentCategoryId='" + parentCategoryId + "'" +
            ", path='" + path + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
