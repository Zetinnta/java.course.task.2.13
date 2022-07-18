package pro.sky.java.course.task23.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course.task23.Employee;
import pro.sky.java.course.task23.service.DepartmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> allWithinSpecificDepartment(@RequestParam("departmentId") String department) {
        return departmentService.allEmployeesWithinSpecificDepartment(department);
    }

    @GetMapping("/all")
    public Map<String, List<Employee>> all() {
        return departmentService.allEmployees();
    }

    @GetMapping("/max-salary")
    public Employee maxSalaryWithinSpecificDepartment(@RequestParam("departmentId") String department) {
        return departmentService.maxSalaryWithinDepartment(department);
    }

    @GetMapping("/min-salary")
    public Employee minSalaryWithinSpecificDepartment(@RequestParam("departmentId") String department) {
        return departmentService.minSalaryWithinDepartment(department);
    }
}
