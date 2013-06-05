package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import model.Paper;
import model.Review;
import model.Reviewer;
import model.Role;
import model.Status;
import model.User;
import service.PaperService;
import service.UserService;
import controller.Controller;

public class ReviewerDialog extends JDialog {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

private Reviewer my_reviewer;
 
 //Form components
 private JLabel my_label_title;
 private JLabel my_label_author;
 private JLabel my_label_reviewer;
 private JLabel my_label_acceptance;
 private JLabel my_text_author;
 private JComboBox my_combobox_reviewer;
 private JComboBox my_combobox_acceptance;
 private JButton my_command_cancel;
 private JButton my_command_update;
 
 public ReviewerDialog(final MainView the_parent,
                      final Controller the_controller,
                      final Reviewer the_reviewer,
                      final Paper the_paper) {
   setLayout(null);
   setTitle("Reviewer Command Center");
   setModal(true);
   setSize(400, 240);
   setResizable(false);
   
   my_label_title = new JLabel(the_paper.getTitle());
   my_label_title.setBounds(150,5,200,20);
   
   my_label_author = new JLabel("Author:");
   my_label_author.setBounds(40,50,50,20);
   my_text_author = new JLabel(the_paper.getAuthor().getFullName());
   my_text_author.setBounds(200,50,165,20);
   
   my_label_reviewer = new JLabel("Reviewer: ");
   my_label_reviewer.setBounds(40,80,200,20);
   List<User> dropValues = UserService.getInstance().getAllUsers(the_paper, the_reviewer.getConference().getID(), Role.REVIEWER); 
   my_combobox_reviewer = new JComboBox(new DefaultComboBoxModel(dropValues.toArray()));
   my_combobox_reviewer.setBounds(200,80,165,20);
   
   // I'm not sure what was going on here...
   List<Review> review_list = PaperService.getInstance().getReviews(the_paper.getID());
   Reviewer reviewer = null;
   for (Review review : review_list)
   {
	   if (review.getReviewer().getID() == the_reviewer.getID())
	   {
		   reviewer = (Reviewer) review.getReviewer();
	   }
   }
   my_combobox_reviewer.setEnabled(reviewer.getID() == 0);
   
   my_label_acceptance = new JLabel("Acceptance Descision:");
   my_label_acceptance.setBounds(40,110,200,20);
   Status[] values = {Status.UNDECIDED, Status.ACCEPT, Status.DECLINE};
   my_combobox_acceptance = new JComboBox(new DefaultComboBoxModel(values));
   my_combobox_acceptance.setBounds(200,110,165,20);
   my_combobox_acceptance.setEnabled(the_reviewer.canAddReview());
   if(the_paper != null && the_paper.getAcceptanceStatus() != null)
	   my_combobox_acceptance.setSelectedItem(the_paper.getAcceptanceStatus());
   
   
   my_command_update = new JButton("Update");
   my_command_update.setBounds(85, 165, 100, 20);
   my_command_update.addActionListener(the_controller);
   
   my_command_cancel = new JButton("Cancel");
   my_command_cancel.setBounds(205, 165, 100, 20);
   my_command_cancel.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(final ActionEvent the_event){
       dispose();
     }
   });
   
   add(my_label_title);
   add(my_label_author);
   add(my_label_reviewer);
   add(my_label_acceptance);
   add(my_text_author);
   add(my_combobox_reviewer);
   add(my_combobox_acceptance);
   add(my_command_update);
   add(my_command_cancel);
   
   //Center dialog
   int parentWidth = MainView.WIDTH;
   int parentHeight = MainView.HEIGHT;
   int locX = (parentWidth / 2) - (getWidth() / 2);
   int locY = (parentHeight / 2) - (getHeight() / 2);
   setLocation(locX, locY);
 }
 
 public Object getDecision() {
   return my_combobox_acceptance.getSelectedItem();
 }
 
 public Object getReviewer() {
   return my_combobox_reviewer.getSelectedItem();
 } 
}
