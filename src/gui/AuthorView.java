package gui;

/**
 * AuthorView.java
 * @author yongyuwang
 * @version 5-28-1952
 * Prototype table to display author's papers.
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableCellRenderer;

import model.Author;
import model.AuthorTableModel;
import model.User;

public class AuthorView extends JPanel implements ActionListener, Observer {
	
	/**
	 * The default serial ID
	 */
	private static final long serialVersionUID = 1L;
  
  private Author user;
  	
  /**
   * Table Model.
  */
  private AuthorTableModel model;
  
	public AuthorView(final User the_user) {
		this.user = new Author(the_user);
		
		user.addObserver(this);
		
		setLayout(new BorderLayout(0, 0));
	  
		model = new AuthorTableModel(user);
		
		JTable table = new JTable(model);

		table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
		table.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox()));
   
		JScrollPane scrollPanel = new JScrollPane(table);
		add(scrollPanel, BorderLayout.NORTH);
		
        JPanel panel = new JPanel();
        JButton AddSubmissionButton = new JButton("Add Submission");
        panel.add(AddSubmissionButton);
        AddSubmissionButton.addActionListener(this);
		add(panel, BorderLayout.SOUTH);
	}
	
	/**
	 * Launches the new paper submission form when pressed.
	 */
    public void actionPerformed(ActionEvent event) {
		new PaperSubmissionForm(user).setVisible(true);
    }

  
    /**
     * Returns view's model.
     * @return The view's model.
     */
    public AuthorTableModel getModel() {
      return model;
    }
    
    /**
     * Test code to launch a local panel.
     * @param args
     */
 	public static void main(String[] args) {
	  
 		User testUser = new User();
 		AuthorView test = new AuthorView(testUser);
 		JFrame testFrame = new JFrame();
 		testFrame.getContentPane().add(test);
 		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		testFrame.pack();
 		testFrame.setVisible(true);	
 	}

    @Override
    public void update(Observable arg0, Object arg1) {
      model.getData();
      model.fireTableChanged(new TableModelEvent(model));    
    }
}

/**
 * Many slightly different implementations exist on how to add buttons to a table.
 * The code below represents one example of how to do it.
 * As of now no other implementation in my local test classes works. 
 * As my understanding of this changes so may the code below.
 */

class ButtonRenderer extends JButton implements TableCellRenderer {

	/**
	 * The default serial ID
	 */
	private static final long serialVersionUID = 1L;

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
	/**
	 * The default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
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
		
		// DEBUG
		System.out.println("reached DEBUG POINT 1");
		System.out.println("button is in row " + row + " column " + column);
		
		return button;
	}

	public Object getCellEditorValue() {
		if (isPushed) {
			JOptionPane.showMessageDialog(button, label + " was pressed!");
		}
		
		// DEBUG
		System.out.println("reached DEBUG POINT 2\n");

		isPushed = false;
		return new String(label);
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}