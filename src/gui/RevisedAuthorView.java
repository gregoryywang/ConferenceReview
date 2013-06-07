package gui;

/**
 * RevisedAuthorView
 * A new AuthorView based on the new PGChairView
 * Has edit buttons acting on each paper in the table.
 * 
 * @author yongyuwang
 * Based on PGChairView made by Roshun Jones
 */

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import service.PaperService;

import model.Author;
import model.Paper;
import model.User;

import controller.AuthorViewController;
import controller.Controller;

public class RevisedAuthorView extends JPanel {
  private static final long serialVersionUID = 1L;
  private List<Paper> model;
  private Controller controller;
  private JTable table;
  private JScrollPane scrollPane;
  private String[] columns = {"Title", "Category", "Acceptance Status"};
  private Author author;
  private TableModel tableModel;
  private List<Paper> data;
  private JButton ViewEditButton, DeleteSubmission, AddSubmissionButton;
  private MainView parent;

  public RevisedAuthorView(User aUser) {
    author = new Author(aUser);
    model = author.viewPapers();
    controller = new AuthorViewController(author);
    
    // Configure view/edit button
    ViewEditButton = new JButton("View/Edit Details");
    ViewEditButton.setEnabled(false);
    ViewEditButton.setActionCommand("view_edit");
    ViewEditButton.addActionListener(controller);
    
    // Configure Delete Submission button
    DeleteSubmission = new JButton("Delete Submission");
    DeleteSubmission.setEnabled(false);
    DeleteSubmission.setActionCommand("delete_submission");
    DeleteSubmission.addActionListener(controller);
    
    // Configure the Add Submission button
    AddSubmissionButton = new JButton("Add Submission");
    // Disables new submission button if deadline is passed
    if(!author.canSubmitOrModify())
    {
    	AddSubmissionButton.setEnabled(false);
    }
   
    tableModel = new TableModel(model);
    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowSelectionAllowed(true);
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      @Override
      public void valueChanged(ListSelectionEvent event) {
        //Enable view/edit button when when selection is made
    	  ViewEditButton.setEnabled(true);
    	  DeleteSubmission.setEnabled(true);
      }
    });
    
    setLayout(new BorderLayout());
    scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.NORTH);
    
    JPanel southPanel = new JPanel();
    southPanel.add(ViewEditButton);
    southPanel.add(DeleteSubmission);
    southPanel.add(AddSubmissionButton);
    add(southPanel, BorderLayout.SOUTH);
    
    parent = (MainView) getTopLevelAncestor();
    setVisible(true);
  }
  
  public TableModel getTableModel() {
    return tableModel;
  }
  
  public Paper getSelectedRow() {   
    int selectedRow = table.getSelectedRow();
    
    if(selectedRow >= 0)
      return data.get(selectedRow);
    
    return null;
  }
  
  public Author getAuthor() {
    return author;
  }
  
  public MainView getMainView() {
    return parent;
  }
  
  //:) I know.....
  public void disableButton() {
	ViewEditButton.setEnabled(false);
	DeleteSubmission.setEnabled(false);
  }
  
  public class TableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    private TableModel(List<Paper> model) {
      super();
      
      if(model == null)
        data = new ArrayList<Paper>();
      else
        data = model;
    }
    
    @Override
    public int getColumnCount() {
      return columns.length;
    }

    @Override
    public int getRowCount() {
      return data.size();
    }

    @Override
    public String getColumnName(int columnIndex){
      return columns[columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex ) {
      Paper paper = data.get(rowIndex); 
      String result = null;
      switch(columnIndex) {
        case 0: result = paper.getTitle(); break;
        case 1: result = paper.getCategory(); break;
        case 2: result = paper.getAcceptanceStatus().toString(); break;
      }
      
      return result;
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
      return String.class;
    }
  }
}

