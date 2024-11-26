package com.javaMsProject.companyMs.company.impl;

import com.javaMsProject.companyMs.company.Company;
import com.javaMsProject.companyMs.company.CompanyRepository;
import com.javaMsProject.companyMs.company.CompanyService;
import com.javaMsProject.companyMs.company.clients.ReviewClient;
import com.javaMsProject.companyMs.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ReviewClient reviewClient;
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);

    }
    @Override
    public boolean updateCompanyById(Company updatedCompany, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent())     {
            Company companyToUpdate = companyOptional.get();
            companyToUpdate.setName(updatedCompany.getName());
            companyToUpdate.setDescription(updatedCompany.getDescription());
            companyRepository.save(companyToUpdate);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        display(company);
        return company;
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        try{
            Optional<Company> companyOptional = companyRepository.findById(id);
            if(companyOptional.isPresent()){
                companyRepository.deleteById(id);
                return true;
            }

            return false;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
//        System.out.println("--------------------"+reviewMessage.getContent());
    Company company = companyRepository.findById(reviewMessage.getCompanyId())
            .orElseThrow(() -> new NotFoundException("Company not found with id : "+reviewMessage.getCompanyId()));
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }

    private void display(Company company){
//        System.out.println("Name ==== "+company.getName());
//        System.out.println("Reviews ==== "+company.getReviews());
    }

}
