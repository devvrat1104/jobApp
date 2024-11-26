package com.javaMsProject.companyMs.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping()
    public ResponseEntity<List<Company>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany( @RequestBody Company company, @PathVariable Long id){
        System.out.println(company.getName()+"inside controller put");
        companyService.updateCompanyById(company,id);
        return new ResponseEntity<>("company updated successfully", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company){
        companyService.createCompany(company);
        return new ResponseEntity<>("Company added successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company company = companyService.getCompanyById(id);
        if(company != null)     return new ResponseEntity<>(company,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable long id){
        boolean isCompanyDeleted = companyService.deleteCompanyById(id);
        if(isCompanyDeleted)    return new ResponseEntity<>("Company deleted successfully",HttpStatus.OK);
        else return new ResponseEntity<>("Internal Server error",HttpStatus.NOT_FOUND);
    }
}
