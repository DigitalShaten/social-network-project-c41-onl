package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.tms.socialnetworkc41onl.util.ConnectionManager.getConnection;

public class SubscriptionDao {

    public void save(Subscription s) {
        String sql = "INSERT INTO subscriptions (user_id, subscription_user_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, s.getUserId());
            ps.setLong(2, s.getSubscriptionUserId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении подписки: " + e.getMessage(), e);
        }
    }

    public void delete(long userId, long subscriptionUserId) {
        String sql = "DELETE FROM subscriptions WHERE user_id = ? AND subscription_user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setLong(2, subscriptionUserId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении подписки: " + e.getMessage(), e);
        }
    }

    public boolean exists(long userId, long subscriptionUserId) {
        String sql = "SELECT 1 FROM subscriptions WHERE user_id = ? AND subscription_user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setLong(2, subscriptionUserId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки существования подписки: " + e.getMessage(), e);
        }
    }

    public List<Long> findSubscriptionUserIds(long userId) {
        List<Long> ids = new ArrayList<>();
        String sql = "SELECT subscription_user_id FROM subscriptions WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getLong("subscription_user_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения списка подписок: " + e.getMessage(), e);
        }
        return ids;
    }
}