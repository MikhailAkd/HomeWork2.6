package com.homework.HomeWork26.controller;

import com.homework.HomeWork26.employees.Employee;
import com.homework.HomeWork26.services.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee employeeMaxSalaryDepartment(@RequestParam int department) {
        return departmentService.employeeMaxSalaryDepartment(department);
    }

    @GetMapping("/min-salary")
    public Employee employeeMinSalaryDepartment(@RequestParam int department) {
        return departmentService.employeeMinSalaryDepartment(department);
    }

    @GetMapping(value = "/all", params = "Id")
    public List<Employee> employeesDepartment(@RequestParam ("Id") int department) {
        return departmentService.employeesDepartment(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> employeesAllDepartments() {
        return departmentService.employeesAllDepartments();
    }
}
