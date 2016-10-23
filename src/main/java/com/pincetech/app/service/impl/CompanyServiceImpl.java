package com.pincetech.app.service.impl;

import com.pincetech.app.service.CompanyService;
import com.pincetech.app.domain.Company;
import com.pincetech.app.repository.CompanyRepository;
import com.pincetech.app.repository.search.CompanySearchRepository;
import com.pincetech.app.service.dto.CompanyDTO;
import com.pincetech.app.service.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
    
    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private CompanyMapper companyMapper;

    @Inject
    private CompanySearchRepository companySearchRepository;

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.companyDTOToCompany(companyDTO);
        company = companyRepository.save(company);
        CompanyDTO result = companyMapper.companyToCompanyDTO(company);
        companySearchRepository.save(company);
        return result;
    }

    /**
     *  Get all the companies.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CompanyDTO> findAll() {
        log.debug("Request to get all Companies");
        List<CompanyDTO> result = companyRepository.findAll().stream()
            .map(companyMapper::companyToCompanyDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one company by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CompanyDTO findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOne(id);
        CompanyDTO companyDTO = companyMapper.companyToCompanyDTO(company);
        return companyDTO;
    }

    /**
     *  Delete the  company by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
        companySearchRepository.delete(id);
    }

    /**
     * Search for the company corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> search(String query) {
        log.debug("Request to search Companies for query {}", query);
        return StreamSupport
            .stream(companySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(companyMapper::companyToCompanyDTO)
            .collect(Collectors.toList());
    }
}
