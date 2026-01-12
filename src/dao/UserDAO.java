package dao;

import model.User;
import util.DBUtil;

import java.sql.*;

public class UserDAO {

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, role, failed_attempts, locked) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getFailedAttempts());
            ps.setBoolean(5, user.isLocked());

            ps.executeUpdate();
        }
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setRole(rs.getString("role"));
                u.setFailedAttempts(rs.getInt("failed_attempts"));
                u.setLocked(rs.getBoolean("locked"));
                return u;
            }
        }
        return null;
    }

    public void updateFailedAttempts(String username, int count) throws SQLException {
        String sql = "UPDATE users SET failed_attempts = ? WHERE username = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, count);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }

    public void lockAccount(String username) throws SQLException {
        String sql = "UPDATE users SET locked = TRUE WHERE username = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.executeUpdate();
        }
    }

    public void logAttempt(String username, String status) throws SQLException {
        String sql = "INSERT INTO login_logs (username, timestamp, status) VALUES (?, NOW(), ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, status);
            ps.executeUpdate();
        }
    }
}
