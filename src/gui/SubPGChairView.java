package gui;

public class SubPGChairView
{
	
}


//package gui;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import javax.swing.AbstractAction;
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//import model.Author;
//import model.Conference;
//import model.Paper;
//import model.Recommendation;
//import model.Review;
//import model.Reviewer;
//import model.Role;
//import model.SubProgramChair;
//import model.User;
//import model.Viewer;
//import service.PaperService;
//import service.UserService;
//
//public class SubPGChairView extends JPanel implements Viewer
//{
//	/**
//	 * The default serial version UID.
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	/**
//	 * The default background color.
//	 */
//	private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
//	
//	/**
//	 * The default number of columns in the central panel.
//	 */
//	private static final int PANEL_COLUMNS = 1;
//	
//	/**
//	 * The default number of rows in the central panel.
//	 */
//	private static final int PANEL_ROWS = 0;
//	
//	/**
//	 * Reference to the SubProgramChair Object associated with this view.
//	 */
//	private SubProgramChair my_user;
//	
//	/**
//	 * Reference to the Collection of Paper Objects associated with this User.
//	 */
//	private List<Paper> my_papers;
//
//	/**
//	 * Constructs a default SubPGhairView Object.
//	 */
//	public SubPGChairView()
//	{
//		super();
//		my_user = new SubProgramChair(new Conference(),"first", "last", "password", "screenName", "me@apple.com");
//		my_papers = new ArrayList<Paper>();
//		add(createCentralPanel(), BorderLayout.CENTER);
//	}
//	
//	/**
//	 * TEST CONSTRUCTOR.
//	 */
//	public SubPGChairView(final List<Paper> the_papers)
//	{
//		super();
//		my_user = new SubProgramChair(new Conference(), "first", "last", "Pass", "name", "@me.com");
//		my_papers = the_papers;
//		add(createCentralPanel(), BorderLayout.CENTER);
//	}
//	
//	/**
//	 * Constructs a SubPGChairView from a specific
//	 * SubProgramChair User.
//	 */
//	public SubPGChairView(final User the_user)
//	{
//		super();
//		my_user = new SubProgramChair(the_user);
//		my_papers = PaperService.getInstance().getAssignedPapers(the_user.getID(), 
//			((SubProgramChair) the_user).getConference().getID(), Role.SUB_PROGRAM_CHAIR);
//		add(createCentralPanel(), BorderLayout.CENTER);
//	}
//	
//	/**
//	 * Private method to create and fill the central panel of the SubPGChairView.
//	 * 
//	 * @return returns a panel filled with components
//	 */
//	private JPanel createCentralPanel()
//	{
//		final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
//		
//		panel.add(createPaperPanel());
//		panel.add(createReviewerPanel(my_papers.get(0)));
//		// need to create something to view reviewers reviews
//		panel.add(createRecommendationPanel(my_papers.get(0)));
//		
//		return panel;
//	}
//	
//	/**
//	 * Private method to create the recommendation portion of the panel
//	 * with the given paper.
//	 * 
//	 * @param the_paper the paper that is populating the panel
//	 * @return returns a panel that is populated by the given paper
//	 */
//	private JPanel createRecommendationPanel(final Paper the_paper)
//	{
//		final JPanel recommendation_panel = new JPanel();
//		
//		final JButton recommendation_button = new JButton();
//		if ("".equals(the_paper.getRecommendation()))
//		{
//			recommendation_button.setText("View Recommendation");
//		}
//		else
//		{
//			recommendation_button.setText("Create Recommendation");
//		}
//		
//		recommendation_button.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(final ActionEvent the_event)
//			{
//				if ("Create Recommendation".equals(recommendation_button.getText()))
//				{
//					new RecommendationForm(my_user, new Recommendation(my_user, 0, ""), 
//						the_paper).start();
//					
//				}
//				else
//				{
//					new RecommendationForm(my_user, the_paper.getRecommendation(), the_paper).start();
//				}
//			}
//		});
//		recommendation_panel.add(recommendation_button);
//		
//		return recommendation_panel;
//	}
//	
//	/**
//	 * Method to create and return a JPanel of all Papers
//	 */
//	private JPanel createPaperPanel()
//	{
//		final JPanel paper_panel = new JPanel();
//		
//		paper_panel.add(new JLabel("All assigned papers: "));
//		if (my_papers.size() != 0)
//		{
//			final JComboBox paper_combo_box = new JComboBox();
//			for (Paper paper : my_papers)
//			{
//				paper_combo_box.addItem(paper.getTitle());
//			}
//			paper_combo_box.setEditable(false);
//			paper_combo_box.addActionListener(new ActionListener()
//			{
//				public void actionPerformed(final ActionEvent the_event)
//				{
//					createReviewerPanel(my_papers.get(paper_combo_box.getSelectedIndex()));
//				}
//			});
//			paper_panel.add(paper_combo_box);
//			final JButton paper_button = new JButton("Go");
//			paper_button.addActionListener(new ActionListener()
//			{
//				public void actionPerformed(final ActionEvent the_event)
//				{
//					final Paper chosen_paper = my_papers.get(paper_combo_box.getSelectedIndex());
//					new PaperForm(my_user, chosen_paper).start();
//				}
//			});
//			final JPanel button_panel = new JPanel();
//			button_panel.add(paper_button);
//			paper_panel.add(button_panel);
//		}
//		
//		return paper_panel;
//	}
//	
//	/**
//	 * Method to update the ReviewerPanel based on the chosen paper.
//	 * 
//	 * @param the_paper the Paper that is populating the Reviewer drop downs
//	 * @return returns a JPanel of the current Reviewers for a specified Paper
//	 */
//	private JPanel createReviewerPanel(final Paper the_paper)
//	{
//		final JPanel reviewer_panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
//		
//		// create all pre-existing reviewers
//		for (int i = 0; i < the_paper.getReviews().size(); i++)
//		{
//			final JPanel panel = new JPanel();
//			panel.add(new JLabel("Reviewer #" + (i + 1) + ": "));
//			final JComboBox reviewer_combo_box = new JComboBox();
//			reviewer_combo_box.addItem(the_paper.getReviews().get(i));
//			reviewer_combo_box.setEditable(false);
//			panel.add(reviewer_combo_box);
//			final JButton review_button = new JButton("My Review");
//			review_button.addActionListener(new ActionListener()
//			{
//				public void actionPerformed(final ActionEvent the_event)
//				{
//					final Reviewer reviewer = (Reviewer) reviewer_combo_box.getSelectedItem();
//					viewReview(the_paper, my_user, reviewer.getReview(the_paper.getID()));
//				}
//			});
//			panel.add(review_button);
//			reviewer_panel.add(panel);
//		}
//		
//		// create drop downs for potential reviewers
//		for (int i = the_paper.getReviews().size(); i < 3; i++)
//		{
//			final JPanel panel = new JPanel();
//			panel.add(new JLabel("Reviewer #" + (i + 1) + ": "));
//			final JComboBox reviewer_combo_box = new JComboBox(
//				new DefaultComboBoxModel(UserService.getInstance().getAllUsers().toArray()));
//			reviewer_combo_box.setEditable(false);
//			panel.add(reviewer_combo_box);
//			final JButton reviewer_button = new JButton("Choose Reviewer #" + (i + 1) + ": ");
//			reviewer_button.addActionListener(new ReviewerAction(reviewer_button, reviewer_combo_box, the_paper));
//			panel.add(reviewer_button);
//			reviewer_panel.add(panel);
//		}
//		
//		return reviewer_panel;
//	}
//	
//	/**
//	 * Gets the SubProgramChair.
//	 * 
//	 * @return the SubProgramChair
//	 */
//	public User getSubProgramChair()
//	{
//		return my_user;
//	}
//	
//	/**
//	 * Assigns a Recommendation to a specified Paper.
//	 * 
//	 * @param the_paper the Paper that the
//	 * Recommendation is being assigned to
//	 */
//	public void writeRecommendation(final Paper the_paper)
//	{
//		// I need to create the RecommendationForm here
//		// and then grab the data from the recommendation
//		final Recommendation recommendation = new Recommendation();
//		((SubProgramChair) my_user).submitRecommendation(recommendation, the_paper);
//	}
//	
//	/**
//	 * View the Recommendation written by this User
//	 * on a specified Paper
//	 * 
//	 * @param the_paper the Paper that the Recommendation
//	 * was written for
//	 */
//	public void viewRecommendation(final Paper the_paper)
//	{
//		final Recommendation recommendation = PaperService.getInstance().getRecommendation(the_paper.getID());
//		new RecommendationForm(my_user, recommendation, the_paper).start();
//	}
//	
//	/**
//	 * View the Reviews associated with the specified Paper.
//	 * 
//	 * @param the_paper the Paper that
//	 * the User is requesting Reviews for
//	 * @param the_user the User that wrote
//	 * the Review
//	 */
//	public void viewReview(final Paper the_paper, final User the_user, final Review the_review)
//	{
//		// I need to create a ReviewForm here
//		// and then populate the fields with
//		// the_users Review (this will be disabled / invisible if the Review hasn't been written)
//		
//		final List<Review> all_user_reviews = PaperService.getInstance().getReviews(the_paper.getID());
//		Review user_review = null;
//		for (Review review : all_user_reviews)
//		{
//			if (review.getReviewer().getID() == the_user.getID())
//			{
//				user_review = review;
//			}
//		}
//		
//		// use the user_review to populate the ReviewForm here
//		new ReviewForm(the_paper, the_user, user_review).start();
//	}
//	
//	/**
//	 * Assigns a Reviewer to a specified Paper.
//	 * 
//	 * @param the_paper the Paper that
//	 * the Reviewer is being assigned to
//	 */
//	public void assignReviewer(final Paper the_paper, final User the_user, final Conference the_conference)
//	{
//		((SubProgramChair) my_user).assignReviewer(the_user, the_paper, the_conference);
//	}
//	
//	/**
//	 * The graphical representation of the
//	 * specified paper.
//	 * 
//	 * @param the_paper the Paper that the User
//	 * is Requesting to View
//	 */
//	public void viewPaper(final Paper the_paper)
//	{
//		final Paper paper = (Paper) PaperService.getInstance().getAssignedPapers
//			(my_user.getID(), my_user.getConference().getID(), Role.SUB_PROGRAM_CHAIR);
//		
//		// populate PaperForm field with the given paper
//		new PaperForm(my_user, paper).start();
//	}
//	
//	/**
//	 * Overrides the implemented Viewer method.
//	 */
//	@Override
//	public List<Paper> viewPapers() 
//	{
//		return my_papers;
//	}
//
//	/**
//	 * Overrides the implemented Viewer method.
//	 */
//	@Override
//	public List<Review> viewReviews() 
//	{
//		return new ArrayList<Review>();
//	}
//	
//	/**
//	 * Private class to Listen for Reviewer events.
//	 */
//	private class ReviewerAction extends AbstractAction
//	{
//		/**
//		 * The default serial version UID.
//		 */
//		private static final long serialVersionUID = 1L;
//
//		/**
//		 * Reference to the JButton.
//		 */
//		private JButton my_button;
//		
//		/**
//		 * Reference to the JComboBox.
//		 */
//		private JComboBox my_combo_box;
//		
//		/**
//		 * Reference to the Paper.
//		 */
//		private Paper my_paper;
//		
//		/**
//		 * Constructs a new ReviewerAction from the
//		 * given button and combo box.
//		 */
//		private ReviewerAction(final JButton the_button, final JComboBox the_combo_box, 
//			final Paper the_paper)
//		{
//			super();
//			my_button = the_button;
//			my_combo_box = the_combo_box;
//			my_paper = the_paper;
//		}
//
//		/**
//		 * Overrides the implemented interface method.
//		 * 
//		 * @param the_event the Object that fired the event
//		 */
//		@Override
//		public void actionPerformed(final ActionEvent the_event) 
//		{
//			final List<Review> all_reviews = my_paper.getReviews();
//			boolean new_reviewer = true;
//			
//			for (Review review : all_reviews)
//			{
//				if (((User) review.getReviewer()).getID() == ((User) my_combo_box.getSelectedItem()).getID())
//				{
//					new_reviewer = false;
//				}
//			}
//			if (new_reviewer)
//			{
//				List<User> user_list = UserService.getInstance().getAllUsers();
//				final User chosen_reviewer = user_list.get(my_combo_box.getSelectedIndex());
//				((SubProgramChair) my_user).assignReviewer(chosen_reviewer, 
//					my_paper, my_user.getConference());
//			}
//			else
//			{
//				JOptionPane.showMessageDialog(null, "The Reviewer " + 
//					((User) my_combo_box.getSelectedItem()).toString() + " has already been chosen.");
//			}
//		}
//	}
//	
//	/**
//	 * Main method to test the SubPGChairView.
//	 * 
//	 * @param the_args the command-line arguments
//	 */
//	/*
//	public static void main(final String[] the_args)
//	{	
//		final Random rand = new Random(System.currentTimeMillis());
//		
//		final List<Paper> papers = new ArrayList<Paper>();
//		final User author_user = UserService.getInstance().authenticateUser("AuthorTest", "AuthorTest");
//		Author ath = new Author(author_user);
//		papers.add(new Paper());
//		
//		
//		final JFrame frame = new JFrame("SubProgram Chair");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setBackground(BACKGROUND_COLOR);
//		final SubPGChairView view = new SubPGChairView(papers);
//		frame.add(view);
//		frame.pack();
//		frame.setVisible(true);
//	}
//	*/
//	
//}
//
