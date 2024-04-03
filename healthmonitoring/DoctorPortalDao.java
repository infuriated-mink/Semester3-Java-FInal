import java.util.List;
import java.util.stream.Collectors;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    public DoctorPortalDao() {
        userDao = new UserDao();
        healthDataDao = new HealthDataDao();
    }

    public Doctor getDoctorById(int doctorId) {
        User user = userDao.getUserById(doctorId);
        if (user != null && user.isDoctor()) {
            return new Doctor(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(), /
                    user.isDoctor()
            );
        }
        return null;
    }

    public List<User> getPatientsByDoctorId(int doctorId) {
        List<User> allUsers = userDao.getAllUsers();
        return allUsers.stream()
                .filter(user -> user.getDoctorId() == doctorId && !user.isDoctor())
                .collect(Collectors.toList());
    }

    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByUserId(patientId);
    }
}