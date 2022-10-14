package com.homework.HomeWork26;

import com.homework.HomeWork26.employees.Employee;
import com.homework.HomeWork26.exception.EmployeeNotFoundException;
import com.homework.HomeWork26.services.DepartmentService;
import com.homework.HomeWork26.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Milana", "Belova", 1, 35000),
                new Employee("Stepan", "Maximov", 2, 41000),
                new Employee("Xenia", "Alexandrova", 3, 52000),
                new Employee("Ilya", "Glukhov", 4, 29000),
                new Employee("Dmitry", "Volkov", 5, 32500)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @Test
    public void employeesFromDepartmentTest() {
        assertThat(departmentService.employeesDepartment(6)).isEmpty();
    }

    @Test
    public void emptyDepartmentTest() {
        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        assertThat(departmentService.employeesAllDepartments()).isEmpty();
        assertThat(departmentService.employeesDepartment(3)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("employeeMaxSalaryParams")
    public void employeeMaxSalaryDepartmentTest1(int departmentNumber, Employee expectedEmployee) {
        assertThat(departmentService.employeeMaxSalaryDepartment(departmentNumber).isEqualTo(expectedEmployee));
    }

    @Test
    public void employeeMaxSalaryDepartmentTest2() {
        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> departmentService.employeeMaxSalaryDepartment(1));
    }

    @ParameterizedTest
    @MethodSource("employeeMinSalaryParams")
    public void employeeMinSalaryDepartmentTest1(int departmentNumber, Employee expectedEmployee) {
        assertThat(departmentService.employeeMinSalaryDepartment(departmentNumber).isEqualTo(expectedEmployee));
    }

    @Test
    public void employeeMinSalaryDepartmentTest2() {
        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> departmentService.employeeMinSalaryDepartment(1));
    }

    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public void employeesFromDepartmentTest2(int departmentNumber, List<Employee> expectedEmployee) {
        assertThat(departmentService.employeesDepartment(departmentNumber)).containsExactlyElementsOf(expectedEmployee);
    }

    public static Stream<Arguments> employeeMaxSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Nikita", "Tarasov", 1, 41000)),
                Arguments.of(1, new Employee("Alisa", "Berezina", 3, 52000))
        );
    }

    public static Stream<Arguments> employeeMinSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Sofya", "Knyazeva", 2, 32700)),
                Arguments.of(1, new Employee("Ivan", "Grigoriev", 4, 54300))
        );
    }

    public static Stream<Arguments> employeesFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Igor", "Nechaev", 1, 32500), new Employee("Miroslava", "Gorbunova", 1, 51700))),
                Arguments.of(2, List.of(new Employee("Lyubov", "Danilova", 2, 29900), new Employee("Kirill", "Zolotarev", 2, 36000))),
                Arguments.of(3, Collections.emptyList())
        );
    }
}