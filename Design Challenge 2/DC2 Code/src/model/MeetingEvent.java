package model;

public class MeetingEvent extends Event{
    private boolean attending;
	
    public MeetingEvent(String name, String color) 
    {
    	super(name, color);
    }

    public boolean isAttending()
    {
    	return attending;
    }

    public void setAttending(boolean attending)
    {
    	this.attending = attending;
        
        if(attending)
            super.setColor("Yellow");
        else 
        	super.setColor("Orange");
    }
        
    @Override
    public String toString()
    {
        String status;
        
        if(attending)
            status = "Attending";
        else 
        	status = "Not attending";
        
        return super.getName() + "\n" + status;
    }
    
    public String getType()
    {
    	return "Meeting";
    }
}
