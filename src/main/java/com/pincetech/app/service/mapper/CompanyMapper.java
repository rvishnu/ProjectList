package com.pincetech.app.service.mapper;

import com.pincetech.app.domain.*;
import com.pincetech.app.service.dto.CompanyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper {

    CompanyDTO companyToCompanyDTO(Company company);

    List<CompanyDTO> companiesToCompanyDTOs(List<Company> companies);

    @Mapping(target = "departments", ignore = true)
    Company companyDTOToCompany(CompanyDTO companyDTO);

    List<Company> companyDTOsToCompanies(List<CompanyDTO> companyDTOs);
}
