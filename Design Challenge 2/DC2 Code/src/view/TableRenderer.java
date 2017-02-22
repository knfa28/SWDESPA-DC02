package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRenderer extends DefaultTableCellRenderer{
	
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
       
        if(column == 0)
        {
        	setBackground(new Color(100, 150,230));
        }
        else
        {
        	setBackground(Color.white);
        }
        
        //setBorder(null);
        return this;  
    }
}
