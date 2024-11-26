package com.javaMsProject.companyMs.company;

import com.javaMsProject.companyMs.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    void createCompany(Company company);
    boolean updateCompanyById(Company updatedCompany, Long id);

    Company getCompanyById(Long id);
    boolean deleteCompanyById(Long id);

    public void updateCompanyRating(ReviewMessage reviewMessage);
}
