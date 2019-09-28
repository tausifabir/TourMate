package com.example.tourmatebase03;

import java.io.Serializable;

public class Event  implements Serializable {


    private String id;
    private String name;
    private String starting_location;
    private String destination;
    private String date;
    private int budget;

    public String getStarting_location() {
        return starting_location;
    }

    public String getDate() {
        return date;
    }

    public Event(String id, String name, String starting_location, String destination, String date, int budget) {
        this.id = id;
        this.name = name;
        this.starting_location = starting_location;
        this.destination = destination;
        this.date = date;
        this.budget = budget;
    }

    public Event(String id, String name, String destination, int budget) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.budget = budget;
    }

    public Event() {
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public int getBudget() {
        return budget;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", budget=" + budget +
                '}';
    }
}


