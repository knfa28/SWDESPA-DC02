package model;

import facebook.FBAdapter;
import sms.SMSAdapter;

public class ViewFactory {
    public static Observer getObserver(String view)
    {
        if(view.equalsIgnoreCase("FBView"))
            return new FBAdapter();
        
        else if(view.equalsIgnoreCase("SMSView"))
            return new SMSAdapter();
        
        return null;
    }
}
