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
	private ProgramChair pgChair;

	//Form components
	private JLabel lblTitle;
	private JLabel lblAuthor;
	private JLabel lblSubChair;
	private JLabel lblAcceptance;
	private JLabel txtAuthor;
	private JComboBox cmbSubChair;
	private JLabel txtSubChair;
	private JComboBox cmbAcceptance;
	private JButton cmdViewPaper;
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

		lblTitle = new JLabel(aPaper.getTitle());
		lblTitle.setBounds(150,5,200,20);

		lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(40,50,50,20);
		txtAuthor = new JLabel(aPaper.getAuthor().getFullName());
		txtAuthor.setBounds(200,50,165,20);

		lblSubChair = new JLabel("Sub-Program Chair:");
		lblSubChair.setBounds(40,80,200,20);
		List<User> dropValues = UserService.getInstance().getAllUsers(aPaper, pgChair.getConference().getID(), Role.SUB_PROGRAM_CHAIR); 
		cmbSubChair = new JComboBox(new DefaultComboBoxModel(dropValues.toArray()));
		cmbSubChair.setBounds(200,80,165,20);
		SubProgramChair subProgram = PaperService.getInstance().getAssignedSubprogramChair(aPaper.getID());
		//cmbSubChair.setEnabled(subProgram.getID() == 0);
		if(subProgram.getID() != 0)
		{
			SubProgramChair[] dropdown = {subProgram};
			cmbSubChair.setModel(new DefaultComboBoxModel(dropdown));
		}

		lblAcceptance = new JLabel("Acceptance Descision:");
		lblAcceptance.setBounds(40,110,200,20);
		Status[] values = {Status.UNDECIDED, Status.ACCEPT, Status.DECLINE};
		cmbAcceptance = new JComboBox(new DefaultComboBoxModel(values));
		cmbAcceptance.setBounds(200,110,165,20);
		cmbAcceptance.setEnabled(pgChair.canAssignDecision());
		if(aPaper != null && aPaper.getAcceptanceStatus() != null)
			cmbAcceptance.setSelectedItem(aPaper.getAcceptanceStatus());
		
		
		cmdViewPaper = new JButton("View Paper");
		cmdViewPaper.setBounds(65, 165, 100, 20); //from l, from t, width, ht
		cmdViewPaper.addActionListener(aController);

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

		if(subProgram.getID()==0)
		{
			add(cmbSubChair); 
		}
		else
		{  
			txtSubChair = new JLabel(subProgram.toString());
			txtSubChair.setBounds(200,80,165,20);
			add(txtSubChair);
		}

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
