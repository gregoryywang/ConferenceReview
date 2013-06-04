package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import model.Paper;
import model.ProgramChair;
import model.User;

import controller.Controller;
import controller.PGChairViewController;

public class PGChairView extends JPanel {
  private static final long serialVersionUID = 1L;
  private List<Paper> model;
  private Controller controller;
  private JTable table;
  private JScrollPane scrollPane;
  private String[] column = {"Author","Title", "Sub-Program Chair", "Acceptance Status"};
  private ProgramChair PGChair;
  private TableModel tableModel;
  private List<Paper> data;
  
  public PGChairView(User aUser) {
    PGChair = new ProgramChair(aUser);
    model = PGChair.viewPapers();
    controller = new PGChairViewController();
    tableModel = new TableModel(model);
    table = new JTable(tableModel); 
    setLayout(new BorderLayout());
    scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.NORTH);
    JButton test = new JButton("Test");
    test.addActionListener( new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        Paper paper = getSelectedRow();
      }
      });
    add(test, BorderLayout.SOUTH);
    
    setVisible(true);
  }
  
  private Paper getSelectedRow() {   
    int selectedRow = table.getSelectedRow();
    
    if(selectedRow >= 0)
      return data.get(selectedRow);
    
    return null;
  }
  
  private class TableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private TableModel(List<Paper> model) {
      if(model == null)
        data = new ArrayList<Paper>();
      else
        data = model;
    }
    
    @Override
    public int getColumnCount() {
      return column.length;
    }

    @Override
    public int getRowCount() {
      return data.size();
    }

    @Override
    public String getColumnName(int columnIndex){
      return column[columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex ) {
      Paper paper = data.get(rowIndex); 
      String result = null;
      switch(columnIndex) {
        case 0: result = paper.getAuthor().getFullName(); break;
        case 1: result = paper.getTitle(); break;
        case 2: result = "Not Assigned"; break;
        case 3: result = paper.getStatus().toString(); break;
      }
      
      return result;
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
      return String.class;
    }
  }
}

