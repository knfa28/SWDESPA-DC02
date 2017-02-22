package model;

import java.util.ArrayList;

public interface Observer {
    public void update(String event, int month, int day, int year, String color);
    public void update(ArrayList<Day> days);
}
