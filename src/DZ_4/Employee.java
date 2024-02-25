package DZ_4;

import java.time.LocalDate;
import java.time.Period;

record Employee(String name, int id, String phone, int experience /*LocalDate startJobDate*/) {
//    public int getExp() {
//        Period period = Period.between(startJobDate, LocalDate.now());
//        return period.getYears();
//    }
}
