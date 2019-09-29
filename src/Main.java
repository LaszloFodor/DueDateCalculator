import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {


    public static void main(String... args) {
        DueDateCalculator dueDateCalculator = new DueDateCalculator(LocalTime.of(9, 0),
                LocalTime.of(17, 0));
        LocalDateTime submissionDate = LocalDateTime.of(2019, 9, 23, 13, 12);
        System.out.println(submissionDate);
        System.out.println(dueDateCalculator.calculateDueDate(submissionDate, 16));
    }
}
