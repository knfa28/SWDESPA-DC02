package file;

public class TXTFile extends File{
    
    public TXTFile(String filename)
    {
    	super(filename);
        super.setParameters("_", 0,2,1);
        
        super.read();
    }
}
