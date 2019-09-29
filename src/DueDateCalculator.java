import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DueDateCalculator {

    private LocalTime startHour;

    private LocalTime endHour;

    public DueDateCalculator(LocalTime startHour, LocalTime endHour) {
        if (startHour.equals(endHour)) {
            throw new IllegalArgumentException("Start hour and end hour cannot be the same!");
        } else if (startHour.isAfter(endHour)) {
            throw new IllegalArgumentException("Start hour cannot be earlier than end hour!");
        }

        this.startHour = startHour;
        this.endHour = endHour;
    }

    public LocalDateTime calculateDueDate(LocalDateTime submitDate, int turnaround) {
        if (turnaround < 0) {
            throw new IllegalArgumentException("Turnaround cannot be negative!");
        } else if (!isValidDate(submitDate)) {
            throw new IllegalArgumentException("Date can only be submitted within working hours and on weekdays!");
        } else {
            if (submitDate.getHour() + turnaround < endHour.getHour()) {
                return submitDate.plusHours(turnaround);
            } else {
                int workingHours = endHour.getHour() - startHour.getHour();
                int businessDays = turnaround / workingHours;
                int businessHours = turnaround % workingHours;
                LocalDateTime dueDate = submitDate;
                if (dueDate.plusHours(businessHours).getHour() > endHour.getHour()) {
                    businessDays++;
                    businessHours -= (endHour.getHour() - submitDate.getHour());
                    dueDate = dueDate.withHour((startHour.getHour() + businessHours));
                    dueDate = dueDate.plusDays(businessDays);
                    dueDate = calculateDayOfWeek(dueDate);
                } else {
                    dueDate = dueDate.plusDays(businessDays);
                    dueDate = dueDate.plusHours(businessHours);
                    dueDate = calculateDayOfWeek(dueDate);
                }
                return dueDate;
            }
        }
    }

    private LocalDateTime calculateDayOfWeek(LocalDateTime dueDate) {
        if (dueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            dueDate = dueDate.plusDays(2);
        } else if (dueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            dueDate = dueDate.plusDays(1);
        }
        return dueDate;
    }

    private boolean isValidDate(LocalDateTime submitDate) {
        if (submitDate.toLocalTime().isBefore(startHour) ||
                submitDate.toLocalTime().isAfter(endHour)) {
            return false;
        } else if (submitDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                submitDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        return true;
    }
}
