package com.pincetech.app.service.mapper;

import com.pincetech.app.domain.*;
import com.pincetech.app.service.dto.CountryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Country and its DTO CountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryMapper {

    CountryDTO countryToCountryDTO(Country country);

    List<CountryDTO> countriesToCountryDTOs(List<Country> countries);

    Country countryDTOToCountry(CountryDTO countryDTO);

    List<Country> countryDTOsToCountries(List<CountryDTO> countryDTOs);
}
