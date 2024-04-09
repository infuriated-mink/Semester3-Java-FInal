import java.sql.Connection;
import java.util.List;

public class HealthMonitoringApp {

    private static UserDao userDao;
    private static HealthDataDao healthDataDao;
    private static RecommendationSystem recommendationSystem;
    private static MedicineReminderManager medicineReminderManager;
    private static DoctorPortalDao doctorPortalDao;

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getCon();

        // Initialize DAOs with the database connection
        userDao = new UserDao();
        healthDataDao = new HealthDataDao(connection);
        recommendationSystem = new RecommendationSystem();
        medicineReminderManager = new MedicineReminderManager();
        doctorPortalDao = new DoctorPortalDao(connection);

        // Test functionalities
        testRegisterUser();
        testLoginUser();
        testAddHealthData();
        testGenerateRecommendations();
        testAddMedicineReminder();
        testGetRemindersForUser();
        testGetDueRemindersForUser();
        testDoctorPortal();
    }

    public static void testRegisterUser() {
        User newUser = new User(1, "Nessa", "Rice", "nessa.rice2@example.com", "password123", false);
        boolean registrationSuccess = userDao.createUser(newUser);
        System.out.println("-----------------------------------------------------");
        System.out.println("USER REGISTRATION");
        System.out.println("-----------------------------------------------------");
        System.out.println("- User registration for " + newUser.getFirstName() + " " + newUser.getLastName() + " was " + (registrationSuccess ? "SUCCESSFUL." : "UNSUCCESSFUL."));
        System.out.println();
    }
    public static void testLoginUser() {
        String userEmail = "nessa.rice2@example.com";
        String userPassword = "password123";
        boolean loginSuccess = loginUser(userEmail, userPassword);
        System.out.println("-----------------------------------------------------");
        System.out.println("USER LOGIN");
        System.out.println("-----------------------------------------------------");
        System.out.println("- Login " + (loginSuccess ? "SUCCESSFUL" : "FAILED") + " for user: " + userEmail);
        System.out.println();
    }

    public static boolean loginUser(String email, String password) {
        User user = userDao.getUserByEmail(email);
        if (user != null && userDao.verifyPassword(email, password)) {
            System.out.println("Login successful for user: " + email);
            return true;
        } else {
            System.out.println("Incorrect email or password. Please try again.");
            return false;
        }
    }

    public static void testAddHealthData() {
        HealthData newHealthData = new HealthData(1, 1, 70.0, 175.0, 10000, 70, "2024-04-08");
        boolean healthDataAdded = healthDataDao.createHealthData(newHealthData);
        System.out.println("-----------------------------------------------------");
        System.out.println("HEALTH DATA ADDITION");
        System.out.println("-----------------------------------------------------");
        System.out.println("- Addition of health data for user ID " + newHealthData.getUserId() + " on " + newHealthData.getDate() + " was " + (healthDataAdded ? "SUCCESSFUL." : "UNSUCCESSFUL."));
        System.out.println();
    }

    public static void testGenerateRecommendations() {
        System.out.println("-----------------------------------------------------");
        System.out.println("RECOMMENDATIONS");
        System.out.println("-----------------------------------------------------");
        HealthData healthData = healthDataDao.getHealthDataById(1);
        List<String> recommendations = recommendationSystem.generateRecommendations(healthData);
        recommendations.forEach(recommendation -> System.out.println("- " + recommendation));
        System.out.println();
    }

    public static void testAddMedicineReminder() {
        MedicineReminder newReminder = new MedicineReminder(1, 1, "Medicine A", "1 tablet", "Every 8 hours", "2024-04-08", "2024-04-15");
        boolean reminderAdded = medicineReminderManager.addReminder(newReminder);
        System.out.println("-----------------------------------------------------");
        System.out.println("MEDICINE REMINDERS");
        System.out.println("-----------------------------------------------------");
        System.out.println("- Medicine reminder for " + newReminder.getMedicineName() + " was SUCCESSFULLY added.");
        System.out.println();
    }

    public static void testGetRemindersForUser() {
        List<MedicineReminder> reminders = medicineReminderManager.getRemindersForUser(1);
        System.out.println("-----------------------------------------------------");
        System.out.println("MEDICINE REMINDERS FOR USER ID 1");
        System.out.println("-----------------------------------------------------");
        if (!reminders.isEmpty()) {
            reminders.forEach(reminder -> System.out.println("- Reminder for " + reminder.getMedicineName() + " starting on " + reminder.getStartDate()));
        } else {
            System.out.println("- No reminders found.");
        }
        System.out.println();
    }

    public static void testGetDueRemindersForUser() {
        List<MedicineReminder> dueReminders = medicineReminderManager.getDueReminders(1);
        System.out.println("-----------------------------------------------------");
        System.out.println("DUE MEDICINE REMINDERS FOR USER ID 1");
        System.out.println("-----------------------------------------------------");
        if (!dueReminders.isEmpty()) {
            dueReminders.forEach(reminder -> System.out.println("- Due reminder for " + reminder.getMedicineName() + " starting on " + reminder.getStartDate()));
        } else {
            System.out.println("- No due reminders found.");
        }
        System.out.println();
    }

    public static void testDoctorPortal() {
        Doctor doctor = doctorPortalDao.getDoctorById(2);
        System.out.println("-----------------------------------------------------");
        System.out.println("DOCTOR INFORMATION");
        System.out.println("-----------------------------------------------------");
        if (doctor != null) {
            System.out.println("- Retrieved doctor: " + doctor.getFirstName() + " " + doctor.getLastName() + " with specialization in " + doctor.getSpecialization());
            List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctor.getId());
            System.out.println("- Patients under Dr. " + doctor.getLastName() + ":");
            if (!patients.isEmpty()) {
                patients.forEach(patient -> System.out.println("  * " + patient.getFirstName() + " " + patient.getLastName()));
                System.out.println("\n - Displaying health data for " + patients.get(0).getFirstName() + " " + patients.get(0).getLastName() + ":");
                List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(patients.get(0).getId());
                healthDataList.forEach(healthData -> System.out.println("  -- Data on " + healthData.getDate() + ": Weight - " + healthData.getWeight() + "kg, Steps - " + healthData.getSteps()));
            } else {
                System.out.println("  * No patients found.");
            }
        } else {
            System.out.println("- Doctor with ID 2 not found.");
        }
        System.out.println();
    }
}