import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class MedicineReminderManager {
    private static final String INSERT_QUERY = "INSERT INTO medicine_reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";

    // Constructor
    public MedicineReminderManager() {
    }

    // Method to add a reminder
    public boolean addReminder(MedicineReminder reminder) {
        boolean result = false;
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, reminder.getUserId());
            preparedStatement.setString(2, reminder.getMedicineName());
            preparedStatement.setString(3, reminder.getDosage());
            preparedStatement.setString(4, reminder.getSchedule());

            // Convert String dates to java.sql.Date
            LocalDate startDate = LocalDate.parse(reminder.getStartDate());
            LocalDate endDate = LocalDate.parse(reminder.getEndDate());
            preparedStatement.setDate(5, Date.valueOf(startDate));
            preparedStatement.setDate(6, Date.valueOf(endDate));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Reminder added successfully!");
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method to get reminders for a user
    public List<MedicineReminder> getRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MedicineReminder reminder = new MedicineReminder(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("medicine_name"),
                    resultSet.getString("dosage"),
                    resultSet.getString("schedule"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date")
                );
                userReminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userReminders;
    }

    // Method to get reminders that are due for a user
    public List<MedicineReminder> getDueReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ? AND end_date <= ?";
        
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MedicineReminder reminder = new MedicineReminder(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("medicine_name"),
                    resultSet.getString("dosage"),
                    resultSet.getString("schedule"),
                    resultSet.getDate("start_date").toString(),
                    resultSet.getDate("end_date").toString()
                );
                dueReminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dueReminders;
    }
}