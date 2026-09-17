package com.example.employee_curd.service;

import com.example.employee_curd.dto.EmployeeDTO;
import com.example.employee_curd.entity.Employee;
import com.example.employee_curd.exception.ResourceNotFoundException;
import com.example.employee_curd.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // GET ALL
    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // GET BY ID
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee =
                employeeRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );
        return convertToDTO(employee);
    }


    // CREATE
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        Employee employee = convertToEntity(employeeDTO);

        Employee savedEmployee =
                employeeRepository.save(employee);

        return convertToDTO(savedEmployee);
    }

    // UPDATE
    public EmployeeDTO updateEmployee(
            Long id,
            EmployeeDTO employeeDTO) {

        Employee existingEmployee =
                employeeRepository.findById(id).orElse(null);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        existingEmployee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee =
                employeeRepository.save(existingEmployee);

        return convertToDTO(updatedEmployee);
    }

    // DELETE
    public void deleteEmployee(Long id) {

        employeeRepository.deleteById(id);
    }

    // ENTITY → DTO
    private EmployeeDTO convertToDTO(Employee employee) {

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    // DTO → ENTITY
    private Employee convertToEntity(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());

        return employee;
    }
}