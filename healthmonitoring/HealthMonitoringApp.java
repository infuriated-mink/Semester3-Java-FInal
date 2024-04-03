package healthmonitoring;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HealthMonitoringApp {

    private static UserDaoExample userDao = new UserDaoExample();
    private static HealthDataDao healthDataDao = new HealthDataDao(); // Assuming this class exists
    private static MedicineReminderDao medicineReminderDao = new MedicineReminderDao(); // Assuming this class exists
    private static RecommendationSystem recommendationSystem = new RecommendationSystem(); // Assuming this class exists
    private static DoctorPortalDao doctorPortalDao = new DoctorPortalDao(); // Assuming this class exists

    public static void main(String[] args) {
        User newUser = new User(0, "John", "Doe", "john.doe@example.com", "password123", false);
        boolean userCreated = userDao.createUser(newUser);
        if (userCreated) {
            System.out.println("New user registered successfully.");
        }

        testLoginUser();

        HealthData healthData = new HealthData(0, newUser.getId(), 70.0, 1.75, 10000, 70, "2024-04-03");
        boolean healthDataAdded = healthDataDao.createHealthData(healthData);
        if (healthDataAdded) {
            System.out.println("Health data added successfully.");
        }

        List<String> recommendations = recommendationSystem.generateRecommendations(healthData);
        for (String recommendation : recommendations) {
            System.out.println(recommendation);
        }

        MedicineReminder reminder = new MedicineReminder(0, newUser.getId(), "Medicine A", "100mg", "Every 8 hours",
                "2024-04-03", "2024-04-10");
        boolean reminderAdded = medicineReminderDao.addReminder(reminder);
        if (reminderAdded) {
            System.out.println("Medicine reminder added successfully.");
        }

        List<MedicineReminder> userReminders = medicineReminderDao.getRemindersForUser(newUser.getId());
        for (MedicineReminder userReminder : userReminders) {
            System.out.println("Reminder for medicine: " + userReminder.getMedicineName());
        }

        List<MedicineReminder> dueReminders = medicineReminderDao.getDueReminders(newUser.getId());
        for (MedicineReminder dueReminder : dueReminders) {
            System.out.println("Due reminder for medicine: " + dueReminder.getMedicineName());
        }

        testDoctorPortal();
    }

    public static boolean loginUser(String email, String password) {
        boolean loginSuccess = userDao.verifyPassword(email, password);
        if (loginSuccess) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Incorrect email or password. Please try again.");
        }
        return loginSuccess;
    }

    public static void testDoctorPortal() {
        int doctorId = 1;
        Doctor doctor = doctorPortalDao.getDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("Doctor found: " + doctor.getFirstName() + " " + doctor.getLastName());
        }

        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
        for (User patient : patients) {
            System.out.println("Patient: " + patient.getFirstName() + " " + patient.getLastName());
        }

        int patientId = 2;
        List<HealthData> patientHealthData = doctorPortalDao.getHealthDataByPatientId(patientId);
        for (HealthData data : patientHealthData) {
            System.out.println("Health Data: " + data.getSteps() + " steps, " + data.getHeartRate() + " bpm");
        }
    }

    public static void testLoginUser() {
        String userEmail = "john.doe@example.com";
        String userPassword = "password123";

        boolean loginSuccess = loginUser(userEmail, userPassword);

        if (loginSuccess) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Incorrect email or password. Please try again.");
        }
    }
}
