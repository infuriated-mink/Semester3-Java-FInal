import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class HealthMonitoringApp {

    private static UserDao userDao = new UserDao();
    private static HealthDataDao healthDataDao = new HealthDataDao();
    private static MedicineReminderManager medicineReminderManager = new MedicineReminderManager();
    private static RecommendationSystem recommendationSystem = new RecommendationSystem();

    public static void main(String[] args) {
        testLoginUser();
        testAddHealthData();
        testGenerateRecommendations();
        testAddMedicineReminder();
        testGetRemindersForUser();
        testGetDueRemindersForUser();
        testDoctorPortal();
    }

    private static void testLoginUser() {
        String userEmail = "john.doe@example.com";
        String userPassword = "password123";
        boolean loginSuccess = loginUser(userEmail, userPassword);
        if (loginSuccess) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Incorrect email or password. Please try again.");
        }
    }

    private static boolean loginUser(String email, String password) {
        User user = userDao.getUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public static void testAddHealthData() {
        HealthData healthData = new HealthData(1, 1, 70.0, 175.0, 8000, 80, "2023-04-06", 420);
        boolean success = healthDataDao.createHealthData(healthData);
        if (success) {
            System.out.println("Health data added successfully.");
        } else {
            System.out.println("Failed to add health data.");
        }
    }

    public static void testGenerateRecommendations() {
        HealthData healthData = healthDataDao.getHealthDataById(1);
        if (healthData != null) {
            List<String> recommendations = recommendationSystem.generateRecommendations(healthData);
            System.out.println("Recommendations:");
            for (String recommendation : recommendations) {
                System.out.println(recommendation);
            }
        } else {
            System.out.println("No health data found for generating recommendations.");
        }
    }

    public static void testAddMedicineReminder() {
        MedicineReminder reminder = new MedicineReminder(1, 1, "Medicine A", "2 pills", "08:00 AM", "2023-04-06",
                "2023-04-20");
        medicineReminderManager.addReminder(reminder);
        System.out.println("Medicine reminder added successfully.");
    }

    public static void testGetRemindersForUser() {
        MedicineReminderManager medicineReminderManager = new MedicineReminderManager(); // Initialize the variable
        List<MedicineReminder> reminders = medicineReminderManager.getRemindersForUser(1); // Assuming user ID 1
        System.out.println("Medicine reminders for user:");
        for (MedicineReminder reminder : reminders) {
            System.out.println(reminder.getMedicineName() + " - " + reminder.getSchedule());
        }
    }

    public static void testGetDueRemindersForUser() {
        List<MedicineReminder> dueReminders = medicineReminderManager.getDueReminders(1); // Assuming user ID 1
        System.out.println("Due medicine reminders for user:");
        for (MedicineReminder reminder : dueReminders) {
            System.out.println(reminder.getMedicineName() + " - " + reminder.getSchedule());
        }
    }

    public static void testDoctorPortal() {
        int doctorId = 1; 
        User doctor = userDao.getUserById(doctorId);
        if (doctor != null && doctor.isDoctor()) {
            System.out.println("Doctor found: " + doctor.getFirstName() + " " + doctor.getLastName());
            List<User> patients = userDao.getPatientsByDoctorId(doctorId);
            if (patients != null && !patients.isEmpty()) {
                System.out.println("Patients of Dr. " + doctor.getLastName() + ":");
                for (User patient : patients) {
                    System.out.println(patient.getFirstName() + " " + patient.getLastName());
                    List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(patient.getId());
                    if (healthDataList != null && !healthDataList.isEmpty()) {
                        System.out.println("Health Data for " + patient.getFirstName() + ":");
                        for (HealthData healthData : healthDataList) {
                            System.out.println("Date: " + healthData.getDate() + ", Steps: " + healthData.getSteps()
                                    + ", Heart Rate: " + healthData.getHeartRate());
                        }
                    } else {
                        System.out.println("No health data available for " + patient.getFirstName());
                    }
                }
            } else {
                System.out.println("No patients found for Dr. " + doctor.getLastName());
            }
        } else {
            System.out.println("Doctor not found or ID does not belong to a doctor.");
        }
    }
}