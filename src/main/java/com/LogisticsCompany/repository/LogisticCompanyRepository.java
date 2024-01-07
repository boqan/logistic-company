package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.LogisticCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticCompanyRepository extends JpaRepository<LogisticCompany, Long> {


}
