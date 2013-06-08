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
import model.SubProgramChair;
import model.User;
import controller.Controller;
import controller.SubPGChairController;

/**
 * View that a SubProgram Chair should see
 * @author Roshun (template)
 * @author Danielle
 * @version 2013 Spring
 */
public class SubPGChairView extends JPanel
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The headings for the JTable
	 */
	private String[] columns = {"Author","Title", "Status", "Reviewer", "Reviewer", "Reviewer"};
	/**
	 * The list of papers to display.
	 */
	private List<Paper> model;
	
	/**
	 * The controller for actions associated with popups which may change the model.
	 */
	private Controller controller;
	
	/**
	 * The table.
	 */
	private JTable table;
	
	/**
	 * The scrollpane to display the table on
	 */
	private JScrollPane scrollPane;
	
	/**
	 * My subprogram chair user.
	 */
	private SubProgramChair my_SPChair;
	
	/**
	 * The button to launch the command center.
	 */
	private JButton btViewEdit;
	
	/**
	 * The model for the J Table
	 */
	private TableModel tableModel;
	
	/**
	 * The list of papers.
	 */
	private List<Paper> data;
	
	/**
	 * The parent of this panel.
	 */
	private MainView parent;
	
	/**
	 * Constructs a SubPGChairView from a specific
	 * SubProgramChair User.
	 */
	public SubPGChairView(final User the_user)
	{
		super();
		my_SPChair = new SubProgramChair(the_user);
		model = my_SPChair.getPapers();
		controller = new SubPGChairController(this, model);

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

	/**
	 * Get the table model for this view.
	 * @return the table model for this view
	 */
	public TableModel getTableModel() {
		return tableModel;
	}

	/**
	 * Get the selected row of the table
	 * @return the paper at the selected row of the Jtable
	 */
	public Paper getSelectedRow() {   
		int selectedRow = table.getSelectedRow();

		if(selectedRow >= 0)
			return data.get(selectedRow);

		return null;
	}

	/**
	 * Get the subprogram chair user
	 * @return the user
	 */
	public SubProgramChair getSPChair() {
		return my_SPChair;
	}

	/**
	 * Get the parent of this pane
	 * @return the parent of this pane
	 */
	public MainView getMainView() {
		return parent;
	}

	//:) I know.....
	public void disableButton() {
		btViewEdit.setEnabled(false);
	}

	/**
	 * The Table Model for displaying the papers
	 * @author Roshun (template)
	 * @author Danielle
	 */
	public class TableModel extends AbstractTableModel 
	{
		/**
		 * The id
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 * @param model  the papers to display
		 */
		private TableModel(List<Paper> model) {
			super();

			if(model == null)
				data = new ArrayList<Paper>();
			else
				data = model;
		}

		/**
		 * @return the column count for this table.
		 */
		@Override
		public int getColumnCount() {
			return columns.length;
		}

		/**
		 * @return the row count for this table.
		 */
		@Override
		public int getRowCount() {
			return data.size();
		}

		/**
		 * @return the heading name for this column
		 */
		@Override
		public String getColumnName(int columnIndex){
			return columns[columnIndex];
		}

		/**
		 * @return the paper at the row index.
		 */
		@Override //{"Author","Title", "Status", "Reviewer", "Reviewer", "Reviewer"}
		public Object getValueAt(int rowIndex, int columnIndex ) {
			Paper paper = data.get(rowIndex); 
			String result = "";
			List<Reviewer> reviewers = my_SPChair.getReviewers(paper);
			switch(columnIndex) {
			case 0: result = paper.getAuthor().getFullName(); break;
			case 1: result = paper.getTitle(); break;
			case 2: result = paper.getStatus().displayName(); break;
			case 3:
				if(reviewers.size() > 0)
				{
					result = reviewers.get(0).getFullName();
				}
				else
				{
					result = "Not Assigned";
				}
				break; 
			case 4: 
				if(reviewers.size() > 1)
				{
					result = reviewers.get(1).getFullName();
				}
				else
				{
					result = "Not Assigned";
				}
				break; 
			case 5: 
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

		/**
		 * Get the class of the column.  They are all strings (nothing else!)
		 */
		@Override
		public Class getColumnClass(int columnIndex) {
			return String.class;
		}
	}
}



