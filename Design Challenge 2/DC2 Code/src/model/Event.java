package model;

import java.awt.Color;

public class Event{

    private String name;
    private Color color;
    private String colorName;

    public Event(String name, String color)
    {
    	this.name = name;
        setColor(color);
    }

    public String getName()
    {
    	return name;
    }
	
    public Color getColor()
    {
    	return color;
    }
    
    public void setColor(String color)
    {
        if(color.equalsIgnoreCase("Blue"))
            this.color = Color.blue;
    	else if(color.equalsIgnoreCase("Red"))
            this.color = Color.red;
    	else if(color.equalsIgnoreCase("Green"))
            this.color = Color.green;
    	else if(color.equalsIgnoreCase("Yellow"))
            this.color = Color.yellow;
        else if(color.equalsIgnoreCase("Orange"))
            this.color = Color.orange;
        
        this.colorName = color;
    }
    
    public String toString()
    {
    	return name;
    }

    public String getColorName()
    {
    	return colorName;
    }
}

