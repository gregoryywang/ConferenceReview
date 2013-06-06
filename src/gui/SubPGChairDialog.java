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
import model.SubProgramChair;
import model.User;
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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MainView my_parent;
	private final Controller my_controller;
	private final SubProgramChair my_SPChair;
	private final Paper my_paper;
	private JTextPane txtComments;
	private JComboBox cmbRating;
	private final JList reviewer_choices = new JList();

	public SubPGChairDialog(final MainView aParent, final Controller aController,
			final SubProgramChair SPChair, final Paper aPaper)
	{
		my_parent = aParent;
		my_controller = aController;
		my_SPChair = SPChair;
		my_paper = aPaper;

		JPanel contentPanel = new JPanel();

		setTitle("Sub Program Chair Command Center");
		setModal(false);
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

		createReviewItems(aPaper, contentPanel);

		JLabel lblMyRating = new JLabel("My Rating:");
		lblMyRating.setBounds(15, 174, 90, 14);

		cmbRating = new JComboBox();
		cmbRating.setBounds(120, 171, 200, 20);
		cmbRating.setEditable(false);
		cmbRating.setModel(new DefaultComboBoxModel(Recommendation.RATING_SCALE_HIGH_TO_LOW));

		JLabel lblMyComments = new JLabel("My Comments:");
		lblMyComments.setBounds(15, 202, 90, 14);		
		txtComments = new JTextPane();
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

		List<Reviewer> reviewers = my_SPChair.getReviewers(aPaper);
		if(reviewers.isEmpty())
		{
			JButton btnSave = new JButton("Save Reviewers");
			btnSave.setActionCommand("save_rev");
			btnSave.addActionListener(my_controller);
			buttonPane.add(btnSave);
		}
		else
		{
			JButton btnSaveRec = new JButton("Save Recommendation");
			btnSaveRec.setActionCommand("save_rec");
			btnSaveRec.addActionListener(my_controller);
			buttonPane.add(btnSaveRec);
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

	public int getRating()
	{
		return (Integer) cmbRating.getSelectedItem();
	}

	public String getComments()
	{
		return txtComments.getText();
	}

	private void createReviewItems(final Paper aPaper, JPanel contentPanel) {
		final List<Review> reviews = my_paper.getReviews();
		List<Reviewer> reviewers = my_SPChair.getReviewers(aPaper);
		List<User> reviewer_pool = UserService.getInstance().getAllUsers(my_paper, my_SPChair.getConference().getID(), Role.REVIEWER);
		for(User usr: reviewer_pool)
		{
			System.out.println(usr);
		}
		
		if(reviewers.size() == 0)
		{
			reviewer_choices.setModel(new DefaultComboBoxModel(reviewer_pool.toArray()));
			JScrollPane scroll_pane = new JScrollPane(reviewer_choices);
			scroll_pane.setBounds(120, 83 + 0*30, 200, 20*3);
			contentPanel.add(scroll_pane);
		}
		else
		{
			System.out.println("Number of Reviews:" + reviews.size());
			for(int i = 0; i < 4; i++)
			{
				if(reviews.size() > i)
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


