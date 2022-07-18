package pro.sky.java.course.task23.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.java.course.task23.Employee;
import pro.sky.java.course.task23.exception.EmployeeAlreadyAddedException;
import pro.sky.java.course.task23.exception.EmployeeNotFoundException;
import pro.sky.java.course.task23.exception.EmployeeStorageIsFullException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EmployeeServiceTest {

    private final EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl();

    @ParameterizedTest
    @MethodSource("params")
    public void addEmployeeNegativeTest(String firstName, String lastName, int salary, String department) {
        Employee expected = new Employee(firstName, lastName, salary, department);
        assertThat(employeeServiceImpl.add(firstName, lastName, salary, department)).isEqualTo(expected);

        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeServiceImpl.add(firstName, lastName, salary, department));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void addEmployeeNegativeTestSecond(String firstName, String lastName, int salary, String department) {
        List<Employee> employees = generateListOfEmployees(20);
        employees.forEach(employee ->
                assertThat(employeeServiceImpl.add(employee.getFirstName(), employee.getLastName(), employee.getEmployeeSalary(), employee.getEmployeeDepartment())).isEqualTo(employee));

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeServiceImpl.add(firstName, lastName, salary, department));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeEmployeePositiveTest(String firstName, String lastName, int salary, String department) {

        Employee expected = new Employee(firstName, lastName, salary, department);
        assertThat(employeeServiceImpl.add(firstName, lastName, salary, department)).isEqualTo(expected);

        assertThat(employeeServiceImpl.remove(firstName, lastName)).isEqualTo(expected);
        assertThat(employeeServiceImpl.getAll()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeEmployeeNegativeTest(String firstName, String lastName, int salary, String department) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.remove("test", "test"));

        Employee expected = new Employee(firstName, lastName, salary, department);
        assertThat(employeeServiceImpl.add(firstName, lastName, salary, department)).isEqualTo(expected);

        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.remove("test", "test"));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findEmployeePositiveTest(String firstName, String lastName, int salary, String department) {

        Employee expected = new Employee(firstName, lastName, salary, department);
        assertThat(employeeServiceImpl.add(firstName, lastName, salary, department)).isEqualTo(expected);

        assertThat(employeeServiceImpl.find(firstName, lastName)).isEqualTo(expected);
        assertThat(employeeServiceImpl.getAll()).hasSize(1);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findEmployeeNegativeTest(String firstName, String lastName, int salary, String department) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.find("test", "test"));

        Employee expected = new Employee(firstName, lastName, salary, department);
        assertThat(employeeServiceImpl.add(firstName, lastName, salary, department)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.find("test", "test"));
    }

    private List<Employee> generateListOfEmployees(int size) {
        return Stream.iterate(1, i -> i + 1)
                .limit(size)
                .map(i -> new Employee("FirstName" + (char) ((int) 'a' + i), "LastName" + (char) ((int) 'a' + i), 10000, Integer.toString(i)))
                .collect(Collectors.toList());
    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Ivan", "Ivanov", 50000, "1"),
                Arguments.of("Petr", "Petrov", 55000, "1"),
                Arguments.of("Mariya", "Ivanova", 60000, "2"));
    }

}
