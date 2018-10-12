package server.models.services;

import server.Logger;
import server.DatabaseConnection;
import server.models.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryService {

    public static String selectAllInto(List<Category> targetList) {
        targetList.clear();
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT CategoryId, Name FROM Categories"
            );
            if (statement != null) {
                ResultSet results = statement.executeQuery();
                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Category(results.getInt("CategoryId"), results.getString("Name")));


                    }
                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select all from 'Categories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
        return "OK";
    }

    public static Category selectById(int id) {
        Category result = null;
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT CategoryId, Name FROM Categories WHERE CategoryId = ?"
            );
            if (statement != null) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (results != null && results.next()) {
                    result = new Category(results.getInt("CategoryId"), results.getString("Name"));


                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select by id from 'Categories' table: " + resultsException.getMessage();

            Logger.log(error);
        }
        return result;
    }

    public static String insert(Category itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "INSERT INTO Categories (CategoryId, Name) VALUES (?, ?)"
            );
            statement.setInt(1, itemToSave.getId());
            statement.setString(2, itemToSave.getName());








            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't insert into 'Categories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String update(Category itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "UPDATE Categories SET Name = ? WHERE CategoryId = ?"
            );
            statement.setString(1, itemToSave.getName());








            statement.setInt(2, itemToSave.getId());
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't update 'Categories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String deleteById(int id) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "DELETE FROM Categories WHERE CategoryId = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't delete by id from 'Categories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

}