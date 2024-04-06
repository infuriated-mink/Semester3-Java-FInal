package healthmonitoring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HealthDataDao {

  public boolean createHealthData(HealthData healthData) {
    String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query)) {
      statement.setInt(1, healthData.getUserId());
      statement.setDouble(2, healthData.getWeight());
      statement.setDouble(3, healthData.getHeight());
      statement.setInt(4, healthData.getSteps());
      statement.setInt(5, healthData.getHeartRate());
      statement.setString(6, healthData.getDate());
      int updatedRows = statement.executeUpdate();
      return updatedRows != 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public HealthData getHealthDataById(int id) {
    String query = "SELECT * FROM health_data WHERE id = ?";

    try (Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query)) {
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        return new HealthData(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getDouble("weight"),
            rs.getDouble("height"),
            rs.getInt("steps"),
            rs.getInt("heart_rate"),
            rs.getString("date"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<HealthData> getHealthDataByUserId(int userId) {
    String query = "SELECT * FROM health_data WHERE user_id = ?";
    List<HealthData> healthDataList = new ArrayList<>();

    try (Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query)) {
      statement.setInt(1, userId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        healthDataList.add(new HealthData(
            rs.getInt("id"),
            userId,
            rs.getDouble("weight"),
            rs.getDouble("height"),
            rs.getInt("steps"),
            rs.getInt("heart_rate"),
            rs.getString("date")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return healthDataList;
  }

  public boolean updateHealthData(HealthData healthData) {
    String query = "UPDATE health_data SET user_id = ?, weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? WHERE id = ?";

    try (Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query)) {
      statement.setInt(1, healthData.getUserId());
      statement.setDouble(2, healthData.getWeight());
      statement.setDouble(3, healthData.getHeight());
      statement.setInt(4, healthData.getSteps());
      statement.setInt(5, healthData.getHeartRate());
      statement.setString(6, healthData.getDate());
      statement.setInt(7, healthData.getId());
      int updatedRows = statement.executeUpdate();
      return updatedRows != 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean deleteHealthData(int id) {
    String query = "DELETE FROM health_data WHERE id = ?";

    try (Connection con = DatabaseConnection.getCon();
        PreparedStatement statement = con.prepareStatement(query)) {
      statement.setInt(1, id);
      int updatedRows = statement.executeUpdate();
      return updatedRows != 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
