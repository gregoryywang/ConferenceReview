package gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Administrator;
import model.Paper;
import model.Review;
import model.Viewer;
import dao.ConferenceDAO;

/**
 * Class to create a AdministratorView Object that
 * represents what an Administrator User Interface
 * looks like.
 * 
 * @author Levon Kechichian
 * @version 1.0
 */
public class AdminView extends JPanel implements Viewer
{
	/*
	 * depends how I know which conference I am manipulating
	 * I am assuming it is by ID but not sure how to
	 * obtain the correct conference to change its Date Object. 
	 */
	private static final int STUB_ID = 0;
	
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the Administrator Object
	 * associated with this View.
	 */
	private Administrator my_administrator;
	
	/**
	 * Creates a default AdminView Object.
	 */
	public AdminView()
	{
		super();
		my_administrator = new Administrator(0, "Admin", 
			"istrator", "SU", "root", "barackobama@thepresi.dent");
	}
	
	/**
	 * Method to fill the Panel.
	 */
	public void fillPanel()
	{
		final JPanel panel = new JPanel(new GridLayout(2, 2));
		
		final JLabel papers = new JLabel("Papers");
		//final JLabel paper_list = new JLabel(viewPapers().toArray().toString());
		final JLabel paper_list = new JLabel("THE PAPER LIST");
		final JLabel reviews = new JLabel("Reviews");
		//final JLabel review_list = new JLabel(viewReviews().toArray().toString());
		final JLabel review_list = new JLabel("THE REVIEW LIST");
		
		panel.add(papers);
		panel.add(paper_list);
		panel.add(reviews);
		panel.add(review_list);
		
		add(panel);
	}
	
	/**
	 * Returns a List of Paper Objects.
	 * 
	 * @return the List of Paper Objects
	 */
	@Override
	public List<Paper> viewPapers() 
	{
		
		return new ArrayList<Paper>(((ConferenceDAO) my_administrator.getConferenceDAO())
			.getConferences().get(STUB_ID).getPapers());
	}

	/**
	 * Returns a Collection of Review Objects.
	 * 
	 * @return the Collection of Review Objects
	 */
	@Override
	public List<Review> viewReviews() 
	{
		return new ArrayList<Review>(((ConferenceDAO)my_administrator.getConferenceDAO())
			.getConferences().get(STUB_ID).getPapers().get(STUB_ID).getReviews());
	}
	
}
