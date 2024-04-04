package healthmonitoring;

import java.time.LocalDate;
import java.util.List;

public class MedicineReminderManager {

    private UserDaoExample userDao = new UserDaoExample();
    private static HealthDataDao healthDataDao = new HealthDataDao(); // Assuming HealthDataDao is implemented
    private static RecommendationSystem recommendationSystem = new RecommendationSystem(); // Assuming
                                                                                           // RecommendationSystem is
                                                                                           // implemented
    private static DoctorPortalDao doctorPortalDao = new DoctorPortalDao(); // Assuming DoctorPortalDao is implemented

    public void manageMedicineReminders() {
        User newUser = new User(0, "John", "Doe", "john.doe@example.com", "password123", false);
        boolean userCreated = userDao.createUser(newUser);
        if (userCreated) {
            System.out.println("New user registered successfully.");
        }

        testLoginUser();

        HealthData healthData = new HealthData(0, newUser.getId(), 70.0, 1.75, 10000, 70, LocalDate.now().toString());
        boolean healthDataAdded = healthDataDao.createHealthData(healthData);
        if (healthDataAdded) {
            System.out.println("Health data added successfully.");
        }

        List<String> recommendations = recommendationSystem.generateRecommendations(healthData);
        for (String recommendation : recommendations) {
            System.out.println(recommendation);
        }

        MedicineReminder reminder = new MedicineReminder(0, newUser.getId(), "Medicine A", "100mg", "Every 8 hours",
                LocalDate.now().toString(), LocalDate.now().plusDays(7).toString());
        addReminder(reminder);
        System.out.println("Medicine reminder added successfully.");

        List<MedicineReminder> userReminders = getRemindersForUser(newUser.getId());
        for (MedicineReminder userReminder : userReminders) {
            System.out.println("Reminder for medicine: " + userReminder.getMedicineName());
        }

        List<MedicineReminder> dueReminders = getDueReminders(newUser.getId());
        for (MedicineReminder dueReminder : dueReminders) {
            System.out.println("Due reminder for medicine: " + dueReminder.getMedicineName());
        }

        testDoctorPortal();
    }

    public void addReminder(MedicineReminder reminder) {
        // Implementation to add reminder to database
    }

    public List<MedicineReminder> getRemindersForUser(int userId) {
        // Implementation to retrieve reminders for a user from database
        return null;
    }

    public List<MedicineReminder> getDueReminders(int userId) {
        // Implementation to retrieve due reminders for a user from database
        return null;
    }

    private void testLoginUser() {
        String userEmail = "john.doe@example.com";
        String userPassword = "password123";

        boolean loginSuccess = userDao.verifyPassword(userEmail, userPassword);

        if (loginSuccess) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Incorrect email or password. Please try again.");
        }
    }

    private void testDoctorPortal() {
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
}
