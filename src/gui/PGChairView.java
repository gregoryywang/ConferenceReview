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
import model.ProgramChair;
import model.Reviewer;
import model.SubProgramChair;
import model.User;
import service.PaperService;
import controller.Controller;
import controller.PGChairViewController;

public class PGChairView extends JPanel {
  private static final long serialVersionUID = 1L;
  private List<Paper> model;
  private Controller controller;
  private JTable table;
  private JScrollPane scrollPane;
  private String[] columns = {"Author","Title", "Sub-Program Chair", "Acceptance Status", 
    "Reviewer 1", "Reviewer 2", "Reviewer 3"};
  private ProgramChair PGChair;
  private TableModel tableModel;
  private List<Paper> data;
  private JButton btViewEdit;
  private MainView parent;

  public PGChairView(User aUser) {
    PGChair = new ProgramChair(aUser);
    model = PGChair.viewPapers();
    controller = new PGChairViewController(this, model);
    
    //Configure view/edit button
    btViewEdit = new JButton("View/Edit Details");
    btViewEdit.setEnabled(false);
    btViewEdit.setActionCommand("view_edit");
    btViewEdit.addActionListener(controller);
   
    tableModel = new TableModel(model);
    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowSelectionAllowed(true);
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      @Override
      public void valueChanged(ListSelectionEvent event) {
        //Enable view/edit button when when selection is made
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
  
  public ProgramChair getPGChair() {
    return PGChair;
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
      List<Reviewer> reviewers = PGChair.getReviewers(paper);
      
      switch(columnIndex) {
        case 0: result = paper.getAuthor().getFullName(); break;
        case 1: result = paper.getTitle(); break;
        case 2: 
          SubProgramChair user = PaperService.getInstance().getAssignedSubprogramChair(paper.getID());
          result = (user.getID() == 0) ? "Not Assigned" : user.getFullName(); break; 
        case 3: result = paper.getAcceptanceStatus().toString(); break;
        case 4:
          if(reviewers.size() > 0)
          {
            result = reviewers.get(0).getFullName();
          }
          else
          {
            result = "Not Assigned";
          }
          break; 
        case 5: 
          if(reviewers.size() > 1)
          {
            result = reviewers.get(1).getFullName();
          }
          else
          {
            result = "Not Assigned";
          }
          break; 
        case 6: 
          if(reviewers.size() > 2)
          {
            result = reviewers.get(2).getFullName();
          }
          else
          {
            result = "Not Assigned";
          }
          break; 

      }
      
      return result;
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
      return String.class;
    }
  }
}

