package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Paper;
import model.Review;
import model.Role;
import model.SubProgramChair;
import model.User;
import model.Viewer;
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
	private static final int REVIEWER_PANEL_COLUMNS = 2;
	
	/**
	 * Reference to the SubProgramChair Object associated with this view.
	 */
	private SubProgramChair my_user;
	
	/**
	 * Reference to the Collection of Paper Objects associated with this User.
	 */
	private List<Paper> my_papers;
	
	/**
	 * Constructs a default SubPGhairView Object.
	 */
	public SubPGChairView()
	{
		super("Sub-Program Chair");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		add(createCentralPanel(), BorderLayout.CENTER);
		my_user = new SubProgramChair(new Conference(),"first", "last", "password", "screenName", "me@apple.com");
		my_papers = new ArrayList<Paper>();
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
		my_papers = new ArrayList<Paper>();
	}
	
	public JPanel createCentralPanel()
	{
		final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
		
		panel.add(createReviewerPanel());
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
	
	public JPanel createReviewerPanel()
	{
		final JPanel reviewer_panel = new JPanel(new GridLayout(PANEL_ROWS, REVIEWER_PANEL_COLUMNS));
		
		reviewer_panel.add(new JLabel("Reviewer #1: "));
		
		// FOR THESE I NEED A 
		// UserService.getInstance().getUsers(Role.REVIEWER)
		final JComboBox reviewer1 = new JComboBox(
				new DefaultComboBoxModel(new UserDAO().getUsers(Role.REVIEWER).toArray()));
		reviewer1.setEditable(false);
		reviewer_panel.add(reviewer1);
		
		reviewer_panel.add(new JLabel("Reviewer #2: "));
		
		// FOR THESE I NEED A 
		// UserService.getInstance().getUsers(Role.REVIEWER)
		final JComboBox reviewer2 = new JComboBox(
				new DefaultComboBoxModel(new UserDAO().getUsers(Role.REVIEWER).toArray()));
		reviewer2.setEditable(false);
		reviewer_panel.add(reviewer2);
		
		reviewer_panel.add(new JLabel("Reviewer #3: "));
		
		// FOR THESE I NEED A 
		// UserService.getInstance().getUsers(Role.REVIEWER)
		final JComboBox reviewer3 = new JComboBox(
				new DefaultComboBoxModel(new UserDAO().getUsers(Role.REVIEWER).toArray()));
		reviewer3.setEditable(false);
		reviewer_panel.add(reviewer3);
		
		return reviewer_panel;
	}
	
	/**
	 * Initializes the SubProgramChair GUI.
	 */
	public void start()
	{
		
		setVisible(true);
		pack();
	}
	
	/**
	 * Assigns a Recommendation to a specified Paper.
	 * 
	 * @param the_paper the Paper that the
	 * Recommendation is being assigned to
	 */
	public void writeRecommendation(final Paper the_paper)
	{
		
	}
	
	/**
	 * View the Reviews associated with the specified Paper.
	 * 
	 * @param the_paper the Paper that
	 * the User is requesting Reviews for
	 */
	public List<Review> viewReview(final Paper the_paper)
	{
		return null;
	}
	
	/**
	 * Assigns a Reviewer to a specified Paper.
	 * 
	 * @param the_paper the Paper that
	 * the Reviewer is being assigned to
	 */
	public void assignReviewer(final Paper the_paper)
	{
		
	}
	
	/**
	 * The graphical representation of the
	 * specified paper.
	 * 
	 * @param the_paper the Paper that the User
	 * is Requesting to View
	 */
	public Paper viewPaper(final Paper the_paper)
	{
		return null;
	}
	
	/**
	 * Overrides the implemented Viewer method.
	 */
	@Override
	public List<Paper> viewPapers() {
		return null;
	}

	/**
	 * Overrides the implemented Viewer method.
	 */
	@Override
	public List<Review> viewReviews() {
		return null;
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
