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
import model.User;
import service.PaperService;
import controller.Controller;
import controller.PGChairViewController;
import controller.ReviewerViewController;

public class ReviewerView extends JPanel {
  private static final long serialVersionUID = 1L;
  private List<Paper> model;
  private Controller controller;
  private JTable table;
  private JScrollPane scrollPane;
  private String[] columns = {"Author","Title", "Acceptance Status"};
  private Reviewer reviewer;
  private TableModel tableModel;
  private List<Paper> data;
  private JButton btViewEdit;
  private JButton btViewPaper;
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
    btViewEdit = new JButton("Write/Edit Review");
    btViewEdit.setEnabled(false);
    btViewEdit.setActionCommand("write_review");
    btViewEdit.addActionListener(controller);
   
    //Configure view/edit button
    btViewPaper = new JButton("View Paper");
    btViewPaper.setEnabled(false);
    btViewPaper.setActionCommand("view_paper");
    btViewPaper.addActionListener(controller);

    tableModel = new TableModel(model);
    table = new JTable(tableModel);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowSelectionAllowed(true);
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      @Override
      public void valueChanged(ListSelectionEvent event) {
        //Enable view/edit button when selection is made
        btViewEdit.setEnabled(true);
        //Enable view paper button when selection is made
        btViewPaper.setEnabled(true);
      }
    });
    
    setLayout(new BorderLayout());
    scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.NORTH);
    JPanel southPanel = new JPanel();
    southPanel.add(btViewEdit);
    southPanel.add(btViewPaper);
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
        case 3: result = paper.getAcceptanceStatus().displayName(); break;
      }
      
      return result;
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
      return String.class;
    }
  }
}

//package gui;
//
//import gui.TablePanel.TableModel;
//
//import java.awt.BorderLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import model.Paper;
//import model.Reviewer;
//import model.User;
//import service.ConferenceService;
//import service.UserService;
//import controller.Controller;
//
//public class ReviewerView extends JPanel
//{
//
//	/**
//	 * The default serial ID
//	 */
//	private static final long serialVersionUID = 1L;
//	private Reviewer my_user;
//	private TablePanel tablePanel;
//	private ReviewerController controller;
//
//	public ReviewerView(final User the_user) 
//	{
//		my_user = new Reviewer(the_user);
//		setLayout(new BorderLayout(0, 0));
//		controller = new ReviewerController(my_user);
//
//		String[][] properties = {
//				{"javax.swing.JButton", "Title", "Title", "true"},
//			    {"java.lang.String", "Author", "Author", "false"},
//				{"javax.swing.JButton", "Review", "Review", "true"},
//		};
//		// Create table panel.
//		tablePanel = new TablePanel<Paper>(properties, controller);
//
//		// Populate model
//		tablePanel.setModel(my_user.viewPapers());
//
//		add(tablePanel, BorderLayout.NORTH);
//
//	}
//
//	/**
//	 * Returns view's model.
//	 * 
//	 * @return The view's model.
//	 */
//	public TableModel getModel() {
//		return tablePanel.getModel();
//	}
//
//	/**
//	 * Test code to launch a local panel.
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
//	}
//
//	private class ReviewerController implements Controller, ActionListener
//	{
//
//		public ReviewerController(Reviewer the_reviewer) {
//			
//		}
//
//		@Override
//		public void update(Object aObject) {
//
//		}
//
//		@Override
//		public void setModel(Object aObject) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//
//		}
//	}
//	
//	
//}


