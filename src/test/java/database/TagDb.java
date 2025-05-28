package database;

import pojo.TagPojo;

import java.sql.*;

public class TagDb extends JDBC {
    public TagPojo getTagById(int id) {
        String sql = "SELECT * FROM wp_terms AS t JOIN wp_term_taxonomy AS tt ON t.term_id = tt.term_id WHERE t.term_id = ?;";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet results = preparedStatement.executeQuery()) {
                if (results.next()) {
                    return TagPojo.builder()
                            .id(results.getInt("term_id"))
                            .name(results.getString("name"))
                            .description(results.getString("description"))
                            .count(results.getInt("count"))
                            .slug(results.getString("slug"))
                            .taxonomy(results.getString("taxonomy"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while working with storage: " + e.getMessage());
        }
        return null;
    }

    public int createTag(TagPojo tag) {
        String sql1 = "INSERT INTO wp_terms (name, slug) VALUES (?, ?);";
        String sql2 = "INSERT INTO wp_term_taxonomy (term_id, taxonomy, description, count) VALUES (?, ?, ?);";
        try (Connection connection = connectionToDatabase(); PreparedStatement preparedStatement = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS); PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {
            int id;
            //
            preparedStatement.setString(1, tag.getName());
            preparedStatement.setString(2, tag.getSlug());
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Нет ID.");
                }
            }
            preparedStatement2.setString(1, Integer.toString(id));
            preparedStatement2.setString(2, tag.getTaxonomy());
            preparedStatement2.setString(3, tag.getDescription());
            preparedStatement2.setString(4, Integer.toString(tag.getCount()));
            preparedStatement2.executeUpdate();
            return id;
        } catch (SQLException e) {
            System.out.println("Errow while working with storage");
        }
        return -1;
    }

    public void deleteTagById(int id) {
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
