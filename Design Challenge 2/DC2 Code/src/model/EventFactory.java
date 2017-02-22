package model;

import java.awt.Color;

public class EventFactory {
    public static Event getEvent(String name, String color)
    {
		if(color.equalsIgnoreCase("Blue") || color.contains("Blue") )
			return new Event(name, color);
			
		else if(color.equalsIgnoreCase("Red"))
		{
	        TaskEvent te = new TaskEvent(name, color);
	        te.setCompleted(false);
	        return  te;           
	    }	
	        
		else if(color.equalsIgnoreCase("Green") || color.contains("Green") )
		{
			TaskEvent te = new TaskEvent(name, color);
	        te.setCompleted(true);
	      	return  te;  
        }   
		
        else if(color.equalsIgnoreCase("Yellow")  || color.contains("Yellow") )
        {
            MeetingEvent me = new MeetingEvent(name, color);
            me.setAttending(true);
            return  me;  
        }
        
        else if(color.equalsIgnoreCase("Orange") || color.contains("Orange") )
        {
            MeetingEvent me = new MeetingEvent(name, color);
            me.setAttending(false);
            return  me;  
        }
		
        return new Event(name, color);
    }
}
