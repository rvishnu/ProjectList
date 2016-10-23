package com.pincetech.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "source_code_location")
    private String sourceCodeLocation;

    @Column(name = "vendor_product")
    private Boolean vendorProduct;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Technology> technologies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public Project url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceCodeLocation() {
        return sourceCodeLocation;
    }

    public Project sourceCodeLocation(String sourceCodeLocation) {
        this.sourceCodeLocation = sourceCodeLocation;
        return this;
    }

    public void setSourceCodeLocation(String sourceCodeLocation) {
        this.sourceCodeLocation = sourceCodeLocation;
    }

    public Boolean isVendorProduct() {
        return vendorProduct;
    }

    public Project vendorProduct(Boolean vendorProduct) {
        this.vendorProduct = vendorProduct;
        return this;
    }

    public void setVendorProduct(Boolean vendorProduct) {
        this.vendorProduct = vendorProduct;
    }

    public Department getDepartment() {
        return department;
    }

    public Project department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Project employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Project addEmployee(Employee employee) {
        employees.add(employee);
        employee.setProject(this);
        return this;
    }

    public Project removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setProject(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public Project technologies(Set<Technology> technologies) {
        this.technologies = technologies;
        return this;
    }

    public Project addTechnology(Technology technology) {
        technologies.add(technology);
        technology.setProject(this);
        return this;
    }

    public Project removeTechnology(Technology technology) {
        technologies.remove(technology);
        technology.setProject(null);
        return this;
    }

    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if(project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", url='" + url + "'" +
            ", sourceCodeLocation='" + sourceCodeLocation + "'" +
            ", vendorProduct='" + vendorProduct + "'" +
            '}';
    }
}
