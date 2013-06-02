package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import model.User;

import service.UserService;

/**
 * Abstract class that wraps JTable
 * 
 * @author Roshun Jones
 * @version 1.0
 */
public class TablePanel extends JPanel implements ActionListener { 
  protected static int CLASS_TYPE = 0;
  protected static int METHOD_NAME = 1;
  protected static int COLUMN_DISPLAY = 2;
  protected static int READ_ONLY = 3;
  
  private static final long serialVersionUID = 1L;
  private TableModel model = null;
  private JTable table = null;
  private JScrollPane scrollPane;
  
  private HashMap<Integer, Collection<Object>> referenceValues = new HashMap<Integer, Collection<Object>>();
  private HashMap<Integer, Component> componentMap = new HashMap<Integer, Component>();
  
  public TablePanel(final String[][] aProperties) {
    model = new TableModel(aProperties);
    table = new JTable( model );
    
    //Build custom components
    buildComponents();
    
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
   * Sets reference values or this panel.
   */
  public void setReferenceValues(final HashMap<Integer, Collection<Object>> aRefs) {
    referenceValues = aRefs;
    for( int i = 0; i < model.getColumnCount(); i++ ) {
      Object component = componentMap.get(i);
      if( component instanceof JComboBox) {
        Collection<Object> refs = referenceValues.get(i); 
        if( refs != null ) {
          ((JComboBox) component).setModel(new DefaultComboBoxModel(refs.toArray()));
        }
      }
    }
  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    
  }
  
  /**
   * Builds custom components in table cells.
   */
  private void buildComponents() {
    for( int i = 0; i < model.getColumnCount(); i++ ) {
      //Build combo box and register component
      if(model.getColumnClass(i).equals(JComboBox.class)) {
        TableColumn column = table.getColumnModel().getColumn(i);       
        JComboBox box = new JComboBox(new Object[0]);
        column.setCellEditor(new DefaultCellEditor(box));
        componentMap.put(i, box);
      }
    }
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
    
    /**
     * Sets the value of a cell, also updates the model.
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
      try {
        String method = "set" + properties[columnIndex][METHOD_NAME];
        Object obj = data.get(rowIndex);
        
        Method[] methods = obj.getClass().getMethods();
        for(Method m : methods) {
          if( m.getName().equals(method) ) {
            m.invoke(obj, value);
            break;
          }
        }
      }catch( Exception e ) {
        System.out.println(e.getMessage());
      }
    }
    
    /**
     * Returns the columns class.
     */
    @Override
    public Class getColumnClass(int columnIndex) {
      Class result = null;
      try {
        result = Class.forName(properties[columnIndex][CLASS_TYPE]); 
      }catch( Exception e ) {
        System.out.println(e.getMessage());
      }
      
      return result;
    }
    
    /*
     * The column that the edit button is located in needs to have editable
     * cells! Otherwise the edit button does not work.
     */
    public boolean isCellEditable(int row, int col) {
      return true;
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

  
  public static void main(String[] args) {
                                // Column data type, access method name, display name, column read only
     String[][] properties = {  
                               {"java.lang.String","PaperId","Paper Id","true"},
                               {"java.lang.String","AuthorId","Author Id","true"},
                               {"java.lang.String","SubProgramChair","Subprogram Chair","true"},
                               {"javax.swing.JComboBox","Status","Status","true"},
                               {"javax.swing.JComboBox","S","S","true"}
                             };
     
     TablePanel panel = new TablePanel(properties);
     Object[] users = UserService.getInstance().getAllUsers().toArray();
     panel.setModel(Arrays.asList(users), User.class);
     List<Object> testList = new ArrayList<Object>();
     testList.add("Test1");
     testList.add("Test2");
     HashMap<Integer, Collection<Object>> refs = new HashMap<Integer, Collection<Object>>();
     refs.put(3, testList);
     panel.setReferenceValues(refs);
     
     JFrame frame = new JFrame();
     frame.getContentPane().add(panel);
     frame.setVisible(true);
     frame.pack();
  }
}
