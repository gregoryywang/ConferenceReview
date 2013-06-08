package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import model.Paper;
import model.Recommendation;
import model.Review;
import model.Reviewer;
import model.Role;
import model.Status;
import model.SubProgramChair;
import model.User;
import service.PaperService;
import service.UserService;
import controller.Controller;

/**
 * A Dialog of all actions a SubProgramChair can take.
 * @author Roshun Jones (template for layout)
 * @author Danielle Tucker
 *
 */
public class SubPGChairDialog extends JDialog
{
	/**
	 * The id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The parent
	 */
	private final MainView my_parent;
	
	/**
	 * The controller
	 */
	private final Controller my_controller;
	
	/**
	 * The subprogram chair user
	 */
	private final SubProgramChair my_SPChair;
	
	/**
	 * The Paper under observation.
	 */
	private final Paper my_paper;
	
	/**
	 * The comments for the recommendation
	 */
	private JTextPane txtComments;
	
	/**
	 * The rating for the recommendation
	 */
	private JComboBox cmbRating;
	
	/**
	 * The list of reviewers for the paper.
	 */
	private final JList reviewer_choices = new JList();

	/**
	 * constructor
	 * @param aParent the parent view
	 * @param aController the controller for actions
	 * @param SPChair the subprogram chair user
	 * @param aPaper the paper under consideration
	 */
	public SubPGChairDialog(final MainView aParent, final Controller aController,
			final SubProgramChair SPChair, final Paper aPaper)
	{
		my_parent = aParent;
		my_controller = aController;
		my_SPChair = SPChair;
		my_paper = aPaper;


		setTitle("Sub Program Chair Command Center");
		setModal(false);
		setSize(350, 350);
		//		setResizable(false);
		setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
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
		contentPanel.add(lblReviews);

		createReviewItems(aPaper, contentPanel);

		//Ratings
		JLabel lblMyRating = new JLabel("My Rating:");
		lblMyRating.setBounds(15, 174, 90, 14);
		contentPanel.add(lblMyRating);

		JLabel lblMyComments = new JLabel("My Comments:");
		lblMyComments.setBounds(15, 202, 90, 14);		
		contentPanel.add(lblMyComments);

		Recommendation rec = my_paper.getRecommendation();
		txtComments = new JTextPane();
		if (rec.getRating() == 0)
		{//New Rating
			cmbRating = new JComboBox();
			cmbRating.setBounds(120, 171, 200, 20);
			cmbRating.setEditable(false);
			cmbRating.setModel(new DefaultComboBoxModel(Recommendation.RATING_SCALE_HIGH_TO_LOW));		
			contentPanel.add(cmbRating);
		}
		else
		{//Show Old ratings
			JLabel lblRating = new JLabel(Integer.toString(rec.getRating()));
			lblRating.setBounds(120, 171, 200, 20);
			contentPanel.add(lblRating);

			txtComments.setText(rec.getComments());
			txtComments.setEditable(false);
		}
		JScrollPane jsp = new JScrollPane(txtComments);
		jsp.setBounds(120, 202, 200, 68);
		contentPanel.add(jsp);

		//Button Pane
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);
		//Toggle Buttons
		List<Reviewer> reviewers = my_SPChair.getReviewers(aPaper);
		if(reviewers.isEmpty())
		{
			JButton btnSave = new JButton("Save Reviewers");
			btnSave.setActionCommand("save_rev");
			btnSave.addActionListener(my_controller);
			buttonPane.add(btnSave);
		}
		else if (my_paper.getRecommendation().getRating()==0)
		{
			JButton btnSaveRec = new JButton("Save Recommendation");			
			btnSaveRec.setActionCommand("save_rec");
			btnSaveRec.addActionListener(my_controller);
			if(my_paper.getStatus() == Status.RECOMMENDATION_NEEDED)
			{
				buttonPane.add(btnSaveRec);
			}
		}
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				dispose();
			}
		});
		buttonPane.add(btnCancel);
		getRootPane().setDefaultButton(btnCancel);


		//Center dialog
		int parentWidth = MainView.WIDTH;
		int parentHeight = MainView.HEIGHT;
		int locX = (parentWidth / 2) - (getWidth() / 2);
		int locY = (parentHeight / 2) - (getHeight() / 2);
		setLocation(locX, locY);	
	}

	/**
	 * Gets the assignedReviewer.
	 * 
	 * @return the assignedReviewers
	 */
	public List<User> getAssignedReviewer()
	{
		Object[] select_obj = reviewer_choices.getSelectedValues();
		List<User> selections = new ArrayList<User>();
		for(Object obj: select_obj)
		{
			selections.add((User)obj);
		}
		return selections;
	}

	/**
	 * Get the rating
	 * @return the rating selected
	 */
	public int getRating()
	{
		String descriptor = (String)cmbRating.getSelectedItem();
		for(int i = 0; i < Recommendation.RATING_SCALE_HIGH_TO_LOW.length; i++)
		{
			if(descriptor.equals(Recommendation.RATING_SCALE_HIGH_TO_LOW[i]))
			{
				return 5-i;
			}
		}
		return 0;
	}

	/**
	 * Get the comments associated with the recommendation.
	 * @return
	 */
	public String getComments()
	{
		return txtComments.getText();
	}

	/**
	 * Create the buttons/text for each review/reviewer
	 * @param aPaper the paper
	 * @param contentPanel the panel
	 */
	private void createReviewItems(final Paper aPaper, JPanel contentPanel) {
		final List<Review> reviews = my_paper.getReviews();
		List<Reviewer> reviewers = my_SPChair.getReviewers(aPaper);
		List<User> reviewer_pool = UserService.getInstance().getAllUsers(aPaper, my_SPChair.getConference().getID(), Role.REVIEWER);
		if(reviewers.size() == 0)
		{
			reviewer_choices.setModel(new DefaultComboBoxModel(reviewer_pool.toArray()));
			JScrollPane scroll_pane = new JScrollPane(reviewer_choices);
			scroll_pane.setBounds(120, 83 + 0*30, 200, 20*3);
			contentPanel.add(scroll_pane);
		}
		else
		{
			for(int i = 0; i < 3; i++)
			{
				if(reviews.size() > i && reviews.get(i).getID() != 0)
				{ //display button for review
					JButton btnReviewer1 = new JButton("View Review");
					btnReviewer1.setBounds(120, 83 + i*30, 200, 20);
					btnReviewer1.addActionListener(new ReviewActionListener(reviews.get(i)));
					btnReviewer1.setEnabled(reviewers.size() > 0);
					contentPanel.add(btnReviewer1);	
				}
				else
				{//Display label for reviewer assigned
					JButton btnReviewer1 = new JButton("Review Not Avaliable");
					btnReviewer1.setBounds(120, 83 + i*30, 200, 20);
					btnReviewer1.setEnabled(false);
					contentPanel.add(btnReviewer1);			
				}
			}
		}
	}

	/**
	 * Listen for the button press of a review to launch a review to read
	 * @author Danielle Tucker
	 * @version 2013 Spring
	 */
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
}


