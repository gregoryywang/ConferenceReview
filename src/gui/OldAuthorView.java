
package gui;

/**
 * Author View.
 * 
 * @author Roshun Jones
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import controller.AuthorViewController;
import service.ConferenceService;
import model.Author;
import model.User;
import model.Paper;

public class OldAuthorView extends JPanel {

  /**
   * The default serial ID
   */
  private static final long serialVersionUID = 1L;
  private Author user;
  private TablePanel tablePanel;
  private AuthorViewController controller;
  
  public OldAuthorView(final User the_user) {
    this.user = new Author(the_user);
    setLayout(new BorderLayout());
    controller = new AuthorViewController(user);

    String[][] properties = {
      {"java.lang.String", "Title", "Title", "false"},
      {"java.lang.String", "Category", "Category", "false"},
      {"java.lang.String", "AcceptanceStatus", "AcceptanceStatus", "false"}
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
    
    /*
    JButton viewPaper = new JButton("View Submission");
    viewPaper.addActionListener(controller);
    panel.add(viewPaper);
    */
    
    JButton editPaper = new JButton("Edit Submission");
    if(!user.canSubmitOrModify())
    {
    	editPaper.setEnabled(false);
    }
    editPaper.addActionListener(controller);
    panel.add(editPaper);

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
    OldAuthorView test = new OldAuthorView(testUser);
    JFrame testFrame = new JFrame();
    testFrame.getContentPane().add(test);
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.pack();
    testFrame.setVisible(true);
  }
}
