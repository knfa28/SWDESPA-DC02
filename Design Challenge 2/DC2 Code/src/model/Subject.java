package model;

public interface Subject {
    public void notifyNewEvent(String event, int month, int day, int year, String color);
    public void register(Observer o);
    public void unregister(Observer o);
    public void notifyAllViews();
}
