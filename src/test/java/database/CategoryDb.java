package database;

import pojo.CategoryPojo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryDb extends JDBC {
    public CategoryPojo getCategoryById(int id) {
        String sql = "SELECT * FROM wp_terms AS t JOIN wp_term_taxonomy AS tt ON t.term_id = tt.term_id WHERE t.term_id = ?;";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

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
        int id = -1;
        if (category == null) {
            System.out.println("Category is null");
            return id;
        }

        try (Connection connection  = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String name = category.getName();
            String description = category.getDescription();
            String taxonomy = category.getTaxonomy();
            String slug = category.getSlug();

            String sql = "INSERT INTO wp_terms (name, slug) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, slug);
            int results = preparedStatement.executeUpdate();

            if (results > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                }

                sql = "INSERT INTO wp_term_taxonomy (term_id, taxonomy, description) VALUES (?, ?, ?);";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, Integer.toString(id));
                preparedStatement.setString(2, taxonomy);
                preparedStatement.setString(3, description);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Errow while working with storage");
        }
        return id;
    }

    public void deleteCategoryById(int id) {
        try (Connection connection  = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM wp_terms WHERE term_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.executeUpdate();

            sql = "DELETE FROM wp_term_taxonomy WHERE term_id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errow while working with storage");
        }
    }
}
