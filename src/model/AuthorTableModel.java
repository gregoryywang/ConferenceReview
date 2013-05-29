package model;

/**
 * AuthorTableModel.java
 * @author yongyuwang
 * @version 5-28-1632
 * This is the table model that will be used by the JTable.
 * in AuthorView.
 */

import javax.swing.table.AbstractTableModel;

import java.util.List;

/*
 * REMINDER: ROW/COLUMN COUNT STARTS AT 0!
 */

public class AuthorTableModel extends AbstractTableModel  {
	
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	// hard coded column names in table
	private String[] columnNames = { "Title", "Catagory", "Status", "Edit" };
	
	// data to be extracted from papers in the database
	private Object[][] data;
	
	// DEBUG: hard coded test data table
	// private Object[][] data = {{ "Paper 1 Name", "Paper 1 Status", "Category 1", "Edit 1" }, 
	//							  { "Paper 2 Name", "Paper 2 Status", "Category 2", "Edit 2" }};
	// DEBUG: uncomment line below to test
	// List<Paper> testList;
	
	private Author user;
	
	public AuthorTableModel(Author the_user) {
		super();
		this.user = the_user;
		
		// DEBUG: uncommment line below to test
		// test();
		
		getData();
	}
	
	/** DEBUG
	public void test() {
		testList = new ArrayList();
		Paper test_paper = new Paper();
		User test_user = new User();
		test_user.setID(1);
		test_paper.setAuthor(test_user);
		test_paper.setTitle("Testing Paper Stuff.");
		test_paper.setCategory("Software");
		test_paper.setKeywords("test, greatness");
		test_paper.setAbstract("Wow, I can write an abstract.");
		testList.add(test_paper);
	}
	*/
	
	public void getData() {
		
		// DEBUG: uncomment line below to test
		// List <Paper> paperList = testList;
		
		// Retrieves an collection of papers related to this user from the database.
		List <Paper> paperList = user.viewPapers();
		
		// constructs two dimensional array based on number of papers
		data = new Object[paperList.size()][columnNames.length];
		
		/*
		 * Each column must match predefined labels in columnNames.
		 * For the author table, the last column is always the edit button.
		 */
		for (int i = 0; i < paperList.size(); i++) {
			Paper current = paperList.get(i);
			for (int row = 0; row < paperList.size(); row++) {
				data[row][0] = current.getTitle();
				data[row][1] = current.getCategory();
				data[row][2] = current.getStatus();
				data[row][3] = "Edit Submission";
			}
		}
	}

	
	/*
     * Returns the number of columns in the table model. 
     */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	/*
     * Returns the column name for the column index. 
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /*
     * Returns the number of rows in the table model. 
     */
	@Override
	public int getRowCount() {
		return data.length;
	}

	/*
     * Returns the value of a table model at the specified 
     * row index and column index. 
     */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	 /*
     * The column that the edit button is located in needs to have editable cells!
     * Otherwise the edit button does not work.
     */
    public boolean isCellEditable(int row, int col) {
        if (col < 3) {
            return false;
        } else {
            return true;
        }
    }

}
