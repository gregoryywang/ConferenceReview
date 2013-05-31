package gui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Abstract class that wraps JTable
 * 
 * @author Roshun Jones
 * @version 1.0
 */
public class TablePanel extends JPanel { 
  protected static int CLASS_TYPE = 0;
  protected static int METHOD_NAME = 1;
  protected static int COLUMN_DISPLAY = 2;
  protected static int READ_ONLY = 3;
  
  private static final long serialVersionUID = 1L;
  private TableModel model = null;
  private JTable table = null;
  private JScrollPane scrollPane;
   
  public TablePanel(final String[][] aProperties) {
    model = new TableModel(aProperties);
    table = new JTable( model );
    scrollPane = new JScrollPane(table);
    add(scrollPane);
    setVisible(true);
  }
  
  /**
   * Returns table model.
   * @return table model.
   */
  public TableModel getModel() {
    return model;
  }
  
  /**
   * Sets panel data.
   */
  public void setModel(List<Object> aList, Class aModelClass) {   
    model.setModel(aList, aModelClass);
    model.fireTableDataChanged();
  }
  
  /**
   * Table Model used by this table.
   * @author Roshun Jones
   * @version 1.0
   */
  private class TableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private List<Object> data = new ArrayList<Object>();
    private String[] columnNames;
    private String[][] properties;
    private Class<Object> modelClass;
    
    private TableModel(final String[][] aProperties){
      super();
      properties = aProperties;
      setColumns();
    }
    
    /**
     * Sets model data.
     */
    private void setModel(List<Object> aList, Class<Object> aModelClass) {
      data = aList;
      modelClass = aModelClass;
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
      return data.size();
    }

    /*
     * Returns the value of a table model at the specified row index and column
     * index.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Object value = null;
      try {
        Object obj = data.get(rowIndex);
        String method = "get" + properties[columnIndex][METHOD_NAME];
        Method m = data.get(rowIndex).getClass().getMethod(method, null);
        value = m.invoke(obj, new Object[0]);
      }catch( Exception e ){
        System.out.println(e.getMessage());
      }
        
      return value;
    }

    /*
     * The column that the edit button is located in needs to have editable
     * cells! Otherwise the edit button does not work.
     */
    public boolean isCellEditable(int row, int col) {
      if (col < 3) {
        return false;
      } else {
        return true;
      }
    }
    
    /**
     * Sets column data.
     */
    private void setColumns() {
      List<String> columns = new ArrayList<String>();
      
      for(int i = 0; i < properties.length; i++) {
          columns.add(properties[i][COLUMN_DISPLAY]);
      }
      
      columnNames = columns.toArray(new String[1]);
    }
  }
  
  /*
  public static void main(String[] args) {
                                 Column data type, access method name, display name, column read only
     String[][] properties = {  
                               {"java.util.String","PaperId","Paper Id","true"},
                               {"java.util.String","AuthorId","Author Id","true"},
                               {"java.util.String","SubProgramChair","Subprogram Chair","true"},
                               {"java.util.String","Status","Status","true"}
                             };
     
     AbstractTablePanel panel = new AbstractTablePanel(properties);
     Object[] users = UserService.getInstance().getAllUsers().toArray();
     panel.setModel(Arrays.asList(users), User.class);
     JFrame frame = new JFrame();
     frame.getContentPane().add(panel);
     frame.setVisible(true);
     frame.pack();
  }*/
}
