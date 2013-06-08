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
import model.ProgramChair;
import model.Role;
import model.Status;
import model.SubProgramChair;
import model.User;
import service.PaperService;
import service.UserService;
import controller.Controller;

public class PGChairDialog extends JDialog {
	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ProgramChair pgChair;

	//Form components
	private JLabel lblTitle;
	private JLabel lblTitle2;
	private JLabel lblAuthor;
	private JLabel lblSubChair;
	private JLabel lblReviews;
	private JLabel lblAcceptance;
	private JLabel txtAuthor;
	private JComboBox cmbSubChair;
	private JComboBox cmbReviews;
	private JLabel txtSubChair;
	private JComboBox cmbAcceptance;
	private JButton cmdViewReview;
	private JButton cmdCancel;
	private JButton cmdUpdate;

	public PGChairDialog(MainView aParent,
			Controller aController,
			ProgramChair pgChair,
			Paper aPaper) {
		setLayout(null);
		setTitle("Program Chair Command Center");
		//setModal(true);
		setSize(445, 260);
		setResizable(false);

		lblTitle = new JLabel("Title:");
		lblTitle.setBounds(50,20,70,20);
		lblTitle2 = new JLabel(aPaper.getTitle());
		lblTitle2.setBounds(210,20,185,20);

		lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(50,50,70,20);
		txtAuthor = new JLabel(aPaper.getAuthor().getFullName());
		txtAuthor.setBounds(210,50,185,20);

		lblSubChair = new JLabel("Sub-Program Chair:");
		lblSubChair.setBounds(50,80,220,20);
		List<User> dropValues = UserService.getInstance().getAllUsers(aPaper, pgChair.getConference().getID(), Role.SUB_PROGRAM_CHAIR); 
		cmbSubChair = new JComboBox(new DefaultComboBoxModel(dropValues.toArray()));
		cmbSubChair.setBounds(210,80,185,20);
		SubProgramChair subProgram = PaperService.getInstance().getAssignedSubprogramChair(aPaper.getID());
	
		if(subProgram.getID() != 0)
		{
			SubProgramChair[] dropdown = {subProgram};
			cmbSubChair.setModel(new DefaultComboBoxModel(dropdown));
		}
		
		lblReviews = new JLabel("View Reviews:");
		lblReviews.setBounds(50,110,220,20);
		Object[] reviewValues = PaperService.getInstance().getReviews(aPaper.getID()).toArray();
		if(reviewValues.length == 0) {
		  cmbReviews = new JComboBox(new Object[]{"No reviews available"});
		  cmbReviews.setEnabled(false);
		} else
		  cmbReviews = new JComboBox(new DefaultComboBoxModel(reviewValues));
		
		cmbReviews.setBounds(210,110,185,20);
		
		lblAcceptance = new JLabel("Acceptance Descision:");
		lblAcceptance.setBounds(50,140,220,20);
		Status[] values = {Status.UNDECIDED, Status.ACCEPT, Status.DECLINE};
		cmbAcceptance = new JComboBox(new DefaultComboBoxModel(values));
		cmbAcceptance.setBounds(210,140,185,20);
		cmbAcceptance.setEnabled(pgChair.canAssignDecision() && (reviewValues.length) >= 3);
		if(aPaper != null && aPaper.getAcceptanceStatus() != null)
			cmbAcceptance.setSelectedItem(aPaper.getAcceptanceStatus());
		
		cmdViewReview = new JButton("View Review");
		cmdViewReview.setBounds(170, 195, 100, 20); //from l, from t, width, ht
		cmdViewReview.addActionListener(aController);
		cmdViewReview.setEnabled(cmbReviews.getItemCount() > 0 && cmbReviews.isEnabled());
		cmdViewReview.setActionCommand("ViewReview");
	
		cmdUpdate = new JButton("Update");
		cmdUpdate.setBounds(50, 195, 100, 20);
		cmdUpdate.addActionListener(aController);

		cmdCancel = new JButton("Cancel");
		cmdCancel.setBounds(290, 195, 100, 20);
		cmdCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				dispose();
			}
		});

		add(lblTitle);
		add(lblTitle2);
		add(lblAuthor);
		add(lblSubChair);
		add(lblAcceptance);
		add(txtAuthor);

		if(subProgram.getID()==0)
		{
			add(cmbSubChair); 
		}
		else
		{  
			txtSubChair = new JLabel(subProgram.toString());
			txtSubChair.setBounds(210,80,185,20);
			add(txtSubChair);
		}

		add(lblReviews);
		add(cmbReviews);
		add(cmbAcceptance);
		add(cmdUpdate);
		add(cmdCancel);
		add(cmdViewReview);

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
	
	public Object getReview() {
	  return cmbReviews.getSelectedItem();
	}

	public Object getSubProgramChair() {
		return cmbSubChair.getSelectedItem();
	} 
}
