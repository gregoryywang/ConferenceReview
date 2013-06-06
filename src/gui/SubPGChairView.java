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

public class SubPGChairView extends JPanel
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private String[] columns = {"Author","Title", "Status", "Reviewer", "Reviewer", "Reviewer"};
	private List<Paper> model;
	private Controller controller;
	private JTable table;
	private JScrollPane scrollPane;
	private SubProgramChair my_SPChair;
	private JButton btViewEdit;
	private TableModel tableModel;
	private List<Paper> data;
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

	public TableModel getTableModel() {
		return tableModel;
	}

	public Paper getSelectedRow() {   
		int selectedRow = table.getSelectedRow();

		if(selectedRow >= 0)
			return data.get(selectedRow);

		return null;
	}

	public SubProgramChair getSPChair() {
		return my_SPChair;
	}

	public MainView getMainView() {
		return parent;
	}

	//:) I know.....
	public void disableButton() {
		btViewEdit.setEnabled(false);
	}

	public class TableModel extends AbstractTableModel 
	{
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

		@Override
		public Class getColumnClass(int columnIndex) {
			return String.class;
		}
	}
}



