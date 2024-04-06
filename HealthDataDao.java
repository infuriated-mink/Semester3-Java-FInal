import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HealthDataDao {

  public boolean createHealthData(HealthData healthData) {
    String sql = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date, sleep_duration) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getCon();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, healthData.getUserId());
      stmt.setDouble(2, healthData.getWeight());
      stmt.setDouble(3, healthData.getHeight());
      stmt.setInt(4, healthData.getSteps());
      stmt.setInt(5, healthData.getHeartRate());
      stmt.setString(6, healthData.getDate());
      stmt.setInt(7, healthData.getSleepDuration());

      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public HealthData getHealthDataById(int id) {
    String sql = "SELECT * FROM health_data WHERE id = ?";
    try (Connection conn = DatabaseConnection.getCon();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return new HealthData(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getDouble("weight"),
            rs.getDouble("height"),
            rs.getInt("steps"),
            rs.getInt("heart_rate"),
            rs.getString("date"),
            rs.getInt("sleep_duration"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<HealthData> getHealthDataByUserId(int userId) {
    List<HealthData> healthDataList = new ArrayList<>();
    String sql = "SELECT * FROM health_data WHERE user_id = ?";
    try (Connection conn = DatabaseConnection.getCon();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        healthDataList.add(new HealthData(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getDouble("weight"),
            rs.getDouble("height"),
            rs.getInt("steps"),
            rs.getInt("heart_rate"),
            rs.getString("date"),
            rs.getInt("sleep_duration")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return healthDataList;
  }

  public boolean updateHealthData(HealthData healthData) {
    String sql = "UPDATE health_data SET weight = ?, height = ?, steps = ?, heart_rate = ?, date = ?, sleep_duration = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getCon();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setDouble(1, healthData.getWeight());
      stmt.setDouble(2, healthData.getHeight());
      stmt.setInt(3, healthData.getSteps());
      stmt.setInt(4, healthData.getHeartRate());
      stmt.setString(5, healthData.getDate());
      stmt.setInt(6, healthData.getSleepDuration());
      stmt.setInt(7, healthData.getId());

      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteHealthData(int id) {
    String sql = "DELETE FROM health_data WHERE id = ?";
    try (Connection conn = DatabaseConnection.getCon();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);

      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}