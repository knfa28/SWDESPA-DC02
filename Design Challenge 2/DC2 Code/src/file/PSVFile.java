package file;

public class PSVFile extends File{
  
    public PSVFile(String filename)
    {
    	super(filename);
        super.setParameters(" \\| ", 2,1,0);
        
        super.read();
    }
}
