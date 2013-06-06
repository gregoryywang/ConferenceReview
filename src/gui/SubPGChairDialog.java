package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import model.Conference;
import model.Paper;
import model.Recommendation;
import model.Review;
import model.Reviewer;
import model.Role;
import model.SubProgramChair;
import model.User;
import service.PaperService;
import service.UserService;
import controller.Controller;

public class SubPGChairDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MainView my_parent;
	private final Controller my_controller;
	private final SubProgramChair my_SPChair;
	private final Paper my_paper;

	public SubPGChairDialog(final MainView aParent, final Controller aController,
			final SubProgramChair SPChair, final Paper aPaper)
	{
		my_parent = aParent;
		my_controller = aController;
		my_SPChair = SPChair;
		my_paper = aPaper;

		JPanel contentPanel = new JPanel();

		setTitle("Sub Program Chair Command Center");
		setModal(true);
		setSize(350, 350);
		//		setResizable(false);
		setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		add(contentPanel, BorderLayout.CENTER);


		JLabel lblTitle = new JLabel(aPaper.getTitle());
		lblTitle.setBounds(15, 5, 350, 14);
		contentPanel.add(lblTitle);

		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(15, 33, 90, 14);
		contentPanel.add(lblAuthor);
		JLabel txtAuthor = new JLabel(aPaper.getAuthor().getFullName());
		txtAuthor.setBounds(120, 33, 200, 14);
		contentPanel.add(txtAuthor);

		JLabel lblAcceptance = new JLabel("Status:");
		lblAcceptance.setBounds(15, 58, 90, 14);
		contentPanel.add(lblAcceptance);
		JLabel lblStatus = new JLabel(aPaper.getStatus().displayName());
		lblStatus.setBounds(120, 58, 200, 14);
		contentPanel.add(lblStatus);

		//Reviews
		JLabel lblReviews = new JLabel("Reviews:");
		lblReviews.setBounds(15, 86, 90, 14);

		final List<Review> reviews = my_paper.getReviews();
		List<Reviewer> reviewers = my_SPChair.getReviewers(aPaper);
		for(int i = 0; i < 3; i++)
		{	
			if(reviews.size() > i)
			{//Display button for review 1
				JButton btnReviewer1 = new JButton("View Review");
				btnReviewer1.setBounds(120, 83 + i*30, 200, 20);
				btnReviewer1.addActionListener(new ReviewActionListener(reviews.get(i)));
				btnReviewer1.setEnabled(reviewers.size() > i);
				contentPanel.add(btnReviewer1);			
			}
			else if(reviewers.size() > i)
			{//Display label for reviewer assigned
				JButton btnReviewer1 = new JButton("Review Not Avaliable");
				btnReviewer1.setBounds(120, 83 + i*30, 200, 20);
				btnReviewer1.setEnabled(false);
				contentPanel.add(btnReviewer1);			
			}
			else
			{//Display dropdown for choosing the reviewer
				JComboBox cmbReviewer1 = new JComboBox();
				cmbReviewer1.setBounds(120, 83 + i*30, 200, 20);
				cmbReviewer1.setModel(new DefaultComboBoxModel());
				cmbReviewer1.setEditable(false);
				contentPanel.add(cmbReviewer1);
			}
		}
		
		/*
		//now add the rating and comments sections
		Recommendation rec =  my_SPChair.viewRecommendation(my_paper);
		boolean newRec = (rec.getRating() == 0);
		if(newRec) //new recommendation
		{
			JComboBox cmbRating = new JComboBox(new DefaultComboBoxModel(rec.RATING_SCALE_HIGH_TO_LOW));
			cmbRating.setBounds(200, 90, 165, 20);
			add(cmbRating);
		}
		else
		{

		}
		 */
		JLabel lblMyRating = new JLabel("My Rating:");
		lblMyRating.setBounds(15, 174, 90, 14);

		JComboBox cmbRating = new JComboBox();
		cmbRating.setBounds(120, 171, 200, 20);
		cmbRating.setEditable(true);
		cmbRating.setModel(new DefaultComboBoxModel(Recommendation.RATING_SCALE_HIGH_TO_LOW));

		JLabel lblMyComments = new JLabel("My Comments:");
		lblMyComments.setBounds(15, 202, 90, 14);		
		JTextPane txtComments = new JTextPane();
		JScrollPane jsp = new JScrollPane(txtComments);
		jsp.setBounds(120, 202, 200, 68);

		contentPanel.setLayout(null);
		contentPanel.add(lblTitle);
		contentPanel.add(lblReviews);
		contentPanel.add(lblAuthor);
		contentPanel.add(lblStatus);
		contentPanel.add(lblAuthor);
		contentPanel.add(lblStatus);
		contentPanel.add(lblMyRating);
		contentPanel.add(lblMyComments);
		contentPanel.add(cmbRating);
		contentPanel.add(jsp);

		//Button Pane
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);

		JButton btnSave = new JButton("Save Changes");
		btnSave.setActionCommand("Save");
		btnSave.addActionListener(aController);
		buttonPane.add(btnSave);
		getRootPane().setDefaultButton(btnSave);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				dispose();
			}
		});
		buttonPane.add(btnCancel);


		//Center dialog
		int parentWidth = MainView.WIDTH;
		int parentHeight = MainView.HEIGHT;
		int locX = (parentWidth / 2) - (getWidth() / 2);
		int locY = (parentHeight / 2) - (getHeight() / 2);
		setLocation(locX, locY);	
	}
	/*
	public Object getDecision() {
		return cmbAcceptance.getSelectedItem();
	}

	public Object getSubProgramChair() {
		return cmbSubChair.getSelectedItem();
	} 
	 */



	/**
	 * Gets the SubProgramChair.
	 * 
	 * @return the SubProgramChair
	 */
	public User getSubProgramChair()
	{
		return my_SPChair;
	}




	private class ReviewActionListener implements ActionListener
	{
		private Review my_review;

		private ReviewActionListener(final Review the_review)
		{
			my_review = the_review;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new ReviewForm(my_paper, my_SPChair, my_review).start();
		}	
	}
	/**
	 * Private class to Listen for Reviewer events.
	 */
	private class ReviewerAction extends AbstractAction
	{
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Reference to the JButton.
		 */
		private JButton my_button;

		/**
		 * Reference to the JComboBox.
		 */
		private JComboBox my_combo_box;

		/**
		 * Reference to the Paper.
		 */
		private Paper my_paper;

		/**
		 * Constructs a new ReviewerAction from the
		 * given button and combo box.
		 */
		private ReviewerAction(final JButton the_button, final JComboBox the_combo_box, 
				final Paper the_paper)
		{
			super();
			my_button = the_button;
			my_combo_box = the_combo_box;
			my_paper = the_paper;
		}

		/**
		 * Overrides the implemented interface method.
		 * 
		 * @param the_event the Object that fired the event
		 */
		@Override
		public void actionPerformed(final ActionEvent the_event) 
		{
			final List<Review> all_reviews = my_paper.getReviews();
			boolean new_reviewer = true;

			for (Review review : all_reviews)
			{
				if (((User) review.getReviewer()).getID() == ((User) my_combo_box.getSelectedItem()).getID())
				{
					new_reviewer = false;
				}
			}
			if (new_reviewer)
			{
				List<User> user_list = UserService.getInstance().getAllUsers();
				final User chosen_reviewer = user_list.get(my_combo_box.getSelectedIndex());
				((SubProgramChair) my_SPChair).assignReviewer(chosen_reviewer, 
						my_paper, my_SPChair.getConference());
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The Reviewer " + 
						((User) my_combo_box.getSelectedItem()).toString() + " has already been chosen.");
			}
		}
	}
}


