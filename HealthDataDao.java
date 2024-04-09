import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HealthDataDao {
    private Connection connection;

    public HealthDataDao(Connection connection) {
        this.connection = connection;
    }
    // Method to create health data
    public boolean createHealthData(HealthData healthData) {
        String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, healthData.getUserId());
            preparedStatement.setDouble(2, healthData.getWeight());
            preparedStatement.setDouble(3, healthData.getHeight());
            preparedStatement.setInt(4, healthData.getSteps());
            preparedStatement.setInt(5, healthData.getHeartRate());
            java.sql.Date sqlDate = java.sql.Date.valueOf(healthData.getDate());
            preparedStatement.setDate(6, sqlDate);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to get health data by ID
    public HealthData getHealthDataById(int id) {
        String query = "SELECT * FROM health_data WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new HealthData(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDouble("weight"),
                        resultSet.getDouble("height"),
                        resultSet.getInt("steps"),
                        resultSet.getInt("heart_rate"),
                        resultSet.getString("date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get health data by user ID
    public List<HealthData> getHealthDataByUserId(int userId) {
        List<HealthData> healthDataList = new ArrayList<>();
        String query = "SELECT * FROM health_data WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                healthDataList.add(new HealthData(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDouble("weight"),
                        resultSet.getDouble("height"),
                        resultSet.getInt("steps"),
                        resultSet.getInt("heart_rate"),
                        resultSet.getString("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return healthDataList;
    }

    // Method to update health data
    public boolean updateHealthData(HealthData healthData) {
        String query = "UPDATE health_data SET weight = ?, height = ?, steps = ?, heart_rate = ?, date = ?, sleep_duration = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, healthData.getWeight());
            preparedStatement.setDouble(2, healthData.getHeight());
            preparedStatement.setInt(3, healthData.getSteps());
            preparedStatement.setInt(4, healthData.getHeartRate());
            preparedStatement.setString(5, healthData.getDate());
            preparedStatement.setInt(7, healthData.getId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete health data
    public boolean deleteHealthData(int id) {
        String query = "DELETE FROM health_data WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}