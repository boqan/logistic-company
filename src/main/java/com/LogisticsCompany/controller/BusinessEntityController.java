package com.LogisticsCompany.controller;


import com.LogisticsCompany.error.BusinessEntityNotFoundException;
import com.LogisticsCompany.service.BusinessEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BusinessEntityController {

    @Autowired
    private BusinessEntityService businessEntityService;

    @GetMapping("/TESTNAME/{id}")
    public BigDecimal getRevenue(@PathVariable("id") Long id) throws BusinessEntityNotFoundException {
        return businessEntityService.getRevenue(id);
    }




}
