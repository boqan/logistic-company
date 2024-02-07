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
/**
 * Service class responsible for mapping between Employee and EmployeeDTO objects.
 * It provides methods for converting Employee to EmployeeDTO, DTO to Employee, and lists of employees.
 */
@Service
public class EmployeeMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private OfficeRepository officeRepository;
    /**
     * Convert an Employee object to an EmployeeDTO object.
     * @param employee The Employee object to convert.
     * @return The corresponding EmployeeDTO object.
     * @throws EntityNotFoundException If the associated office is not found.
     */
    public EmployeeDTO EmployeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        // Set properties manually
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setEmployeeType(employee.getEmployeeType());
        if(employee.getId() != null) {
            employeeDTO.setId(employee.getId());
        }
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

    /**
     * Convert an EmployeeDTO object to an Employee object.
     * @param employeeDTO The EmployeeDTO object to convert.
     * @return The corresponding Employee object.
     * @throws EntityNotFoundException If the associated office is not found.
     */
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

    /**
     * Map a list of Employee objects to a list of EmployeeDTO objects.
     * @param employees The list of Employee objects to map.
     * @return A list of corresponding EmployeeDTO objects.
     */
    public List<EmployeeDTO> mapEmployeeListToDTO(List<Employee> employees){
        return employees.stream().map(this::EmployeeToDTO).collect(Collectors.toList());
    }
    /**
     * Map an EmployeeDTO object to an Employee object.
     * @param employeeDTO The EmployeeDTO object to map.
     * @return The corresponding Employee object.
     */
    public Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        return DTOToEmployee(employeeDTO);
    }
    /**
     * Map a list of EmployeeDTO objects to a list of Employee objects.
     * @param employeeDTOS The list of EmployeeDTO objects to map.
     * @return A list of corresponding Employee objects.
     */
    public List<Employee> mapEmployeeList(List<EmployeeDTO> employeeDTOS) {
        return employeeDTOS.stream().map(this::DTOToEmployee).collect(Collectors.toList());
    }
    /**
     * Map an Employee object to an EmployeeDTO object using ModelMapper library.
     * @param employee The Employee object to map.
     * @return The corresponding EmployeeDTO object.
     */
    public EmployeeDTO mapToEmployeeDTO(Employee employee){
        EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
        return dto;
    }
}
