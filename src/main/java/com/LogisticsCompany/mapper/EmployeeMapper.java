package com.LogisticsCompany.mapper;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.repository.OfficeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private OfficeRepository officeRepository; // Inject OfficeRepository

    // EmployeeDTO
    public EmployeeDTO mapToEmployeeDTO(Employee employee){
        EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
        return dto;
    }

    public List<EmployeeDTO> mapEmployeeListToDTO(List<Employee> employees){
        return employees.stream().map(this::mapToEmployeeDTO).collect(Collectors.toList());
    }

    public Employee DTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        // Set properties manually
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setEmployeeType(employeeDTO.getEmployeeType());

        // Assuming that employeeDTO has an 'officeId' property
        if (employeeDTO.getOfficeID() != null) {
            // Retrieve the Office from the repository using the officeId
            Optional<Office> officeOptional = officeRepository.findById(employeeDTO.getOfficeID());

            // Check if the office exists in the database
            if (officeOptional.isPresent()) {
                employee.setOffice(officeOptional.get());
            } else {
                // Handle the case where the office is not found (throw an exception or handle it based on your requirements)
                throw new EntityNotFoundException("Office with ID " + employeeDTO.getOfficeID() + " not found");
            }
        }

        return employee;
    }

    public List<Employee> mapEmployeeList(List<EmployeeDTO> employeeDTOS) {
        return employeeDTOS.stream().map(this::DTOToEmployee).collect(Collectors.toList());
    }
}
