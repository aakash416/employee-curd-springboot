package com.example.employee_curd.controller;

import com.example.employee_curd.dto.EmployeeDTO;
import com.example.employee_curd.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.createEmployee(employeeDTO);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "Employee deleted successfully";
    }
}