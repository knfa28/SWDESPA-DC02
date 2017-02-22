package model;

import java.util.ArrayList;

public class Grabber implements Subject{
    private ArrayList<Observer> observerList;
    private ArrayList<Day> dayList;
    
    private String event;
    private int month;
    private int day;
    private int year;
    private String color;
	
    public Grabber()
    {
    	observerList = new ArrayList<Observer>();
    	dayList = new ArrayList<Day>();
    }
	
    public void notifyNewEvent(String event, int month, int day, int year, String color)
    {
        this.event = event;
        this.month = month;
        this.day = day;
        this.year = year;
        this.color = color;
        notifyAllViews();
    }
	
    @Override
    public void register(Observer o) 
    {
    	observerList.add(o);
    }

    @Override
    public void unregister(Observer o) 
    {
    	observerList.remove(o);
    }

    @Override
    public void notifyAllViews() 
    {
    	for(int i = 0; i < observerList.size(); i++)
            observerList.get(i).update(event, month, day, year, color);
    }	
}
