package gui;

import gui.TablePanel.TableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Paper;
import model.Reviewer;
import model.User;
import service.ConferenceService;
import service.UserService;
import controller.Controller;

public class ReviewerView extends JPanel
{

	/**
	 * The default serial ID
	 */
	private static final long serialVersionUID = 1L;
	private Reviewer my_user;
	private TablePanel tablePanel;
	private ReviewerController controller;

	public ReviewerView(final User the_user) 
	{
		my_user = new Reviewer(the_user);
		setLayout(new BorderLayout(0, 0));
		controller = new ReviewerController(my_user);

		String[][] properties = {
				{"java.lang.String", "Title", "Title", "false"},
				{"javax.swing.JButton", "Status", "Status", "true"},
				{"javax.swing.JButton", "Review", "Review", "true"},
		};
		// Create table panel.
		tablePanel = new TablePanel<Paper>(properties, controller);

		// Populate model
		tablePanel.setModel(my_user.viewPapers());

		add(tablePanel, BorderLayout.NORTH);

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
		
	}

	private class ReviewerController implements Controller, ActionListener
	{

		public ReviewerController(Reviewer the_reviewer) {
			
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

		}
	}
	
	
}


