package gui;

import gui.TablePanel.TableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Paper;
import model.ProgramChair;
import model.User;
import service.ConferenceService;
import service.UserService;
import controller.Controller;

public class PGChairView extends JPanel
{

	/**
	 * The default serial ID
	 */
	private static final long serialVersionUID = 1L;
	private ProgramChair my_user;
	private TablePanel tablePanel;
	private PGChairViewController controller;

	public PGChairView(final User the_user) 
	{
		my_user = new ProgramChair(the_user);
		setLayout(new BorderLayout(0, 0));
		controller = new PGChairViewController(my_user);

		String[][] properties = {
				{"java.lang.JButton", "Title", "Title", "true"},
				{"javax.swing.JButton", "Status", "Status", "true"},
				{"javax.swing.JButton", "Recommendation", "Recommendation", "true"},
				{"javax.swing.JButton", "Reviews", "Reviews", "true"}
				
		};
		// Create table panel.
		tablePanel = new TablePanel<Paper>(properties, controller);

		// Populate model
		tablePanel.setModel(my_user.viewPapers());

		add(tablePanel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		JButton AddSubmissionButton = new JButton("View Details");
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

		User testUser = UserService.getInstance().authenticateUser("PrgmChairTest", "PrgmChairTest");
		testUser.setConference(ConferenceService.getInstance().getConference(2));
		PGChairView test = new PGChairView(testUser);
		JFrame testFrame = new JFrame();
		testFrame.getContentPane().add(test);
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.pack();
		testFrame.setVisible(true);
	}

	private class PGChairViewController implements Controller, ActionListener
	{

		public PGChairViewController(ProgramChair the_pg_chair) {
			
		}

		@Override
		public void update(Object aObject) {

		}

		@Override
		public void setModel(Object aObject) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Title"))
			{
				System.out.println(e.getSource());
				System.out.println("Title is pressed");
			}
			else if(e.getActionCommand().equals("Status"))
			{
				System.out.println(e.getSource());
				System.out.println("Status is pressed");
			}
			else if(e.getActionCommand().equals("Recommendation"))
			{
				System.out.println(e.getSource());
				System.out.println("Recommendation is pressed");
			}
			else if(e.getActionCommand().equals("Reviews"))
			{
				System.out.println(e.getSource());
				System.out.println("Reviews is pressed");
			}
		}
	}
	
}

