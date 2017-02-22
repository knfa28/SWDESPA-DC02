package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import control.Controller;
import model.Day;
import model.Event;
import model.MeetingEvent;
import model.TaskEvent;

public class EventTableCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener
{
	private JTable table;
	private JPanel panel;
    private Controller controller;
    private ArrayList<Day> days;
    
    private int month;
    private int year;

    public EventTableCellRenderer(JTable table, int column, ArrayList<Day> days, Controller controller, int month, int year)
    {
        super();
        this.table = table;
        this.controller = controller;
        this.days = days;
        this.month = month;
        this.year = year;
        panel = new JPanel();
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
    }

    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
            final JTable tempTable = table;
            final int tempColumn = column;
    	 	
	    	if (hasFocus)
	        {
	            panel.setForeground(table.getForeground());
	            panel.setBackground(UIManager.getColor("Button.background"));
	        }
	        else if (isSelected)
	        {
	            panel.setForeground(table.getSelectionForeground());
	            panel.setBackground(table.getSelectionBackground());
	        }
	        else
	        {
	            panel.setForeground(table.getForeground());
	            panel.setBackground(UIManager.getColor("Button.background"));
	        }
	    	
	    	panel.removeAll();
	    	for(Day day : days)
	    	{
	    		if(row+1 == day.getDay())
	    		{
	    			Event event = (Event) day.getEvents().next();
	    			JTextField eventTF = new JTextField(20);
	    			//panel.add(new JLabel(event.getName()));
	    			eventTF.setEditable(false);
	    			eventTF.setText(event.getName());
	    			panel.add(eventTF);
	    			panel.setBackground(event.getColor());
	    			
                    if(event.getClass().getName().contains("MeetingEvent"))
                    {
	    				final MeetingEvent me = (MeetingEvent) day.getEvents().next();
	    				final JButton btnAtt = new JButton("Attending");
	    				final JButton btnNotAtt = new JButton("Not Attending");
	    				
                        if(me.isAttending())
                        {
	    					btnAtt.setEnabled(false);
	    					btnNotAtt.setEnabled(true);
	    				}
                        else
                        {
	    					btnAtt.setEnabled(true);
	    					btnNotAtt.setEnabled(false);
	    				}
                                                                               
	    				panel.add(btnAtt);
	    				panel.add(btnNotAtt);
                                        
                        btnAtt.addActionListener(new ActionListener(){
                            @Override 
                            public void actionPerformed(ActionEvent e) {
                            		fireEditingStopped();
                                    controller.performAction(Controller.Action.attendMeeting, me, null, null, null);
                                    controller.getEvents(month, year);
                                    new EventTableCellRenderer(tempTable, tempColumn, days, controller, month, year);
                           }
                        });
                        
                        btnNotAtt.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e) {
                            	fireEditingStopped();
                                controller.performAction(Controller.Action.skipMeeting, me, null, null, null);
                                controller.getEvents(month, year);
                                new EventTableCellRenderer(tempTable, tempColumn, days, controller, month, year);
                            }
                        });                                          
                    }       
	    			else if(event.getClass().getName().contains("TaskEvent"))
	    			{
	    				final TaskEvent te = (TaskEvent) day.getEvents().next();
	    				final JButton btnCompleteUndo = new JButton();
	    				
                        if(te.isCompleted())
                            btnCompleteUndo.setText("Undo");
                        else btnCompleteUndo.setText("Complete");
	    				
	    				panel.add(btnCompleteUndo);
	                    if(event.getColor() == Color.red)
	                    	btnCompleteUndo.setBackground(Color.green);
	                    else
	                    	btnCompleteUndo.setBackground(Color.red);
	                    btnCompleteUndo.addActionListener(new ActionListener(){
	                        @Override
	                        public void actionPerformed(ActionEvent e) {
	                        	fireEditingStopped();
	                            controller.performAction(Controller.Action.checkTask, te, null, null, null);
	                            controller.getEvents(month, year);
	                            new EventTableCellRenderer(tempTable, tempColumn, days, controller, month, year);
	                        }
	                    });
	    			}
                }
            }
	    	
	    	try 
	    	{
				this.finalize();
			} catch (Throwable e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	fireEditingStopped();
	        return panel;
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        return panel;
    }

    public Object getCellEditorValue()
    {
        return panel;
    }

    public void actionPerformed(ActionEvent e)
    {
        fireEditingStopped();
        System.out.println( e.getActionCommand() + " : " + table.getSelectedRow());
    }
}

