package server.models.services;

import server.Logger;
import server.DatabaseConnection;
import server.models.Accessory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccessoryService {

    public static String selectAllInto(List<Accessory> targetList) {
        targetList.clear();
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT AccessoryId, CategoryId, ConsoleId, Description, Quantity, ThirdParty, ImageURL FROM Accessories"
            );
            if (statement != null) {
                ResultSet results = statement.executeQuery();
                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Accessory(results.getInt("AccessoryId"), results.getInt("CategoryId"), results.getInt("ConsoleId"), results.getString("Description"), results.getInt("Quantity"), results.getBoolean("ThirdParty"), results.getString("ImageURL")));


                    }
                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select all from 'Accessories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
        return "OK";
    }

    public static Accessory selectById(int id) {
        Accessory result = null;
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT AccessoryId, CategoryId, ConsoleId, Description, Quantity, ThirdParty, ImageURL FROM Accessories WHERE AccessoryId = ?"
            );
            if (statement != null) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (results != null && results.next()) {
                    result = new Accessory(results.getInt("AccessoryId"), results.getInt("CategoryId"), results.getInt("ConsoleId"), results.getString("Description"), results.getInt("Quantity"), results.getBoolean("ThirdParty"), results.getString("ImageURL"));


                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select by id from 'Accessories' table: " + resultsException.getMessage();

            Logger.log(error);
        }
        return result;
    }

    public static String insert(Accessory itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "INSERT INTO Accessories (AccessoryId, CategoryId, ConsoleId, Description, Quantity, ThirdParty, ImageURL) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, itemToSave.getId());
            statement.setInt(2, itemToSave.getCategoryId());
            statement.setInt(3, itemToSave.getConsoleId());
            statement.setString(4, itemToSave.getDescription());
            statement.setInt(5, itemToSave.getQuantity());
            statement.setBoolean(6, itemToSave.isThirdParty());
            statement.setString(7, itemToSave.getImageURL());



            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't insert into 'Accessories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String update(Accessory itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "UPDATE Accessories SET CategoryId = ?, ConsoleId = ?, Description = ?, Quantity = ?, ThirdParty = ?, ImageURL = ? WHERE AccessoryId = ?"
            );
            statement.setInt(1, itemToSave.getCategoryId());
            statement.setInt(2, itemToSave.getConsoleId());
            statement.setString(3, itemToSave.getDescription());
            statement.setInt(4, itemToSave.getQuantity());
            statement.setBoolean(5, itemToSave.isThirdParty());
            statement.setString(6, itemToSave.getImageURL());



            statement.setInt(7, itemToSave.getId());
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't update 'Accessories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String deleteById(int id) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "DELETE FROM Accessories WHERE AccessoryId = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't delete by id from 'Accessories' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

}