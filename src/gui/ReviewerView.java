package gui;

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

import model.Paper;
import model.Reviewer;
import model.User;
import controller.Controller;
import controller.ReviewerViewController;

/**
 * Class that will display the ReviewerView panel
 * in the GUI.
 * 
 * @author Levon K
 * @version Spring 2013
 */
public class ReviewerView extends JPanel 
{
  private static final long serialVersionUID = 1L;
  private List<Paper> model;
  private Controller controller;
  private JTable table;
  private JScrollPane scrollPane;
  private String[] columns = {"Author","Title", "Review Complete"};
  private Reviewer reviewer;
  private TableModel tableModel;
  private List<Paper> data;
  private JButton btViewEdit;
  private MainView parent;

  /**
   * Create a view for the Reviewer
   * @param aUser the reviewer.
   */
  public ReviewerView(User aUser) {
    reviewer = new Reviewer(aUser);
    model = reviewer.viewPapers();
    controller = new ReviewerViewController(this, model);
    
    //Configure view/edit button
    btViewEdit = new JButton("Write/View Review");
    btViewEdit.setEnabled(false);
    btViewEdit.setActionCommand("write_review");
    btViewEdit.addActionListener(controller);
   
    tableModel = new TableModel(model);
    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowSelectionAllowed(true);
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      @Override
      public void valueChanged(ListSelectionEvent event) {
        //Enable view/edit button when selection is made
        btViewEdit.setEnabled(true);
      }
    });
    
    setLayout(new BorderLayout());
    scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.NORTH);
    JPanel southPanel = new JPanel();
    southPanel.add(btViewEdit);
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
  
  public Reviewer getReviewer() {
    return reviewer;
  }
  
  public MainView getMainView() {
    return parent;
  }
  
  //:) I know.....
  public void disableButton() {
   btViewEdit.setEnabled(false);
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
        case 0: result = paper.getAuthor().getFullName(); break;
        case 1: result = paper.getTitle(); break;
        case 2: 
        	{
        		if (reviewer.reviewComplete(paper))
        		{
        			result = "Complete";
        		}
        		else
        		{
        			result = "Review Needed";
        		}
        		//result = paper.getStatus().toString(); 
        		break;
        	}
      }
      
      return result;
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
      return String.class;
    }
  }
}