package com.LogisticsCompany.service;

import com.LogisticsCompany.error.BusinessEntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public interface BusinessEntityService {
    BigDecimal getRevenue(Long id) throws BusinessEntityNotFoundException;

}
