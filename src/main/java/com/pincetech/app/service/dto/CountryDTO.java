package com.pincetech.app.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Country entity.
 */
public class CountryDTO implements Serializable {

    private Long id;

    private String countryName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;

        if ( ! Objects.equals(id, countryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + id +
            ", countryName='" + countryName + "'" +
            '}';
    }
}
