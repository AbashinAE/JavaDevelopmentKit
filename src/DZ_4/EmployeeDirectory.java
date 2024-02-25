package DZ_4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDirectory {
    public static int id;
    private List<Employee> employees;

    public EmployeeDirectory() {
        employees = new ArrayList<Employee>();
    }

    public boolean addEmployee(String name, String phone, int experience) {
        Employee employee = new Employee(name, id++, phone, experience);
        if (!employees.contains(employee)) {
            employees.add(employee);
            return true;
        }
        return false;
    }

    public Employee findId(int id) {
        return employees.stream().filter(e -> e.id() == id).findFirst().get();
    }

    public List<Employee> findName(String name) {
        return employees.stream().filter(e -> e.name().equals(name)).toList();
    }

//    public List<Employee> findExperience(int yearExp) {
//        return employees.stream().filter(e -> e.getExp() == yearExp).toList();
//    }

    public List<Employee> findPhone(String phone) {
        return employees.stream().filter(e -> e.phone().equals(phone)).toList();
    }


    public void printArray() {
        employees.forEach(System.out::println);
    }
}