package pro.sky.java.course.task23.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course.task23.Employee;
import pro.sky.java.course.task23.exception.EmployeeNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService
            (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Map<String, List<Employee>> allEmployees() {
        return employeeService.getAll().stream()
                .collect(Collectors.groupingBy(e -> e.getEmployeeDepartment(), Collectors.toList()));
    }

    public List<Employee> allEmployeesWithinSpecificDepartment(String department) {
        return employeeService.getAll().stream()
                .filter(e -> e.getEmployeeDepartment().equals(department))
                .collect(Collectors.toList());
    }

    public Employee maxSalaryWithinDepartment(String department) {
        return employeeService.getAll().stream()
                .filter(e -> e.getEmployeeDepartment().equals(department))
                .max(Comparator.comparingInt(e -> e.getEmployeeSalary()))
                .orElseThrow(() -> new EmployeeNotFoundException());
    }

    public Employee minSalaryWithinDepartment(String department) {
        return employeeService.getAll().stream()
                .filter(e -> e.getEmployeeDepartment().equals(department))
                .min(Comparator.comparingInt(e -> e.getEmployeeSalary()))
                .orElseThrow(() -> new EmployeeNotFoundException());
    }

}
