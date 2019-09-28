package com.example.tourmatebase03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event  implements Serializable {

        private String eventID;
        private String name;
        private String destination;
        private int budget;
        private int expense;
        private String date;

    public static final List<Event> eventList = new ArrayList<>();



    public Event(String eventID, String name, String destination, int budget) {
            this.eventID = eventID;
            this.name = name;
            this.destination = destination;
            this.budget = budget;
        }

        public Event(String name, String destination, int budget) {
            this.name = name;
            this.destination = destination;
            this.budget = budget;
        }

        public Event() {
        }

        public String getEventID() {
            return eventID;
        }

        public void setEventID(String eventID) {
            this.eventID = eventID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }
    }


