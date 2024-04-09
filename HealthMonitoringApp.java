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
        User newUser = new User(1, "Vanessa", "Rice", "vanessa.rice12@example.com", "password123", false);
        boolean registrationSuccess = userDao.createUser(newUser);
        System.out.println("User registration for " + newUser.getFirstName() + " " + newUser.getLastName() + " was " + (registrationSuccess ? "successful." : "unsuccessful."));
    }

    public static void testLoginUser() {
        String userEmail = "vanessa.rice12@example.com";
        String userPassword = "password123";
        boolean loginSuccess = loginUser(userEmail, userPassword);
        System.out.println("User login for " + userEmail + " was " + (loginSuccess ? "successful." : "unsuccessful. Incorrect email or password."));
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
        System.out.println("Addition of health data for user ID " + newHealthData.getUserId() + " on " + newHealthData.getDate() + " was " + (healthDataAdded ? "successful." : "unsuccessful."));
    }

    public static void testGenerateRecommendations() {
        HealthData healthData = healthDataDao.getHealthDataById(1);
        List<String> recommendations = recommendationSystem.generateRecommendations(healthData);
        System.out.println("Generated recommendations based on the latest health data:");
        recommendations.forEach(recommendation -> System.out.println("- " + recommendation));
    }

    public static void testAddMedicineReminder() {
        MedicineReminder newReminder = new MedicineReminder(1, 1, "Medicine A", "1 tablet", "Every 8 hours", "2024-04-08", "2024-04-15");
        boolean reminderAdded = medicineReminderManager.addReminder(newReminder);
        System.out.println("Addition of medicine reminder for " + newReminder.getMedicineName() + " was " + (reminderAdded ? "successful." : "unsuccessful."));
    }

    public static void testGetRemindersForUser() {
        List<MedicineReminder> reminders = medicineReminderManager.getRemindersForUser(1);
        System.out.println("Retrieved medicine reminders for user ID 1:");
        reminders.forEach(reminder -> System.out.println("- Reminder for " + reminder.getMedicineName() + " starting on " + reminder.getStartDate()));
    }

    public static void testGetDueRemindersForUser() {
        List<MedicineReminder> dueReminders = medicineReminderManager.getDueReminders(1);
        System.out.println("Retrieved due medicine reminders for user ID 1:");
        dueReminders.forEach(reminder -> System.out.println("- Due reminder for " + reminder.getMedicineName() + " starting on " + reminder.getStartDate()));
    }

    public static void testDoctorPortal() {
        Doctor doctor = doctorPortalDao.getDoctorById(2);
        if (doctor != null) {
            System.out.println("Retrieved doctor: " + doctor.getFirstName() + " " + doctor.getLastName() + " with specialization in " + doctor.getSpecialization());
            List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctor.getId());
            System.out.println("Patients under Dr. " + doctor.getLastName() + ":");
            patients.forEach(patient -> System.out.println("- " + patient.getFirstName() + " " + patient.getLastName()));
            if (!patients.isEmpty()) {
                System.out.println("Displaying health data for " + patients.get(0).getFirstName() + " " + patients.get(0).getLastName() + ":");
                List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(patients.get(0).getId());
                healthDataList.forEach(healthData -> System.out.println("-- Data on " + healthData.getDate() + ": Weight - " + healthData.getWeight() + "kg, Steps - " + healthData.getSteps()));
            }
        } else {
            System.out.println("Doctor with ID 2 not found.");
        }
    }
}