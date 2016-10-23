package com.pincetech.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Technology.
 */
@Entity
@Table(name = "technology")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "technology")
public class Technology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @ManyToOne
    private Project project;

    @OneToOne
    @JoinColumn(unique = true)
    private TechnologyCategory technologyCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Technology name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public Technology version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Project getProject() {
        return project;
    }

    public Technology project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TechnologyCategory getTechnologyCategory() {
        return technologyCategory;
    }

    public Technology technologyCategory(TechnologyCategory technologyCategory) {
        this.technologyCategory = technologyCategory;
        return this;
    }

    public void setTechnologyCategory(TechnologyCategory technologyCategory) {
        this.technologyCategory = technologyCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Technology technology = (Technology) o;
        if(technology.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, technology.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Technology{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", version='" + version + "'" +
            '}';
    }
}
