package file;

public class FileFactory {
    public static File getFile(String fileName)
    {
		if(fileName.endsWith(".csv"))
			return new CSVFile(fileName);
		
		else if(fileName.endsWith(".psv"))
	        return new PSVFile(fileName);
	
        else if(fileName.endsWith(".txt"))
            return new TXTFile(fileName);
        
		return null;
    }
}
