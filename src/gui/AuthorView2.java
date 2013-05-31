
package gui;

/**
 * Author View.
 * 
 * @author Roshun Jones
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import service.PaperService;

import model.Author;
import model.AuthorTableModel;
import model.Role;
import model.User;
import model.Paper;

public class AuthorView2 extends JPanel implements ActionListener, Observer {

  /**
   * The default serial ID
   */
  private static final long serialVersionUID = 1L;
  private Author user;
  private TablePanel tablePanel;
  
  public AuthorView2(final User the_user) {
    this.user = new Author(the_user);
    user.addObserver(this);
    setLayout(new BorderLayout(0, 0));

    String[][] properties = {
      {"java.util.String", "Title", "Title", "true"},
      {"java.util.String", "Category", "Category", "true"},
      {"java.util.String", "Status", "Status", "true"},
      {"java.util.String", "Edit", "Edit", "true"}
    };

    // Create table panel.
    tablePanel = new TablePanel(properties);

    // Populate model
    List<Paper> papers =
        PaperService.getInstance().getAssignedPapers(user.getID(),
                                                     user.getConference().getID(),
                                                     Role.AUTHOR);

    tablePanel.setModel(Arrays.asList(papers.toArray()), Paper.class);
    add(tablePanel, BorderLayout.NORTH);

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

  @Override
  public void update(Observable arg0, Object arg1) {
 // Populate model
    List<Paper> papers =
        PaperService.getInstance().getAssignedPapers(user.getID(),
                                                     user.getConference().getID(),
                                                     Role.AUTHOR);

    tablePanel.setModel(Arrays.asList(papers.toArray()), Paper.class);
  }
}
