package server.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;

    // Get IntelliJ to auto-generate a constructor, getter and setters here:


    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Category> categories = new ArrayList<>();

    public static int nextId() {
        int id = 0;
        for (Category c: categories) {
            if (c.getId() > id) {
                id = c.getId();
            }
        }
        return id + 1;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("id", getId());
        j.put("name", getName());

        return j;
    }
}