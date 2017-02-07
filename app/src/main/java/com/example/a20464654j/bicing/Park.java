package com.example.a20464654j.bicing;

/**
 * Created by 20464654j on 03/02/17.
 */

public class Park {

    private String id;
    private String name;
    private String lat;
    private String lon;
    private String type;
    private int slots;
    private int bykes;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getSlots() { return slots; }

    public void setSlots(int slots) { this.slots = slots; }

    public int getBykes() { return bykes; }

    public void setBykes(int bykes) { this.bykes = bykes; }
}
