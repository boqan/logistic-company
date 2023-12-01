package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.error.BusinessEntityNotFoundException;
import com.LogisticsCompany.model.BusinessEntity;
import com.LogisticsCompany.repository.BusinessEntityRepository;
import com.LogisticsCompany.service.BusinessEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BusinessEntityImpl  implements BusinessEntityService {

    @Autowired
    private BusinessEntityRepository businessEntityRepository;

    @Override
    public BigDecimal getRevenue(Long id) throws BusinessEntityNotFoundException {
        Optional<BusinessEntity> businessEntityOptional = businessEntityRepository.findById(id);
        if(!businessEntityOptional.isPresent()){
            throw new BusinessEntityNotFoundException("BusinessEntity Not Found");
        }
        return businessEntityOptional.get().getRevenue();
    }
}
