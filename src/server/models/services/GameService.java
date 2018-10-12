package server.models.services;

import server.Logger;
import server.DatabaseConnection;
import server.models.Game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GameService {

    public static String selectAllInto(List<Game> targetList) {
        targetList.clear();
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT GameId, ConsoleId, Name, Sales, Year, ImageURL FROM Games"
            );
            if (statement != null) {
                ResultSet results = statement.executeQuery();
                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Game(results.getInt("GameId"), results.getInt("ConsoleId"), results.getString("Name"), results.getString("Sales"), results.getString("Year"), results.getString("ImageURL")));


                    }
                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select all from 'Games' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
        return "OK";
    }

    public static Game selectById(int id) {
        Game result = null;
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "SELECT GameId, ConsoleId, Name, Sales, Year, ImageURL FROM Games WHERE GameId = ?"
            );
            if (statement != null) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (results != null && results.next()) {
                    result = new Game(results.getInt("GameId"), results.getInt("ConsoleId"), results.getString("Name"), results.getString("Sales"), results.getString("Year"), results.getString("ImageURL"));


                }
            }
        } catch (SQLException resultsException) {
            String error = "Database error - can't select by id from 'Games' table: " + resultsException.getMessage();

            Logger.log(error);
        }
        return result;
    }

    public static String insert(Game itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "INSERT INTO Games (GameId, ConsoleId, Name, Sales, Year, ImageURL) VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, itemToSave.getId());
            statement.setInt(2, itemToSave.getConsoleId());
            statement.setString(3, itemToSave.getName());
            statement.setString(4, itemToSave.getSales());
            statement.setString(5, itemToSave.getYear());
            statement.setString(6, itemToSave.getImageURL());




            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't insert into 'Games' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String update(Game itemToSave) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "UPDATE Games SET ConsoleId = ?, Name = ?, Sales = ?, Year = ?, ImageURL = ? WHERE GameId = ?"
            );
            statement.setInt(1, itemToSave.getConsoleId());
            statement.setString(2, itemToSave.getName());
            statement.setString(3, itemToSave.getSales());
            statement.setString(4, itemToSave.getYear());
            statement.setString(5, itemToSave.getImageURL());




            statement.setInt(6, itemToSave.getId());
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't update 'Games' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

    public static String deleteById(int id) {
        try {
            PreparedStatement statement = DatabaseConnection.newStatement(
                    "DELETE FROM Games WHERE GameId = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            return "OK";
        } catch (SQLException resultsException) {
            String error = "Database error - can't delete by id from 'Games' table: " + resultsException.getMessage();

            Logger.log(error);
            return error;
        }
    }

}