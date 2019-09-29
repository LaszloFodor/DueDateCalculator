import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class DueDateCalculatorTest {

    private DueDateCalculator dueDateCalculator;

    private LocalDateTime testDateTime;

    @Before
    public void setup() {
        dueDateCalculator = new DueDateCalculator(LocalTime.of(9, 0), LocalTime.of(17, 0));
        testDateTime = LocalDateTime.of(2019, 9, 23, 12, 12);
    }

    @Test
    public void testDueDateCalculator_startEarlierThanEnd() {
        dueDateCalculator = new DueDateCalculator(LocalTime.of(7, 0), LocalTime.of(8, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDueDateCalculator_endEarlierThanStart() {
        dueDateCalculator = new DueDateCalculator(LocalTime.of(10, 0), LocalTime.of(8, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDueDateCalculator_startEqualsEnd() {
        dueDateCalculator = new DueDateCalculator(LocalTime.of(8, 0), LocalTime.of(8, 0));
    }

    @Test
    public void testCalculateDueDate_twoDayTurnaround() {
        int turnaround = 16;
        assertEquals(LocalDateTime.of(2019, 9, 25, 12, 12),
                dueDateCalculator.calculateDueDate(testDateTime, turnaround));
    }

    @Test
    public void testCalculateDueDate_withinADay() {
        int turnaround = 2;
        assertEquals(LocalDateTime.of(2019, 9, 23, 14, 12),
                dueDateCalculator.calculateDueDate(testDateTime, turnaround));
    }

    @Test
    public void testCalculateDueDate_turnaroundIsZero() {
        int turnaround = 0;
        assertEquals(LocalDateTime.of(2019, 9, 23, 12, 12),
                dueDateCalculator.calculateDueDate(testDateTime, turnaround));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDueDate_turnaroundLessThanZero() {
        int turnaround = -2;
        assertEquals(LocalDateTime.of(2019, 9, 23, 12, 12),
                dueDateCalculator.calculateDueDate(testDateTime, turnaround));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDueDate_timeEarlierThanStartTime() {
        int turnaround = 2;
        testDateTime = LocalDateTime.of(2019, 9, 23, 7, 12);
        dueDateCalculator.calculateDueDate(testDateTime, turnaround);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDueDate_timeLaterThanEndTime() {
        int turnaround = 2;
        testDateTime = LocalDateTime.of(2019, 9, 23, 20, 12);
        dueDateCalculator.calculateDueDate(testDateTime, turnaround);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDueDate_dateIsSunday() {
        int turnaround = 2;
        testDateTime = LocalDateTime.of(2019, 9, 22, 12, 12);
        dueDateCalculator.calculateDueDate(testDateTime, turnaround);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDueDate_dateIsSaturday() {
        int turnaround = 2;
        testDateTime = LocalDateTime.of(2019, 9, 21, 12, 12);
        dueDateCalculator.calculateDueDate(testDateTime, turnaround);
    }

    @Test
    public void testCalculateDueDate_endsOnNextDay() {
        int turnaround = 2;
        testDateTime = LocalDateTime.of(2019, 9, 23, 16, 12);
        assertEquals(LocalDateTime.of(2019, 9, 24, 10, 12),
                dueDateCalculator.calculateDueDate(testDateTime, turnaround));
    }

    @Test
    public void testCalculateDueDate_submissionDateOnFridayEndsNextWeek() {
        int turnaround = 2;
        testDateTime = LocalDateTime.of(2019, 9, 20, 16, 12);
        assertEquals(LocalDateTime.of(2019, 9, 23, 10, 12),
                dueDateCalculator.calculateDueDate(testDateTime, turnaround));
    }

}
