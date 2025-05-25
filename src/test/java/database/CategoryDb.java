package database;

import pojo.CategoryPojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryDb extends JDBC {
    public CategoryPojo getCategoryById(int id) {
        String sql = "SELECT * FROM wp_terms AS t JOIN wp_term_taxonomy AS tt ON t.term_id = tt.term_id WHERE t.term_id = ?;";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet results = preparedStatement.executeQuery()) {
                if (results.next()) {
                    return CategoryPojo.builder()
                            .id(results.getInt("term_id"))
                            .name(results.getString("name"))
                            .description(results.getString("description"))
                            .taxonomy(results.getString("taxonomy"))
                            .slug(results.getString("slug"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while working with storage: " + e.getMessage());
        }
        return null;
    }

    public int createCategory(CategoryPojo category) {
        String sql1 = "INSERT INTO wp_terms (name, slug) VALUES (?, ?);";
        String sql2 = "INSERT INTO wp_term_taxonomy (term_id, taxonomy, description) VALUES (?, ?, ?);";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS); PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {
            int id;
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getSlug());
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Нет ID.");
                }
            }
            preparedStatement2.setString(1, Integer.toString(id));
            preparedStatement2.setString(2, category.getTaxonomy());
            preparedStatement2.setString(3, category.getDescription());
            preparedStatement2.executeUpdate();
            return id;
        } catch (SQLException e) {
            System.out.println("Errow while working with storage");
        }
        return -1;
    }

    public void deleteCategoryById(int id) {
        String sql1 = "DELETE FROM wp_terms WHERE term_id = ?;";
        String sql2 = "DELETE FROM wp_term_taxonomy WHERE term_id = ?;";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement1 = connection.prepareStatement(sql1); PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {
            preparedStatement1.setString(1, Integer.toString(id));
            preparedStatement1.executeUpdate();
            preparedStatement2.setString(1, Integer.toString(id));
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errow while working with storage");
        }
    }
}
