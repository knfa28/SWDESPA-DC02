package control;

import file.File;
import file.FileFactory;
import model.Event;
import model.EventFactory;
import model.EventManager;
import model.ViewFactory;
import view.AppView;

public class Controller {
    private EventManager em;
    
    public static enum Action 
    {
        addEvent, checkTask, attendMeeting, skipMeeting;
    };
    
    public Controller(EventManager em)
    {
        this.em = em;
    }
	
    public void addEvent(String name, String color, String date)
    {
    	Event event = EventFactory.getEvent(name, color);
    	if(!em.eventExists(date, event))
    		em.addEvent(date, event);
    }
	
    public void importFile(String fileName, int month, int year)
    {
		File file = FileFactory.getFile(fileName);
		em.importList(file.getList(), month, year);
    }
	
    public void addView(AppView view){
        em.addView(view);
    }
    
    public void addObserver(String view){
        em.addObserver(ViewFactory.getObserver(view));
    }
    
    public void getEvents(int month, int year){
    	em.getEvents(month, year);
    }
    
    public void performAction(Action a, Event ev, String eventName, String date, String color){        
        if(Action.addEvent.equals(a)){     
            this.addEvent(eventName, color, date);
	} else if(Action.checkTask.equals(a)){
            em.checkTask(ev);
	} else if(Action.attendMeeting.equals(a)){
            em.attendMeeting(ev);
	} else if(Action.skipMeeting.equals(a)){
            em.skipMeeting(ev);
	}
    }
}
