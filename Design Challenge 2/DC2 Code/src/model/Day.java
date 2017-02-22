package model;

import java.util.ArrayList;
import java.util.Iterator;
    
public class Day {
    private int month;
    private int day;
    private int year;
	
    private ArrayList<Event> events;
	
    public Day(int month, int day, int year)
    {
		this.month = month;
		this.day = day;
		this.year = year;
		events = new ArrayList<Event>();
    }
	
    public void addEvent(Event event)
    {
    	events.add(event);
    }
	
    public void removeEvent(Event event)
    {
    	events.add(event);
    }

    public int getMonth()
    {
    	return month;
    }
    
    public void setMonth(int month)
    {
    	this.month = month;
    }
	
    public int getDay()
    {
    	return day;
    }
	
    public void setDay(int day)
    {
    	this.day = day;
    }

    public int getYear()
    {
    	return year;
    }
	
    public void setYear(int year)
    {
    	this.year = year;
    }
    
    public String getDate()
    {
        return month + "/" + day + "/" + year;
    }
    
    //for testing
    public String toString()
    {
    	String string = new String();
        
        string += month + "/" + day + "/" + year + "\n";
	
        for(int i = 0 ;i<events.size(); i++)
            string += events.get(i).toString() + "\n";
	
        return string;
    }
    
    public Iterator getEvents()
    {
        return events.iterator();
    }
}
