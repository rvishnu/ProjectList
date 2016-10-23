package com.pincetech.app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Project entity.
 */
public class ProjectDTO implements Serializable {

    private Long id;

    private String name;

    private String url;

    private String sourceCodeLocation;

    private Boolean vendorProduct;


    private Long departmentId;
    
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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getSourceCodeLocation() {
        return sourceCodeLocation;
    }

    public void setSourceCodeLocation(String sourceCodeLocation) {
        this.sourceCodeLocation = sourceCodeLocation;
    }
    public Boolean getVendorProduct() {
        return vendorProduct;
    }

    public void setVendorProduct(Boolean vendorProduct) {
        this.vendorProduct = vendorProduct;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;

        if ( ! Objects.equals(id, projectDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", url='" + url + "'" +
            ", sourceCodeLocation='" + sourceCodeLocation + "'" +
            ", vendorProduct='" + vendorProduct + "'" +
            '}';
    }
}
