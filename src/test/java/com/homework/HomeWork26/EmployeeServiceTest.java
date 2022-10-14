package com.homework.HomeWork26;

import com.homework.HomeWork26.employees.Employee;
import com.homework.HomeWork26.exception.*;
import com.homework.HomeWork26.services.CheckService;
import com.homework.HomeWork26.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService(new CheckService());

    @ParameterizedTest
    @MethodSource("params")
    public void addTest1(String firstName,
                         String lastName,
                         int department,
                         double salary) {
        Employee expectedEmployee = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.addEmployee(firstName, lastName, department, salary)).isEqualTo(expectedEmployee);
        assertThatExceptionOfType(EmployeeAlreadyAddedException.class).isThrownBy(() -> employeeService.addEmployee(firstName, lastName, department, salary));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void addTest2(String firstName,
                         String lastName,
                         int department,
                         double salary) {
        List<Employee> employees = generateEmployees(5);
        employees.forEach(employee -> assertThat(employeeService.addEmployee(employee.getFirstName(), employee.getLastName(), employee.getDepartment(), employee.getSalary())).isEqualTo(employee));
        assertThatExceptionOfType(EmployeeArrayIsFullException.class).isThrownBy(() -> employeeService.addEmployee(firstName, lastName, department, salary));
    }

    private List<Employee> generateEmployees(int size) {
        return Stream.iterate(1, i -> i + 1).limit(size).map(i -> new Employee("FirstName" + (char) ((int) 'd' + i), "LastName" + (char) ((int) 'e' + i), i, 10000 + i)).collect(Collectors.toList());
    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Margarita", "Popova", 1, 45000),
                Arguments.of("Andrey", "Ivanov", 2, 32500),
                Arguments.of("Mikhail", "Morozov", 3, 49000)
        );
    }

    @Test
    public void addTest3() {
        assertThatExceptionOfType(InvalidFirstNameException.class).isThrownBy(() -> employeeService.addEmployee("Margarita*", "Popova", 1, 45000));
        assertThatExceptionOfType(InvalidFirstNameException.class).isThrownBy(() -> employeeService.addEmployee(null, "Ivanov", 2, 32500));
        assertThatExceptionOfType(InvalidLastNameException.class).isThrownBy(() -> employeeService.addEmployee("Mikhail", "$Morozov", 3, 49000));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findTest1(String firstName,
                          String lastName,
                          int department,
                          double salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> employeeService.findEmployee("TestFirstName", "TestLastName", 0, 0));

        Employee expectedEmployee = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.addEmployee(firstName, lastName, department, salary)).isEqualTo(expectedEmployee);
        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> employeeService.findEmployee("TestFirstName", "TestLastName", 0, 0));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findTest2(String firstName,
                          String lastName,
                          int department,
                          double salary) {
        Employee expectedEmployee = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.addEmployee(firstName, lastName, department, salary)).isEqualTo(expectedEmployee);
        assertThat(employeeService.getAll()).hasSize(1);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeTest1(String firstName,
                            String lastName,
                            int department,
                            double salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> employeeService.removeEmployee("TestFirstName", "TestLastName", 0, 0));

        Employee expectedEmployee = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.addEmployee(firstName, lastName, department, salary)).isEqualTo(expectedEmployee);
        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> employeeService.removeEmployee("TestFirstName", "TestLastName", 0, 0));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeTest2(String firstName,
                            String lastName,
                            int department,
                            double salary) {
        Employee expectedEmployee = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.addEmployee(firstName, lastName, department, salary)).isEqualTo(expectedEmployee);
        assertThat(employeeService.getAll()).isEmpty();
    }
}