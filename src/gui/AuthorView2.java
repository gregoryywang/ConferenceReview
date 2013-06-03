
package gui;

/**
 * Author View.
 * 
 * @author Roshun Jones
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import controller.AuthorViewController;
import controller.Controller;

import service.ConferenceService;
import service.PaperService;

import model.Author;
import model.AuthorTableModel;
import model.Role;
import model.User;
import model.Paper;

public class AuthorView2 extends JPanel {

  /**
   * The default serial ID
   */
  private static final long serialVersionUID = 1L;
  private Author user;
  private TablePanel tablePanel;
  private AuthorViewController controller;
  
  public AuthorView2(final User the_user) {
    this.user = new Author(the_user);
    setLayout(new BorderLayout());
    controller = new AuthorViewController(user);

    String[][] properties = {
      {"java.lang.String", "Title", "Title", "false"},
      {"javax.swing.JComboBox", "Category", "Category", "true"},
      {"javax.swing.JComboBox", "Status", "Status", "true"},  
    };

    // Create table panel.
    tablePanel = new TablePanel<Paper>(properties, controller);
     
    // Populate model
    tablePanel.setModel(user.viewPapers());
    
    HashMap<Integer, Collection<Object>> refs = new HashMap<Integer, Collection<Object>>();
    refs.put(1, Arrays.asList(ConferenceService.getInstance().getCategories().toArray()));
    
    tablePanel.setReferenceValues(refs);
    
    add(tablePanel, BorderLayout.NORTH);

    JPanel panel = new JPanel();
    JButton AddSubmissionButton = new JButton("Add Submission");
    if(!user.canSubmitOrModify())
    {
    	AddSubmissionButton.setEnabled(false);
    }
    panel.add(AddSubmissionButton);
    AddSubmissionButton.addActionListener(controller);
    add(panel, BorderLayout.SOUTH);
  }

  /**
   * Returns view's model.
   * 
   * @return The view's model.
   */
  public TableModel getModel() {
    return tablePanel.getModel();
  }

  /**
   * Test code to launch a local panel.
   * 
   * @param args
   */
  public static void main(String[] args) {

    User testUser = new User();
    AuthorView2 test = new AuthorView2(testUser);
    JFrame testFrame = new JFrame();
    testFrame.getContentPane().add(test);
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.pack();
    testFrame.setVisible(true);
  }
}
