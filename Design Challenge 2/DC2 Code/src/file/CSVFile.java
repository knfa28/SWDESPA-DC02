package file;

public class CSVFile extends File{
    
    public CSVFile(String filename)
    {
    	super(filename);
        super.setParameters(", ", 2,0,1);
        
        super.read();
    }
}
