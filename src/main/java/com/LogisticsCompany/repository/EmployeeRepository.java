package com.LogisticsCompany.repository;

import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for managing employees.
 * This interface provides methods for querying employees by name and employee type.
 */
@Repository
public interface EmployeeRepository extends JpaRepository< Employee, Long> {
    Optional<Employee> findByName(String name);
    List<Employee> findAllByEmployeeType(EmployeeType employeeType);
}