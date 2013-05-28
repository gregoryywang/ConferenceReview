package model;

/**
 * AuthorTableModel.java
 * @author yongyuwang
 * @version 5-28-1632
 * This is the table model that will be used by the JTable
 * in AuthorView.
 */
import javax.swing.table.AbstractTableModel;

/*
 * REMINDER: ROW/COLUMN COUNT STARTS AT 0!
 */

public class AuthorTableModel extends AbstractTableModel  {
	
	// hard coded column names in table
	private String[] columnNames = { "Title", "Catagory", "Status", "Edit" };
	
	// data to be extracted from papers in the database
	// private Object[][] data;
	
	// DEBUG: hard coded test data table
	private Object[][] data = {{ "Paper 1 Name", "Paper 1 Status", "Catagory 1", "Edit 1" }, { "Paper 2 Name", "Paper 2 Status", "catagory 2", "Edit 2" }};
	
	public AuthorTableModel(User user) {
		super();
	}
	
	public void getData() {
		
	}

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

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	 /*
     * The column that the edit button is located needs to have editable cells!
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
