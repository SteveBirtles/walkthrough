package server.models.services;

import server.DatabaseConnection;
import server.Logger;
import server.models.Console;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ConsoleService {

    public static String selectAllInto(List<Console> targetList) {
        targetList.clear();
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT ConsoleId, ManufacturerId, Name, MediaType, Year, Sales, Handheld, ImageURL, Notes FROM Consoles"
            );
            if (statement != null) {
                ResultSet results = statement.executeQuery();
                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Console(results.getInt("ConsoleId"), results.getInt("ManufacturerId"), results.getString("Name"), results.getString("MediaType"), results.getString("Year"), results.getString("Sales"), results.getBoolean("Handheld"), results.getString("ImageURL"), results.getString("Notes")));


                    }
                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select all from 'Consoles' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
        return "OK";
    }

    public static Console selectById(int id) {
        Console result = null;
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT ConsoleId, ManufacturerId, Name, MediaType, Year, Sales, Handheld, ImageURL, Notes FROM Consoles WHERE ConsoleId = ?"
            );
            if (statement != null) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (results != null && results.next()) {
                    result = new Console(results.getInt("ConsoleId"), results.getInt("ManufacturerId"), results.getString("Name"), results.getString("MediaType"), results.getString("Year"), results.getString("Sales"), results.getBoolean("Handheld"), results.getString("ImageURL"), results.getString("Notes"));


                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select by id from 'Consoles' table: " + resultsException.getMessage();

            Logger.log(error);
        }
        return result;
    }

    public static String insert(Console itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "INSERT INTO Consoles (ConsoleId, ManufacturerId, Name, MediaType, Year, Sales, Handheld, ImageURL, Notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, itemToSave.getId());
            statement.setInt(2, itemToSave.getManufacturerid());
            statement.setString(3, itemToSave.getName());
            statement.setString(4, itemToSave.getMediaType());
            statement.setString(5, itemToSave.getYear());
            statement.setString(6, itemToSave.getSales());
            statement.setBoolean(7, itemToSave.getHandheld());
            statement.setString(8, itemToSave.getImageURL());
            statement.setString(9, itemToSave.getNotes());

            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't insert into 'Consoles' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String update(Console itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "UPDATE Consoles SET ManufacturerId = ?, Name = ?, MediaType = ?, Year = ?, Sales = ?, Handheld = ?, ImageURL = ?, Notes = ? WHERE ConsoleId = ?"
            );
            statement.setInt(1, itemToSave.getManufacturerid());
            statement.setString(2, itemToSave.getName());
            statement.setString(3, itemToSave.getMediaType());
            statement.setString(4, itemToSave.getYear());
            statement.setString(5, itemToSave.getSales());
            statement.setBoolean(6, itemToSave.getHandheld());
            statement.setString(7, itemToSave.getImageURL());
            statement.setString(8, itemToSave.getNotes());

            statement.setInt(9, itemToSave.getId());
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't update 'Consoles' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String deleteById(int id) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "DELETE FROM Consoles WHERE ConsoleId = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't delete by id from 'Consoles' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

}