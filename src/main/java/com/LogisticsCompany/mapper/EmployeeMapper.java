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
    private OfficeRepository officeRepository;

    // Those methods are written by hand
    public EmployeeDTO EmployeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        // Set properties manually
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setEmployeeType(employee.getEmployeeType());

        // Assuming that employee has an 'office' property
        if (employee.getOffice() != null) {
            // Set the officeId property of the DTO
            employeeDTO.setOfficeID(employee.getOffice().getId());
        }
        else {
            // Handle the case where the office is not found
            throw new EntityNotFoundException("Office with ID " + employeeDTO.getOfficeID() + " is not found");
        }

        return employeeDTO;
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
                // Handle the case where the office is not found
                throw new EntityNotFoundException("Office with ID " + employeeDTO.getOfficeID() + " is not found");
            }
        }

        return employee;
    }

    // Those methods use the ModelMapper library
    public List<EmployeeDTO> mapEmployeeListToDTO(List<Employee> employees){
        return employees.stream().map(this::EmployeeToDTO).collect(Collectors.toList());
    }

    public Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        return DTOToEmployee(employeeDTO);
    }

    public List<Employee> mapEmployeeList(List<EmployeeDTO> employeeDTOS) {
        return employeeDTOS.stream().map(this::DTOToEmployee).collect(Collectors.toList());
    }

    public EmployeeDTO mapToEmployeeDTO(Employee employee){
        EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
        return dto;
    }

}
