package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import view.AppView;

public class EventManager implements Subject{
    private ArrayList<Day> dayList;
    private ArrayList<Observer> observerList;
    
    private String event;
    private int month;
    private int day;
    private int year;
    private String color;
            
    public EventManager()
    {
    	dayList = new ArrayList<Day>();
    	observerList = new ArrayList<Observer>();
    } 
	
    public void addView(AppView view)
    {
    	register(view);
    }
    
    public void addObserver(Observer ob)
    {
        register(ob);
        Calendar cal = new GregorianCalendar();
		int month = cal.get(GregorianCalendar.MONTH) + 1;
		int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int year = cal.get(GregorianCalendar.YEAR);

		for(Day d : dayList)
		{
			if(d.getMonth() == month && d.getDay() == day && d.getYear() == year)
			{
				Iterator events = d.getEvents();
				while(events.hasNext())
				{
					Event event = (Event) events.next();
					ob.update(event.getName(), d.getMonth(), d.getDay(), d.getYear(), event.getColorName());
				}
			}
		}
    }
	
    public void addEvent(String date, Event ev)
    {
    	int dayIndex = -1;
    	dayIndex = getDayIndex(date);
    	
    	String[] newDate = date.split("/");
        int month = Integer.parseInt(newDate[0]);
        int day = Integer.parseInt(newDate[1]);
        int year = Integer.parseInt(newDate[2]);
    	
    	if(dayIndex == -1)
    	{
    		 Day d = new Day(month, day, year);
             d.addEvent(ev);
             dayList.add(d);
    	}
    	else
    	{
    		dayList.get(dayIndex).addEvent(ev);
    	}
    	notifyNewEvent(ev.getName(), month, day, year, ev.getColorName());
    }
    
    public int getDayIndex(String date)
    {
    	String[] newDate = date.split("/");
        int month = Integer.parseInt(newDate[0]);
        int day = Integer.parseInt(newDate[1]);
        int year = Integer.parseInt(newDate[2]);
        
        int dayIndex = -1;
        
        for(int i = 0; i < dayList.size(); i++){
            if(dayList.get(i).getDate().equals(date)){
                dayIndex = i;
            }
        }
        return dayIndex;
    }
    
    public boolean eventExists(String date, Event ev)
    {
    	boolean exists = false;
        int dayIndex = getDayIndex(date);

        if(dayIndex != -1)
        {
        	Iterator events = dayList.get(dayIndex).getEvents();
        	while(events.hasNext())
            {
            	Event event = (Event) events.next();
            	if(event.getName().equals(ev.getName()))
            		exists = true;
            }
        }
        return exists;
    }
	
    public void checkTask(Event ev)
    {
        for(Day d: dayList)
        {
            Iterator events = d.getEvents();
            
            while(events.hasNext())
            {
            	Event te = (Event) events.next();
                
                if(te.getName().equals(ev.getName()))
                {
                    ((TaskEvent)te).checkTask();
                    break;
                }
            }
        }
    }
        
    public void attendMeeting(Event ev)
    {
    	for(Day d: dayList)
    	{
            Iterator events = d.getEvents();
            
            while(events.hasNext())
            {
            	Event me = (Event) events.next();
                
                if(me.getName().equals(ev.getName()))
                {
                    ((MeetingEvent)me).setAttending(true);
                    break;
                }
            }
        }
    }
    
    public void skipMeeting(Event ev)
    {
        for(Day d: dayList)
        {
            Iterator events = d.getEvents();
            
            while(events.hasNext())
            {
            	Event me = (Event) events.next();
                
                if(me.getName().equals(ev.getName()))
                {
                    ((MeetingEvent)me).setAttending(false);
                    break;
                }
            }
        }
    }
	
    public void importList(Iterator days, int month, int year)
    {
    	Calendar cal = new GregorianCalendar();
		int monthToday = cal.get(GregorianCalendar.MONTH) + 1;
		int dayToday = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int yearToday = cal.get(GregorianCalendar.YEAR);

    	while(days.hasNext())
    	{
    		Day day = (Day) days.next();
    		dayList.add(day);
    		Iterator events = day.getEvents();
    		while(events.hasNext())
    		{
    			Event event = (Event) events.next();
    			if(monthToday == day.getMonth() && dayToday == day.getDay() && yearToday == day.getYear())
    			{
    				for(Observer ob : observerList)
    				{
    					ob.update(event.getName(), day.getMonth(), day.getDay(), day.getYear(), event.getColorName());
    				}
    			}
    		}
    	}
        getEvents(month, year);
    }
    
    public Iterator<Day> getAllEvents()
    {
        return dayList.iterator();
    }
    

	public void getEvents(int month, int year)
    {
    	ArrayList<Day> currentDayEvents = new ArrayList<Day>();
    	for(Day day : dayList)
    	{
    		if(day.getMonth() == month && day.getYear() == year)
    		{
    			currentDayEvents.add(day);
    		}
    	}
    	notifyAppViews((ArrayList<Day>)currentDayEvents.clone());
    }
    
    public void notifyAppViews(ArrayList<Day> days)
    {
    	for(Observer ob : observerList)
    	{
    		if(ob.getClass().getName().contains("AppView"))
    		{
    			ob.update(days);
    		}
    	}
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
