package pro.sky.java.course.task23.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course.task23.Employee;
import pro.sky.java.course.task23.exception.EmployeeAlreadyAddedException;
import pro.sky.java.course.task23.exception.EmployeeNotFoundException;
import pro.sky.java.course.task23.exception.EmployeeStorageIsFullException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

//    private final List<Employee> employees = new ArrayList<>(List.of(
//            new Employee("Sophia", "Prohorova", 48000, "5"),
//            new Employee("Egor", "Frolov", 42000, "1"),
//            new Employee("Sergey", "Vasilyev", 53000, "2"),
//            new Employee("Alexander", "Belyaev", 46000, "3"),
//            new Employee("Polina", "Makarova", 56000, "4"),
//            new Employee("Alena", "Minaeva", 45000, "2"),
//            new Employee("Mihail", "Borisov", 52000, "3"),
//            new Employee("Artem", "Novikov", 56000, "5"),
//            new Employee("Konstantin", "Belov", 52000, "4"),
//            new Employee("Valeria", "Petrova", 50000, "1")
//    ));

    private static final int LIMIT = 20;

    public Employee add(String firstName, String lastName, int salary, String department) {
        Employee employee = new Employee(firstName, lastName, salary, department);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String firstName, String lastName) {
        Employee employee = employees.stream()
                .filter(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException());
        employees.remove(employee);
        return employee;
    }

    public Employee find(String firstName, String lastName) {
        return employees.stream()
                .filter(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException());
    }

    public List<Employee> getAll() {
        return new ArrayList<>((employees));
    }
}
