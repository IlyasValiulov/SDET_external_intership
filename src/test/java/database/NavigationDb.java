package database;

import pojo.NavigationPojo;

import java.sql.*;

public class NavigationDb extends JDBC {
    public NavigationPojo getNavigationById(int id) {
        String sql = "SELECT * FROM wp_posts WHERE post_type = 'wp_navigation'";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet results = preparedStatement.executeQuery()) {
                if (results.next()) {
                    return NavigationPojo.builder()
                            .id(results.getInt("id"))
                            .date(results.getString("post_date"))
                            .date_gmt(results.getString("post_date_gmt"))
                            .modified(results.getString("post_modified"))
                            .modified_gmt(results.getString("post_modified_gmt"))
                            .guid(results.getString("guid"))
                            .slug(results.getString("post_name"))
                            .status(results.getString("post_status"))
                            .type(results.getString("post_type"))
                            .password(results.getString("post_password"))
                            .title(results.getString("post_title"))
                            .content(results.getString("post_content"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while working with storage: " + e.getMessage());
        }
        return null;
    }

    public int createNavigation(NavigationPojo navigation) {
        String sql = "INSERT INTO wp_posts (status, type, title, content) VALUES (?, ?, ?, ?);";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int id;
            preparedStatement.setString(1, navigation.getStatus());
            preparedStatement.setString(2, navigation.getType());
            preparedStatement.setString(3, navigation.getTitle());
            preparedStatement.setString(4, navigation.getContent());
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Нет ID.");
                }
            }
            return id;
        } catch (SQLException e) {
            System.out.println("Errow while working with storage");
        }
        return -1;
    }

    public void deleteNavigationById(int id) {
        String sql = "DELETE FROM wp_posts WHERE id = ?;";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errow while working with storage");
        }
    }
}
