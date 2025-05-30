package com.example.demo.service;


import com.example.demo.domain.entity.Employee;
import com.example.demo.infrastructure.repository.EmployeeRepo;
import com.example.demo.domain.dto.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    public EmployeeDTO getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return mapper.map(employee.get(), EmployeeDTO.class);
        } else {
            return null; // or throw exception, your choice
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream()
                .map(employee -> mapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapper.map(employeeDTO, Employee.class);
        Employee saved = employeeRepo.save(employee);
        return mapper.map(saved, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(int id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingOpt = employeeRepo.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }
        Employee existing = existingOpt.get();
        existing.setName(employeeDTO.getName());
        existing.setEmail(employeeDTO.getEmail());
        existing.setAge(employeeDTO.getAge());
        Employee saved = employeeRepo.save(existing);
        return mapper.map(saved, EmployeeDTO.class);
    }

    public boolean deleteEmployee(int id) {
        Optional<Employee> existingOpt = employeeRepo.findById(id);
        if (existingOpt.isEmpty()) {
            return false;
        }
        employeeRepo.deleteById(id);
        return true;
    }



}