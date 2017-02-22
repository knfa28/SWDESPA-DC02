    package facebook;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Day;
import model.Observer;

public class FBAdapter extends FBView implements Observer{

	@Override
	public void update(String event, int month, int day, int year, String colorName) 
	{
		// TODO Auto-generated method stub

        Color color = null;
        if(colorName.equalsIgnoreCase("Blue"))
            color = Color.blue;
    	else if(colorName.equalsIgnoreCase("Red"))
            color = Color.red;
    	else if(colorName.equalsIgnoreCase("Green"))
            color = Color.green;
    	else if(colorName.equalsIgnoreCase("Yellow"))
            color = Color.yellow;
        else if(colorName.equalsIgnoreCase("Orange"))
            color = Color.orange;
        
		showNewEvent(event, month, day, year, color);
	}

	@Override
	public void update(ArrayList<Day> days) 
	{
		// TODO Auto-generated method stub
		return;
	}

}
