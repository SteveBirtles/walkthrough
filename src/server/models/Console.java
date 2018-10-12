package server.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Console {
    private int id;
    private int manufacturerid;
    private String name;
    private String mediaType;
    private String year;
    private String sales;
    private boolean handheld;
    private String imageURL;
    private String notes;


    // Get IntelliJ to auto-generate a constructor, getter and setters here:

    public Console(int id, int manufacturerid, String name, String mediaType, String year, String sales, boolean handheld, String imageURL, String notes) {
        this.id = id;
        this.manufacturerid = manufacturerid;
        this.name = name;
        this.mediaType = mediaType;
        this.year = year;
        this.sales = sales;
        this.handheld = handheld;
        this.imageURL = imageURL;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManufacturerid() {
        return manufacturerid;
    }

    public void setManufacturerid(int manufacturerid) {
        this.manufacturerid = manufacturerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public boolean getHandheld() {
        return handheld;
    }

    public void setHandheld(boolean handheld) {
        this.handheld = handheld;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static ArrayList<Console> consoles = new ArrayList<>();

    public static int nextId() {
        int id = 0;
        for (Console g: consoles) {
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
        j.put("manufacturerid", getManufacturerid());
        j.put("name", getName());
        j.put("mediaType", getMediaType());
        j.put("year", getYear());
        j.put("sales", getSales());
        j.put("handheld", getHandheld());
        j.put("imageURL", getImageURL());
        j.put("notes", getNotes());
        return j;
    }
}