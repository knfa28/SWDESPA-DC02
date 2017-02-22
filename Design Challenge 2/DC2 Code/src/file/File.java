package file;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import model.Day;
import model.Event;
import model.EventFactory;

public class File {
    private String filename;
    private String splitBy;
    private int nameIndex;
    private int dateIndex;
    private int colorIndex;
    private ArrayList<Day> days;
    
    public File(String filename)
    {
		this.filename = filename;
		days = new ArrayList<Day>();
    }
    
    public void setParameters(String splitBy, int colorIndex, int dateIndex, int nameIndex)
    {
        this.splitBy = splitBy;
        this.nameIndex = nameIndex;
        this.dateIndex = dateIndex;
        this.colorIndex = colorIndex;
    }
    
    public void read()
    {
        Path path = Paths.get(filename);
        Charset cs = StandardCharsets.UTF_8;
        String temp;
		
        try(BufferedReader reader = Files.newBufferedReader(path, cs)) 
        {
            while((temp = reader.readLine()) != null)
            {
				String[] info = temp.split(splitBy);
				String[] date = info[dateIndex].split("/");

                int month = Integer.parseInt(date[0]);
                int day = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);

                Day d = new Day(month, day, year);
                Event ev = EventFactory.getEvent(info[nameIndex], info[colorIndex]);
                
                d.addEvent(ev);
                this.days.add(d);
            }			
      	}
        catch(IOException x)
        {
            System.err.println(x);
      	}
    }
	
    public Iterator getList()
    {
    	return days.iterator();
    }
}
