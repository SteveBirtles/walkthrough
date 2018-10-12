package server.models.services;

import server.Logger;
import server.DatabaseConnection;
import server.models.Manufacturer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManufacturerService {

    public static String selectAllInto(List<Manufacturer> targetList) {
        targetList.clear();
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT ManufacturerId, Name FROM Manufacturers"
            );
            if (statement != null) {
                ResultSet results = statement.executeQuery();
                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Manufacturer(results.getInt("ManufacturerId"), results.getString("Name")));


                    }
                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select all from 'Manufacturers' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
        return "OK";
    }

    public static Manufacturer selectById(int id) {
        Manufacturer result = null;
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT ManufacturerId, Name FROM Manufacturers WHERE ManufacturerId = ?"
            );
            if (statement != null) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (results != null && results.next()) {
                    result = new Manufacturer(results.getInt("ManufacturerId"), results.getString("Name"));


                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select by id from 'Manufacturers' table: " + resultsException.getMessage();

            Logger.log(error);
        }
        return result;
    }

    public static String insert(Manufacturer itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "INSERT INTO Manufacturers (ManufacturerId, Name) VALUES (?, ?)"
            );
            statement.setInt(1, itemToSave.getId());
            statement.setString(2, itemToSave.getName());


            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't insert into 'Manufacturers' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String update(Manufacturer itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "UPDATE Manufacturers SET Name = ? WHERE ManufacturerId = ?"
            );
            statement.setString(1, itemToSave.getName());


            statement.setInt(2, itemToSave.getId());
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't update 'Manufacturers' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String deleteById(int id) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "DELETE FROM Manufacturers WHERE ManufacturerId = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't delete by id from 'Manufacturers' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

}