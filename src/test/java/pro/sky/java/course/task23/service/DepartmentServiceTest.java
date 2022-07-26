package pro.sky.java.course.task23.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course.task23.Employee;
import pro.sky.java.course.task23.exception.EmployeeNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
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
                new Employee("Sophia", "Prohorova", 48000, "1"),
                new Employee("Egor", "Frolov", 42000, "1"),
                new Employee("Sergey", "Vasilyev", 53000, "2"),
                new Employee("Alexander", "Belyaev", 46000, "2"),
                new Employee("Polina", "Makarova", 56000, "2")
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("maxSalaryWithinDepartmentParams")
    public void maxSalaryWithinDepartmentPositiveTest(String department, Employee expected) {
        assertThat(departmentService.maxSalaryWithinDepartment(department)).isEqualTo(expected);
    }

    @Test
    public void maxSalaryWithinDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.maxSalaryWithinDepartment("3"));
    }

    @ParameterizedTest
    @MethodSource("minSalaryWithinDepartmentParams")
    public void minSalaryWithinDepartmentPositiveTest(String department, Employee expected) {
        assertThat(departmentService.minSalaryWithinDepartment(department)).isEqualTo(expected);
    }

    @Test
    public void minSalaryWithinDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.minSalaryWithinDepartment("3"));
    }

    @ParameterizedTest
    @MethodSource("allEmployeesWithinSpecificDepartmentParams")
    public void allEmployeesWithinSpecificDepartmentPositiveTest(String department, List<Employee> expected) {
        assertThat(departmentService.allEmployeesWithinSpecificDepartment(department)).containsExactlyElementsOf(expected);
    }

    @Test
    public void allEmployeesTest() {
        assertThat(departmentService.allEmployees()).containsAllEntriesOf(
                Map.of(
                        "1", List.of(
                                new Employee("Sophia", "Prohorova", 48000, "1"),
                                new Employee("Egor", "Frolov", 42000, "1")),
                        "2", List.of(
                                new Employee("Sergey", "Vasilyev", 53000, "2"),
                                new Employee("Alexander", "Belyaev", 46000, "2"),
                                new Employee("Polina", "Makarova", 56000, "2")))

        );
    }

    public static Stream<Arguments> maxSalaryWithinDepartmentParams() {
        return Stream.of(
                Arguments.of(
                        "1", new Employee("Sophia", "Prohorova", 48000, "1"),
                        "2", new Employee("Polina", "Makarova", 56000, "2"))
        );
    }

    public static Stream<Arguments> minSalaryWithinDepartmentParams() {
        return Stream.of(
                Arguments.of(
                        "1", new Employee("Egor", "Frolov", 42000, "1"),
                        "2", new Employee("Alexander", "Belyaev", 46000, "2"))
        );
    }

    public static Stream<Arguments> allEmployeesWithinSpecificDepartmentParams() {
        return Stream.of(
                Arguments.of(
                        "1", List.of(
                                new Employee("Sophia", "Prohorova", 48000, "1"),
                                new Employee("Egor", "Frolov", 42000, "1")),
                        "2", List.of(
                                new Employee("Sergey", "Vasilyev", 53000, "2"),
                                new Employee("Alexander", "Belyaev", 46000, "2"),
                                new Employee("Polina", "Makarova", 56000, "2")),
                        "3", Collections.emptyList()
                ));
    }

}
