package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import control.Controller;
import model.Day;
import model.Observer;

public class AppView extends JFrame implements Observer, ActionListener{
    
    public JLabel monthLabel, yearLabel;
    public JButton btnPrev, btnNext, btnImport, btnAddEvent;
    public JScrollPane scrollCalendarTable;
    public DefaultTableModel modelCalendarTable;
    public JTable calendarTable;
    public JPanel calendarPanel;
    public int day, month, year;
    private JFileChooser chooseFile;
    private Iterator<Day> dayList;
    
    private JButton btnAddFB;
    private JButton btnAddSMS;
    
    private Controller controller;
    
    public AppView(Controller controller)
    {
        try 
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
            {
	            if ("Nimbus".equals(info.getName())) 
	            {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
            }
        }
        catch (Exception e) {}
        
        this.controller = controller;
        calendarPanel = new JPanel(null);
        calendarPanel.setPreferredSize (new Dimension (540, 600));
        calendarPanel.setLayout (null);
        
        this.setTitle("Much Calendar");
        this.setSize(540, 600);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add (calendarPanel);
        this.pack();
        this.setVisible (true);
       
        GregorianCalendar cal = new GregorianCalendar();
        chooseFile = new JFileChooser();
        chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        btnPrev = new JButton ("<<");
        btnPrev.addActionListener(this);
        btnNext = new JButton (">>");
        btnNext.addActionListener(this);
        btnImport = new JButton("Import files");
        btnImport.addActionListener(this);
        btnAddEvent = new JButton("Add Event");
        btnAddEvent.addActionListener(this);
        btnAddFB = new JButton("Add FB View");
        btnAddFB.addActionListener(this);
        btnAddSMS = new JButton("Add SMS View");
        btnAddSMS.addActionListener(this);
        monthLabel = new JLabel ("January");
        yearLabel = new JLabel ("2014");
        modelCalendarTable = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int mColIndex){
                        return true;
            }
        };
        String[] headers = {"Day", "Event"};
        modelCalendarTable.setColumnIdentifiers(headers);

        calendarTable = new JTable(modelCalendarTable);
        scrollCalendarTable = new JScrollPane(calendarTable);
       
        calendarPanel.add(monthLabel);
        calendarPanel.add(yearLabel);
        calendarPanel.add(btnPrev);
        calendarPanel.add(btnNext);
        calendarPanel.add(scrollCalendarTable);
        calendarPanel.add(btnImport);
        calendarPanel.add(btnAddFB);
        calendarPanel.add(btnAddSMS);
        calendarPanel.add(btnAddEvent);
       
        calendarPanel.setBounds(0, 0, 540, 600);
        monthLabel.setBounds((500-monthLabel.getPreferredSize().width)/2, 50, 200, 50);
        yearLabel.setBounds((510-yearLabel.getPreferredSize().width)/2+monthLabel.getPreferredSize().width, 50, 200, 50);
        btnPrev.setBounds(20, 50, 100, 50);
        btnNext.setBounds(420, 50, 100, 50);
        btnImport.setBounds(160, 550, 110, 50);
        btnAddFB.setBounds(40, 550, 110, 50);
        btnAddSMS.setBounds(280, 550, 110, 50);
        btnAddEvent.setBounds(400, 550, 100, 50);
        scrollCalendarTable.setBounds(20, 100, 500, 450);
       
        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

       
        calendarTable.setCellSelectionEnabled(false);
        calendarTable.setRowSelectionAllowed(false);
        calendarTable.setColumnSelectionAllowed(false);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        calendarTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        calendarTable.setRowHeight(75);
        modelCalendarTable.setColumnCount(2);
        calendarTable.getColumnModel().getColumn(0).setPreferredWidth(33);
        calendarTable.getColumnModel().getColumn(1).setPreferredWidth(446);
        calendarTable.setRowHeight(75);
       
        GregorianCalendar gregory = new GregorianCalendar();
        this.day = gregory.get(GregorianCalendar.DAY_OF_MONTH);
        this.month = gregory.get(GregorianCalendar.MONTH);
        this.year = gregory.get(GregorianCalendar.YEAR);

        updateCalendar(month, year);
        calendarTable.getColumn("Day").setCellRenderer(new TableRenderer());
    }
    
    public void updateCalendar(int month, int year)
    {
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthLabel.setText(months[month]);
        yearLabel.setText("" + year);
        GregorianCalendar gregory = new GregorianCalendar(year, month, 1);
        int nod = gregory.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        modelCalendarTable.setRowCount(nod);
        for(int i = 1; i<=31; i++){
            if(i<=nod)
                modelCalendarTable.setValueAt(i, i-1, 0);
        }
		controller.getEvents(month+1, year);
		
        monthLabel.setBounds((500-monthLabel.getPreferredSize().width)/2, 50, 200, 50);
        yearLabel.setBounds((510-yearLabel.getPreferredSize().width)/2+monthLabel.getPreferredSize().width, 50, 200, 50);
    }
    
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		// TODO Auto-generated method stub
		if(ae.getSource() == btnPrev)
		{
			month--;
	        if(month<0)
	        {
	        	month = 11;
	        	year--;
	        }
	        updateCalendar(month, year);
	    }
		else if(ae.getSource() == btnNext)
		{
			month++;
	        if(month>11){
	            month = 0;
	            year++;
	        }
	        updateCalendar(month, year);
	    }
		else if(ae.getSource() == btnImport)
		{
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("Supported Files", "csv", "psv", "txt");
	        chooseFile.setFileFilter(filter);
	        int returnVal = chooseFile.showOpenDialog(null);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        String file = null;
				try {
					file = chooseFile.getSelectedFile().getCanonicalPath();
				} catch (IOException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				controller.importFile(file, month+1, year);
	        }
	    }
		else if(ae.getSource() == btnAddFB)
		{
			controller.addObserver("FBView");
		}
		else if(ae.getSource() == btnAddSMS)
		{
			controller.addObserver("SMSView");
		}
        else if(ae.getSource() == btnAddEvent)
        {
            String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            GregorianCalendar greg = new GregorianCalendar();
            int max = greg.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            JPanel addEventP= new JPanel();
            JTextField eventTF = new JTextField(15);
            String[] colorBoxItems = {"Orange", "Red", "Green", "Blue", "Yellow"};
            JComboBox colorBox = new JComboBox (colorBoxItems);
            final JComboBox monthBox = new JComboBox (months);
            final JComboBox yearBox = new JComboBox ();
            final JComboBox dateBox = new JComboBox ();
            monthBox.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    int selectedMonth = monthBox.getSelectedIndex();
                    int selectedYear = yearBox.getSelectedIndex();
                    GregorianCalendar mountain = new GregorianCalendar(selectedYear, selectedMonth, 1);
                    dateBox.removeAllItems();
                    int clegane = mountain.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                    
                    for (int i = 1; i <= clegane; i++)
                    {
                        dateBox.addItem(String.valueOf(i));
                    }
                }
            });
            for (int i = year-100; i <= year+100; i++)
            {
            	yearBox.addItem(String.valueOf(i));
            }
            for (int i = 1; i <= max; i++)
            {
            	dateBox.addItem(String.valueOf(i));
            }
            addEventP.add(new JLabel("Event: "));
            addEventP.add(eventTF);
            addEventP.add(dateBox);
            addEventP.add(monthBox);
            addEventP.add(yearBox);
            addEventP.add(colorBox);
            
            int opt = JOptionPane.showConfirmDialog(null, addEventP, "Add Event", JOptionPane.OK_CANCEL_OPTION);
            if(opt == JOptionPane.OK_OPTION)
            {
                String date =  (monthBox.getSelectedIndex()+1) + "/" + dateBox.getSelectedItem().toString() + "/" + yearBox.getSelectedItem();
                controller.performAction(Controller.Action.addEvent, null, eventTF.getText(), date, colorBox.getSelectedItem().toString());
                updateCalendar(month, year);
                modelCalendarTable.fireTableDataChanged();
            }
        }
	}

	@Override
	public void update(String name, int month, int day, int year, String color) 
	{
		return;
	}

	@Override
	public void update(ArrayList<Day> days) {
		// TODO Auto-generated method stub
		new EventTableCellRenderer(calendarTable, 1, days, controller, month, year);
	}
}