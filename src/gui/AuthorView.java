package gui;

/**
 * AuthorView.java
 * @author yongyuwang
 * @version 5-25-1424
 * Prototype table to display author's papers
 */

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AuthorView extends JFrame {

  public AuthorView() {
    super("Author View");

    DefaultTableModel AuthorPaperTable = new DefaultTableModel();
    AuthorPaperTable.setDataVector(new Object[][] {{ "Paper 1 Name", "Paper 1 Status", "Edit" }, { "Paper 2 Name", "Paper 2 Status", "Edit" }}, 
    		new Object[] { "Paper Name", "Current Status", "Edit Submission" });
    
    JTable table = new JTable(AuthorPaperTable) {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) {                
                return false;               
        };
    };

    table.getColumn("Edit Submission").setCellRenderer(new ButtonRenderer());
    table.getColumn("Edit Submission").setCellEditor(new ButtonEditor(new JCheckBox()));
   
    JScrollPane scrollPanel = new JScrollPane(table);
    getContentPane().add(scrollPanel);
    setSize(500, 200);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    AuthorView frame = new AuthorView();
  }
}

/**
 * Many slightly different implementations exist on how to add buttons to a table.
 * The code below represents one example of how to do it.
 * As of now no other implementation in my local test classes works. 
 * As my understanding of this changes so may the code below.
 */

class ButtonRenderer extends JButton implements TableCellRenderer {

  public ButtonRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    setText((value == null) ? "" : value.toString());
    return this;
  }
}

class ButtonEditor extends DefaultCellEditor {
  protected JButton button;
  private String label;
  private boolean isPushed;

  public ButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
    label = (value == null) ? "" : value.toString();
    button.setText(label);
    isPushed = true;
    return button;
  }

  public Object getCellEditorValue() {
    if (isPushed) {
      JOptionPane.showMessageDialog(button, label + " was pressed!");
    }
    isPushed = false;
    return new String(label);
  }

  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}