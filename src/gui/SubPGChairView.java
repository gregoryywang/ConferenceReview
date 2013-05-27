package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.Conference;
import model.Paper;
import model.Review;
import model.SubProgramChair;
import model.Viewer;

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
	 * Reference to the SubProgramChair Object associated with this view.
	 */
	private SubProgramChair my_user;
	
	/**
	 * Reference to the Collection of Paper Objects associated with this User.
	 */
	private List<Paper> my_papers;
	
	/**
	 * Constructs a new SubPGhairView Object.
	 */
	public SubPGChairView()
	{
		super("Sub-Program Chair");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		my_user = new SubProgramChair(new Conference(),"first", "last", "password", "screenName", "me@apple.com");
		my_papers = new ArrayList<Paper>();
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
