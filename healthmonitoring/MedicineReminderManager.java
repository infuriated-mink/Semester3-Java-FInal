package healthmonitoring;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MedicineReminderManager {
    private List<MedicineReminder> reminders;

    public MedicineReminderManager() {
        this.reminders = new ArrayList<>();
    }

    public void addReminder(MedicineReminder reminder) {
        reminders.add(reminder);
    }

    // Retrieves all reminders for a specific user
    public List<MedicineReminder> getRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        for (MedicineReminder reminder : reminders) {
            if (reminder.getUserId() == userId) {
                userReminders.add(reminder);
            }
        }
        return userReminders;
    }

    public List<MedicineReminder> getDueReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (MedicineReminder reminder : reminders) {
            if (reminder.getUserId() == userId) {
                LocalDateTime startDate = LocalDateTime.parse(reminder.getStartDate(), formatter);
                LocalDateTime endDate = LocalDateTime.parse(reminder.getEndDate(), formatter);
                if ((now.isEqual(startDate) || now.isAfter(startDate)) && now.isBefore(endDate)) {
                    dueReminders.add(reminder);
                }
            }
        }
        return dueReminders;
    }
}
