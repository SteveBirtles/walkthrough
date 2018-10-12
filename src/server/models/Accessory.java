package server.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Accessory {
    private int id;
    private int categoryId;
    private int consoleId;
    private String description;
    private int quantity;
    private boolean thirdParty;
    private String imageURL;




    // Get IntelliJ to auto-generate a constructor, getter and setters here:

    public Accessory(int id, int categoryId, int consoleId, String description, int quantity, boolean thirdParty, String imageURL) {
        this.id = id;
        this.categoryId = categoryId;
        this.consoleId = consoleId;
        this.description = description;
        this.quantity = quantity;
        this.thirdParty = thirdParty;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(int consoleId) {
        this.consoleId = consoleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(boolean thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static ArrayList<Accessory> accessories = new ArrayList<>();

    public static int nextId() {
        int id = 0;
        for (Accessory a: accessories) {
            if (a.getId() > id) {
                id = a.getId();
            }
        }
        return id + 1;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("id", getId());
        j.put("categoryId", getCategoryId());
        j.put("consoleId", getConsoleId());
        j.put("description", getDescription());
        j.put("quantity", getQuantity());
        j.put("thirdParty", isThirdParty());
        j.put("imageURL", getImageURL());



        return j;
    }
}