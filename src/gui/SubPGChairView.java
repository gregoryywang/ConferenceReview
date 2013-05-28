package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Conference;
import model.Paper;
import model.Recommendation;
import model.Review;
import model.Reviewer;
import model.Role;
import model.SubProgramChair;
import model.User;
import model.Viewer;
import service.PaperService;
import dao.PaperDAO;
import dao.UserDAO;

public class SubPGChairView extends JFrame implements Viewer
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default frame width.
	 */
	private static final int FRAME_WIDTH = 500;
	
	/**
	 * The default frame height.
	 */
	private static final int FRAME_HEIGHT = 300;
	
	/**
	 * The default background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.MAGENTA;
	
	/**
	 * The default number of columns in the central panel.
	 */
	private static final int PANEL_COLUMNS = 1;
	
	/**
	 * The default number of rows in the central panel.
	 */
	private static final int PANEL_ROWS = 0;
	
	/**
	 * The default number of reviewer panel columns.
	 */
	private static final int SUB_PANEL_COLUMNS = 3;
	
	/**
	 * Reference to the SubProgramChair Object associated with this view.
	 */
	private User my_user;
	
	/**
	 * Reference to the Collection of Paper Objects associated with this User.
	 */
	private List<Paper> my_papers = new ArrayList<Paper>();

	/**
	 * Constructs a default SubPGhairView Object.
	 */
	public SubPGChairView()
	{
		super("Sub-Program Chair");
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		add(createCentralPanel(), BorderLayout.CENTER);
		my_user = new SubProgramChair(new Conference(),"first", "last", "password", "screenName", "me@apple.com");
	}
	
	/**
	 * Constructs a SubPGChairView from a specific
	 * SubProgramChair User.
	 */
	public SubPGChairView(final User the_user)
	{
		super("Sub-Program Chair");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		my_user = (SubProgramChair) the_user;
		my_papers = PaperService.getInstance().getAssignedPapers(the_user.getID(), 
			((SubProgramChair) the_user).getConference().getID(), Role.SUB_PROGRAM_CHAIR);
	}
	
	private JPanel createCentralPanel()
	{
		final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
		
		panel.add(createPaperPanel());
		
		panel.add(createReviewerPanel(new Paper()));
		// need to create something to view reviewers
		// need to create something to assign a reviewer
		// neeed to create something to view reviewers reviews
		
		// need to create something to view recommendations
		// need to create something to write recommendations
		
		// need to create something to view all papers
		// need to create something to view a particular paper
		
		// IM THINKING ReviewForm, ReviewerForm, and RecommendationForm CLASSES WILL BE NEEDED!!!! 
		
		return panel;
	}
	
	/**
	 * Method to create and return a JPanel of all Papers
	 */
	private JPanel createPaperPanel()
	{
		final JPanel paper_panel = new JPanel(new GridLayout(PANEL_ROWS, SUB_PANEL_COLUMNS));
		
		paper_panel.add(new JLabel("All assigned papers: "));
	//	my_papers = new ArrayList<Paper>();
		if (my_papers.size() != 0)
		{
			final JComboBox paper_combo_box = new JComboBox(my_papers.toArray());
			paper_combo_box.setEditable(false);
			paper_panel.add(paper_combo_box);
			final JButton paper_button = new JButton("Go");
			paper_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					final Paper chosen_paper = (Paper) paper_combo_box.getSelectedItem();
					new PaperForm(new SubPGChairView(), chosen_paper).start();
				}
			});
		}
		else
		{
			JOptionPane.showMessageDialog(null, "my_papers is empty");
		}
		
		return paper_panel;
	}
	
	/**
	 * Method to create and return a JPanel of all the Reviewers
	 * associated with a specified Paper.
	 * 
	 * @return returns a JPanel of the current Reviewers for a specified Paper
	 */
	private JPanel createReviewerPanel(final Paper the_paper)
	{
		final JPanel reviewer_panel = new JPanel(new GridLayout(PANEL_ROWS, SUB_PANEL_COLUMNS));
		
		reviewer_panel.add(new JLabel("Reviewer #1: "));
		
		// FOR THESE I NEED A 
		// UserService.getInstance().getUsers(Role.REVIEWER)
		final JComboBox reviewer1 = new JComboBox(
				new DefaultComboBoxModel(new UserDAO().getUsers(Role.REVIEWER).toArray()));
		reviewer1.setEditable(false);
		reviewer_panel.add(reviewer1);
		reviewer_panel.add(new JButton("Choose Reviewer #1"));
		
		reviewer_panel.add(new JLabel("Reviewer #2: "));
		
		// FOR THESE I NEED A 
		// UserService.getInstance().getUsers(Role.REVIEWER)
		final JComboBox reviewer2 = new JComboBox(
				new DefaultComboBoxModel(new UserDAO().getUsers(Role.REVIEWER).toArray()));
		reviewer2.setEditable(false);
		reviewer_panel.add(reviewer2);
		reviewer_panel.add(new JButton("Choose Reviewer #2"));
		
		reviewer_panel.add(new JLabel("Reviewer #3: "));
		
		// FOR THESE I NEED A 
		// UserService.getInstance().getUsers(Role.REVIEWER)
		final JComboBox reviewer3 = new JComboBox(
				new DefaultComboBoxModel(new UserDAO().getUsers(Role.REVIEWER).toArray()));
		reviewer3.setEditable(false);
		reviewer_panel.add(reviewer3);
		reviewer_panel.add(new JButton("Choose Reviewer #3"));
		
		return reviewer_panel;
	}
	
	/**
	 * Initializes the SubProgramChair GUI.
	 */
	public void start()
	{
		createCentralPanel();
		pack();
		setVisible(true);
	}
	
	/**
	 * Gets the SubProgramChair.
	 * 
	 * @return the SubProgramChair
	 */
	public User getSubProgramChair()
	{
		return my_user;
	}
	
	/**
	 * Assigns a Recommendation to a specified Paper.
	 * 
	 * @param the_paper the Paper that the
	 * Recommendation is being assigned to
	 */
	public void writeRecommendation(final Paper the_paper)
	{
		// I need to create the RecommendationForm here
		// and then grab the data from the recommendation
		final Recommendation recommendation = new Recommendation();
		((SubProgramChair) my_user).submitRecommendation(recommendation, the_paper);
	}
	
	/**
	 * View the Recommendation written by this User
	 * on a specified Paper
	 * 
	 * @param the_paper the Paper that the Recommendation
	 * was written for
	 */
	public void viewRecommendation(final Paper the_paper)
	{
		// need to create a RecommendationForm here
		// and populate the fields with the Users
		// Recommendation.
		// If the recommendation has not been written yet
		// then you need to disable the button (or make not visible)
		List<Recommendation> all_recommendations = PaperService.getInstance().getRecommendations(the_paper.getID());
//		for (Recommendation recommendation : all_recommendations)
//		{
//			// need to be able to find this Users Recommendation based on his ID
//			final String comments = recommendation.getComments();
//			final int rating = recommendation.getRating();
//			final User reviewer = recommendation.getReviewer();
//			final int id = recommendation.getID();
//		}
		final String comments = all_recommendations.get(0).getComments();
		final int rating = all_recommendations.get(0).getRating();
		final Reviewer reviewer = (Reviewer) all_recommendations.get(0).getReviewer();
		final int id = all_recommendations.get(0).getID();
		
		// populate RecommendationForm fields with the given recommendation.
		
	}
	
	/**
	 * View the Reviews associated with the specified Paper.
	 * 
	 * @param the_paper the Paper that
	 * the User is requesting Reviews for
	 * @param the_user the User that wrote
	 * the Review
	 */
	public void viewReview(final Paper the_paper, final User the_user)
	{
		// I need to create a ReviewForm here
		// and then populate the fields with
		// the_users Review (this will be disabled / invisible if the Review hasn't been written)
		
		final List<Review> all_user_reviews = PaperService.getInstance().getReviews(the_paper.getID());
		Review user_review;
		for (Review review : all_user_reviews)
		{
			if (review.getReviewer().getID() == the_user.getID())
			{
				user_review = review;
			}
		}
		
		// use the user_review to populate the ReviewForm here
	}
	
	/**
	 * Assigns a Reviewer to a specified Paper.
	 * 
	 * @param the_paper the Paper that
	 * the Reviewer is being assigned to
	 */
	public void assignReviewer(final Paper the_paper, final User the_user, final Conference the_conference)
	{
		((SubProgramChair) my_user).assignReviewer(the_user, the_paper, the_conference);
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
		// PaperForm class???
		
		// need a PaperService.getPaper(the_paper.getID());
		
		final Paper paper = new PaperDAO().getPaper(the_paper.getID());
		
		// populate PaperForm field with the given paper
		
	}
	
	/**
	 * Overrides the implemented Viewer method.
	 */
	@Override
	public List<Paper> viewPapers() 
	{
		return my_papers;
	}

	/**
	 * Overrides the implemented Viewer method.
	 */
	@Override
	public List<Review> viewReviews() 
	{
		return new ArrayList<Review>();
	}
	
	/**
	 * Main method to test the SubPGChairView.
	 * 
	 * @param the_args the command-line arguments
	 */
	public static void main(final String[] the_args)
	{
		new SubPGChairView().start();
	}
	
}
