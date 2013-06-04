package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import service.PaperService;
import service.UserService;

import controller.Controller;

import model.Paper;
import model.ProgramChair;
import model.Status;
import model.SubProgramChair;
import model.User;

public class PGChairDialog extends JDialog {
 private ProgramChair pgChair;
 
 //Form components
 private JLabel lblTitle;
 private JLabel lblAuthor;
 private JLabel lblSubChair;
 private JLabel lblAcceptance;
 private JTextField txtAuthor;
 private JComboBox cmbSubChair;
 private JComboBox cmbAcceptance;
 private JButton cmdCancel;
 private JButton cmdUpdate;
 
 public PGChairDialog(MainView aParent,
                      Controller aController,
                      ProgramChair pgChair,
                      Paper aPaper) {
   setLayout(null);
   setTitle("Program Chair Command Center");
   setModal(true);
   setSize(400, 240);
   setResizable(false);
   
   lblTitle = new JLabel("Title");
   lblTitle.setBounds(200,5,200,20);
   
   lblAuthor = new JLabel("Author:");
   lblAuthor.setBounds(40,50,50,20);
   txtAuthor = new JTextField("SomeGuy");
   txtAuthor.setBounds(220,50,135,20);
   txtAuthor.setEnabled(false);
   
   lblSubChair = new JLabel("Sub-Program Chair:");
   lblSubChair.setBounds(40,80,200,20);
   List<User> dropValues = UserService.getInstance().getAllUsers(); 
   cmbSubChair = new JComboBox(new DefaultComboBoxModel(dropValues.toArray()));
   cmbSubChair.setBounds(220,80,135,20);
   SubProgramChair subProgram = PaperService.getInstance().getAssignedSubprogramChair(aPaper.getID());
   cmbSubChair.setEnabled(subProgram.getID() == 0);
   
   lblAcceptance = new JLabel("Acceptance Descision:");
   lblAcceptance.setBounds(40,110,200,20);
   Status[] values = {Status.UNDECIDED, Status.ACCEPT, Status.DECLINE};
   cmbAcceptance = new JComboBox(new DefaultComboBoxModel(values));
   cmbAcceptance.setBounds(220,110,135,20);
   cmbAcceptance.setEnabled(pgChair.canAssignDecision());
   if(aPaper != null && aPaper.getAcceptanceStatus() != null)
     cmbAcceptance.setSelectedItem(aPaper.getAcceptanceStatus());
   
   
   cmdUpdate = new JButton("Update");
   cmdUpdate.setBounds(85, 165, 100, 20);
   cmdUpdate.addActionListener(aController);
   
   cmdCancel = new JButton("Cancel");
   cmdCancel.setBounds(205, 165, 100, 20);
   cmdCancel.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent event){
       dispose();
     }
   });
   
   add(lblTitle);
   add(lblAuthor);
   add(lblSubChair);
   add(lblAcceptance);
   add(txtAuthor);
   add(cmbSubChair);
   add(cmbAcceptance);
   add(cmdUpdate);
   add(cmdCancel);
   
   //Center dialog
   int parentWidth = MainView.WIDTH;
   int parentHeight = MainView.HEIGHT;
   int locX = (parentWidth / 2) - (getWidth() / 2);
   int locY = (parentHeight / 2) - (getHeight() / 2);
   setLocation(locX, locY);
 }
 
 public Object getDecision() {
   return cmbAcceptance.getSelectedItem();
 }
 
 public Object getSubProgramChair() {
   return cmbSubChair.getSelectedItem();
 } 
}
