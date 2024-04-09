import java.sql.*;
import java.util.*;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;
    private Connection connection;

    // Constructor
    public DoctorPortalDao(Connection connection) {
        this.connection = connection;
        userDao = new UserDao();
        healthDataDao = new HealthDataDao(connection);
    }
    // Method to get a doctor by ID
    public Doctor getDoctorById(int doctorId) {
        Doctor doctor = null;
        String query = "SELECT u.*, string_agg(s.name, ', ') as specializations " +
                       "FROM users u " +
                       "LEFT JOIN doctor_specializations ds ON u.id = ds.doctor_id " +
                       "LEFT JOIN specializations s ON ds.specialization_id = s.id " +
                       "WHERE u.id = ? AND u.is_doctor = TRUE " +
                       "GROUP BY u.id";
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Assuming Doctor constructor can handle the specialization string
                doctor = new Doctor(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("is_doctor"),
                    resultSet.getString("medical_license_number"),
                    resultSet.getString("specializations")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }
    // Method to get a list of patients by doctor ID
    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByUserId(patientId);
    }

    // Method to add a patient to a doctor
    public boolean addPatientToDoctor(int doctorId, int patientId) {
        PreparedStatement stmt = null;
        try {
            String query = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, doctorId);
            stmt.setInt(2, patientId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Method to get a list of patients by doctor ID
    public boolean removePatientFromDoctor(int doctorId, int patientId) {
        PreparedStatement stmt = null;
        try {
            String query = "DELETE FROM doctor_patient WHERE doctor_id = ? AND patient_id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, doctorId);
            stmt.setInt(2, patientId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Method to get a list of patients by doctor ID
     public List<User> getPatientsByDoctorId(int doctorId) {
        List<User> patients = new ArrayList<>();
        String query = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, u.is_doctor " +
                       "FROM users u " +
                       "JOIN doctor_patient dp ON u.id = dp.patient_id " +
                       "WHERE dp.doctor_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                boolean isDoctor = resultSet.getBoolean("is_doctor");
                patients.add(new User(id, firstName, lastName, email, password, isDoctor));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}