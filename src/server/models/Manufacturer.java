package server.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Manufacturer {
    private int id;
    private String name;

    // Get IntelliJ to auto-generate a constructor, getter and setters here:


    public Manufacturer(int id, String name) {
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

    public static ArrayList<Manufacturer> manufacturers = new ArrayList<>();

    public static int nextId() {
        int id = 0;
        for (Manufacturer m: manufacturers) {
            if (m.getId() > id) {
                id = m.getId();
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