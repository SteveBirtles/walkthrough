package server.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Game {
    private int id;
    private int consoleId;
    private String name;
    private String sales;
    private String year;
    private String imageURL;

    // Get IntelliJ to auto-generate a constructor, getter and setters here:

    public Game(int id, int consoleId, String name, String sales, String year, String imageURL) {
        this.id = id;
        this.consoleId = consoleId;
        this.name = name;
        this.sales = sales;
        this.year = year;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(int consoleId) {
        this.consoleId = consoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static ArrayList<Game> games = new ArrayList<>();

    public static int nextId() {
        int id = 0;
        for (Game g: games) {
            if (g.getId() > id) {
                id = g.getId();
            }
        }
        return id + 1;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("id", getId());
        j.put("consoleId", getConsoleId());
        j.put("name", getName());
        j.put("sales", getSales());
        j.put("year", getYear());
        j.put("imageURL", getImageURL());

        return j;
    }
}