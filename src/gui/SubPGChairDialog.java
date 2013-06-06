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
	private MainView my_parent;
	private Controller my_controller;
	private SubProgramChair my_SPChair;
	private Paper my_paper;

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
		setSize(400, 240);
//		setResizable(false);
		setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		add(contentPanel, BorderLayout.CENTER);


		JLabel lblTitle = new JLabel(aPaper.getTitle());
		lblTitle.setBounds(107, 5, 83, 14);
		contentPanel.add(lblTitle);

		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(15, 33, 45, 14);
		contentPanel.add(lblAuthor);
		JLabel txtAuthor = new JLabel(aPaper.getAuthor().getFullName());
		txtAuthor.setBounds(104, 33, 116, 14);
		contentPanel.add(txtAuthor);

		JLabel lblAcceptance = new JLabel("Status:");
		lblAcceptance.setBounds(15, 58, 45, 14);
		contentPanel.add(lblAcceptance);
		JLabel lblStatus = new JLabel(aPaper.getStatus().displayName());
		lblAuthor.setBounds(104, 58, 90, 14);
		contentPanel.add(lblStatus);

		//Reviews
		JLabel lblReviews = new JLabel("Reviews:");
		lblReviews.setBounds(15, 86, 45, 14);

		JComboBox cmbReviewer1 = new JComboBox();
		cmbReviewer1.setBounds(104, 83, 191, 20);
		cmbReviewer1.setModel(new DefaultComboBoxModel(new String[] {"User 1", "User 2", "User 3", "User 4", "User 5", "User 6"}));
		cmbReviewer1.setEditable(false);

		JComboBox cmbReviewer2 = new JComboBox();
		cmbReviewer2.setBounds(104, 140, 191, 20);
		cmbReviewer2.setEditable(false);

		JComboBox cmbReviewer3 = new JComboBox();
		cmbReviewer3.setBounds(104, 109, 191, 20);
		cmbReviewer3.setEditable(false);

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
		lblMyRating.setBounds(15, 174, 52, 14);

		JComboBox cmbRating = new JComboBox();
		cmbRating.setBounds(104, 171, 191, 20);
		cmbRating.setEditable(true);
		cmbRating.setModel(new DefaultComboBoxModel(Recommendation.RATING_SCALE_HIGH_TO_LOW));

		JLabel lblMyComments = new JLabel("My Comments:");
		lblMyComments.setBounds(15, 202, 71, 14);		
		JTextPane txtComments = new JTextPane();
		JScrollPane jsp = new JScrollPane(txtComments);
		jsp.setBounds(104, 202, 191, 68);

		contentPanel.setLayout(null);
		contentPanel.add(lblTitle);
		contentPanel.add(lblReviews);
		contentPanel.add(lblAuthor);
		contentPanel.add(lblStatus);
		contentPanel.add(lblAuthor);
		contentPanel.add(lblStatus);
		contentPanel.add(cmbReviewer1);
		contentPanel.add(cmbReviewer2);
		contentPanel.add(cmbReviewer3);
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




	/**
	 * Assigns a Reviewer to a specified Paper.
	 * 
	 * @param the_paper the Paper that
	 * the Reviewer is being assigned to
	 */
	public void assignReviewer(final Paper the_paper, final User the_user, final Conference the_conference)
	{
		((SubProgramChair) my_SPChair).assignReviewer(the_user, the_paper, the_conference);
	}

	/**
	 * The graphical representation of the
	 * specified paper.
	 * 
	 * @param the_paper the Paper that the User
	 * is Requesting to View
	 */
	public void viewPaper(final Paper the_paper)
	{
		final Paper paper = (Paper) PaperService.getInstance().getAssignedPapers
				(my_SPChair.getID(), my_SPChair.getConference().getID(), Role.SUB_PROGRAM_CHAIR);

		// populate PaperForm field with the given paper
		new PaperForm(my_SPChair, paper).start();
	}

	/**
	 * View the Reviews associated with the specified Paper.
	 * 
	 * @param the_paper the Paper that
	 * the User is requesting Reviews for
	 * @param the_user the User that wrote
	 * the Review
	 */
	public void viewReview(final Paper the_paper, final User the_user, final Review the_review)
	{
		// I need to create a ReviewForm here
		// and then populate the fields with
		// the_users Review (this will be disabled / invisible if the Review hasn't been written)

		final List<Review> all_user_reviews = PaperService.getInstance().getReviews(the_paper.getID());
		Review user_review = null;
		for (Review review : all_user_reviews)
		{
			if (review.getReviewer().getID() == the_user.getID())
			{
				user_review = review;
			}
		}

		// use the user_review to populate the ReviewForm here
		new ReviewForm(the_paper, the_user, user_review).start();
	}


	/**
	 * Method to update the ReviewerPanel based on the chosen paper.
	 * 
	 * @param the_paper the Paper that is populating the Reviewer drop downs
	 * @return returns a JPanel of the current Reviewers for a specified Paper
	 */
	private JPanel createReviewerPanel(final Paper the_paper)
	{
		final JPanel reviewer_panel = new JPanel(new GridLayout(5, 5));

		// create all pre-existing reviewers
		for (int i = 0; i < the_paper.getReviews().size(); i++)
		{
			final JPanel panel = new JPanel();
			panel.add(new JLabel("Reviewer #" + (i + 1) + ": "));
			final JComboBox reviewer_combo_box = new JComboBox();
			reviewer_combo_box.addItem(the_paper.getReviews().get(i));
			reviewer_combo_box.setEditable(false);
			panel.add(reviewer_combo_box);
			final JButton review_button = new JButton("My Review");
			review_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					final Reviewer reviewer = (Reviewer) reviewer_combo_box.getSelectedItem();
					viewReview(the_paper, my_SPChair, reviewer.getReview(the_paper.getID()));
				}
			});
			panel.add(review_button);
			reviewer_panel.add(panel);
		}

		// create drop downs for potential reviewers
		for (int i = the_paper.getReviews().size(); i < 3; i++)
		{
			final JPanel panel = new JPanel();
			panel.add(new JLabel("Reviewer #" + (i + 1) + ": "));
			final JComboBox reviewer_combo_box = new JComboBox(
					new DefaultComboBoxModel(UserService.getInstance().getAllUsers().toArray()));
			reviewer_combo_box.setEditable(false);
			panel.add(reviewer_combo_box);
			final JButton reviewer_button = new JButton("Choose Reviewer #" + (i + 1) + ": ");
			reviewer_button.addActionListener(new ReviewerAction(reviewer_button, reviewer_combo_box, the_paper));
			panel.add(reviewer_button);
			reviewer_panel.add(panel);
		}

		return reviewer_panel;
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


