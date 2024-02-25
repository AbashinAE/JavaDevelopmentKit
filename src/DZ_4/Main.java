package DZ_4;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDirectory employeeDirectory = new EmployeeDirectory();
        List(employeeDirectory);
        System.out.println(LocalDate.now());
//        System.out.println(employeeDirectory.findExperience(4)); поиск по стажу не удался
        System.out.println(employeeDirectory.findName("Святослав"));
        System.out.println(employeeDirectory.findPhone("+79004444444"));
        System.out.println(employeeDirectory.findId(5));

    }

    private static void List(EmployeeDirectory e) {
        e.addEmployee("Костя", "+79001111111", 5);
        e.addEmployee("Мария", "+79002222222", 4);
        e.addEmployee("Святослав", "+79003333333", 3);
        e.addEmployee("Кирилл", "+79004444444", 1);
        e.addEmployee("Семен", "+79005555555", 2);
        e.addEmployee("Светлана", "+79006666666", 5);
    }
}
