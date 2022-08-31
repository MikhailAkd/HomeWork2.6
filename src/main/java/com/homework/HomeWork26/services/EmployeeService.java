package com.homework.HomeWork26.services;

import org.springframework.stereotype.Service;
import com.homework.HomeWork26.employees.Employee;
import com.homework.HomeWork26.exception.EmployeeAlreadyAddedException;
import com.homework.HomeWork26.exception.EmployeeNotFoundException;
import com.homework.HomeWork26.exception.EmployeeArrayIsFullException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private static final int countEmployees = 5;

    private final List<Employee> employees;

    public EmployeeService() {
        this.employees = new ArrayList<>();
    }

    public List<Employee> getAll() {
        return Collections.unmodifiableList(employees);
    }

    public Employee addEmployee(String firstName,
                                String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < countEmployees) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeArrayIsFullException();
    }

    public Employee findEmployee(String firstName,
                                   String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException();
    }

    public Employee removeEmployee(String firstName,
                                 String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            employees.remove(employee);
            return employee;
        }
        throw new EmployeeNotFoundException();
    }
}