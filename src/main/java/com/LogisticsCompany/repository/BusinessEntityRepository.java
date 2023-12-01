package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessEntityRepository extends JpaRepository<BusinessEntity, Long> {


}
