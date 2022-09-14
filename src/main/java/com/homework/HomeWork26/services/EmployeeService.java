package com.homework.HomeWork26.services;

import org.springframework.stereotype.Service;
import com.homework.HomeWork26.employees.Employee;
import com.homework.HomeWork26.exception.EmployeeAlreadyAddedException;
import com.homework.HomeWork26.exception.EmployeeNotFoundException;
import com.homework.HomeWork26.exception.EmployeeArrayIsFullException;

import java.util.*;

@Service
public class EmployeeService {
    private static final int countEmployees = 5;

    private final Map<String, Employee> employees;

    public EmployeeService() {
        this.employees = new HashMap<>();
    }

    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    public Employee addEmployee(String firstName, String lastName, int department, double salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (employees.containsKey(employee.getFullName())) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < countEmployees) {
            employees.put(employee.getFullName(), employee);
            return employee;
        }
        throw new EmployeeArrayIsFullException();
    }

    public Employee findEmployee(String firstName, String lastName, int department, double salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (employees.containsKey(employee.getFullName())) {
            return employees.get(employee.getFullName());
        }
        throw new EmployeeNotFoundException();
    }

    public Employee removeEmployee(String firstName, String lastName, int department, double salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (employees.containsKey(employee.getFullName())) {
            return employees.remove(employee.getFullName());
        }
        throw new EmployeeNotFoundException();
    }
}