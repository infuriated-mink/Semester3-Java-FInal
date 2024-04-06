
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                    user.getPassword(),
                    user.isDoctor(), null, null);
        }
        return null;
    }

    public boolean addPatientToDoctor(int doctorId, int patientId) {
        String sql = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getCon();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, patientId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removePatientFromDoctor(int doctorId, int patientId) {
        String sql = "DELETE FROM doctor_patient WHERE doctor_id = ? AND patient_id = ?";
        try (Connection conn = DatabaseConnection.getCon();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, patientId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
