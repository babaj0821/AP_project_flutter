import java.time.LocalDateTime;
import java.time.Duration;

public class Assignment {
    private Course courseName;
    private String assignmentName;
    private LocalDateTime deadline;
    private boolean isActive;

    public Assignment(Course courseName, String assignmentName, LocalDateTime deadline) throws Exception {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.isActive = true;
        Admin a = Admin.getAdmin();
        a.addassignmenttolist(this);
    }

    public String timeLeftUntilDeadline() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, deadline);
        long daysLeft = duration.toDays();
        long hoursLeft = duration.toHours() % 24;
        long minutesLeft = duration.toMinutes() % 60;
        return (daysLeft + " days, " + hoursLeft + " hours, " + minutesLeft + " minutes");
    }

    public void changeDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
