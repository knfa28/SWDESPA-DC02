package model;

public class TaskEvent extends Event{
    private boolean completed;
    
    public TaskEvent(String name, String color) 
    {
    	super(name, color);
    }
    
    public boolean isCompleted()
    {
    	return completed;
    }
    
    public void setCompleted(boolean completed)
    {
        this.completed = completed;
        
        if(completed)
            super.setColor("Green");
        else 
        	super.setColor("Red");
    }
    
    public void checkTask()
    {
        if(completed)
            setCompleted(false);
        else setCompleted(true);
    }
    
    @Override
    public String toString()
    {
        String status;
        
        if(completed)
            status = "Complete";
        else 
        	status = "Incomplete";
        
        return super.toString() + "\n" + status;
    }
    
    public String getType()
    {
    	return "Task";
    }
}
