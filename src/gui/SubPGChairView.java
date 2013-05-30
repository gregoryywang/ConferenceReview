package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Author;
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
import service.UserService;

public class SubPGChairView extends JPanel implements Viewer
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * Reference to the SubProgramChair Object associated with this view.
	 */
	private User my_user;
	
	/**
	 * Reference to the Collection of Paper Objects associated with this User.
	 */
	private List<Paper> my_papers;

	/**
	 * Constructs a default SubPGhairView Object.
	 */
	public SubPGChairView()
	{
		super();
		my_user = new SubProgramChair(new Conference(),"first", "last", "password", "screenName", "me@apple.com");
		my_papers = new ArrayList<Paper>();
		add(createCentralPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * TEST CONSTRUCTOR.
	 */
	public SubPGChairView(final List<Paper> the_papers)
	{
		super();
		my_user = new SubProgramChair(new Conference(), "first", "last", "Pass", "name", "@me.com");
		my_papers = the_papers;
		add(createCentralPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * Constructs a SubPGChairView from a specific
	 * SubProgramChair User.
	 */
	public SubPGChairView(final User the_user)
	{
		super();
		my_user = the_user;
		my_papers = PaperService.getInstance().getAssignedPapers(the_user.getID(), 
			((SubProgramChair) the_user).getConference().getID(), Role.SUB_PROGRAM_CHAIR);
		add(createCentralPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * Private method to create and fill the central panel of the SubPGChairView.
	 * 
	 * @return returns a panel filled with components
	 */
	private JPanel createCentralPanel()
	{
		final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
		
		panel.add(createPaperPanel());
		panel.add(createReviewerPanel(my_papers.get(0)));
		// need to create something to view reviewers reviews
		panel.add(createRecommendationPanel(my_papers.get(0)));
		
		return panel;
	}
	
	/**
	 * Private method to create the recommendation portion of the panel
	 * with the given paper.
	 * 
	 * @param the_paper the paper that is populating the panel
	 * @return returns a panel that is populated by the given paper
	 */
	private JPanel createRecommendationPanel(final Paper the_paper)
	{
		final JPanel recommendation_panel = new JPanel();
		
		final JButton recommendation_button = new JButton();
		if ("".equals(the_paper.getRecommendation()))
		{
			recommendation_button.setText("View Recommendation");
		}
		else
		{
			recommendation_button.setText("Create Recommendation");
		}
		
		recommendation_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				if ("Create Recommendation".equals(recommendation_button.getText()))
				{
					new RecommendationForm(my_user, new Recommendation(my_user, 0, "")).start();
				}
				else
				{
					new RecommendationForm(my_user, the_paper.getRecommendation());
				}
			}
		});
		recommendation_panel.add(recommendation_button);
		
		return recommendation_panel;
	}
	
	/**
	 * Method to create and return a JPanel of all Papers
	 */
	private JPanel createPaperPanel()
	{
		final JPanel paper_panel = new JPanel();
		
		paper_panel.add(new JLabel("All assigned papers: "));
		if (my_papers.size() != 0)
		{
			//final JComboBox paper_combo_box = new JComboBox(my_papers.toArray());
			final JComboBox paper_combo_box = new JComboBox();
			for (Paper paper : my_papers)
			{
				paper_combo_box.addItem(paper.getTitle());
			}
			paper_combo_box.setEditable(false);
			paper_combo_box.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					createReviewerPanel(my_papers.get(paper_combo_box.getSelectedIndex()));
				}
			});
			paper_panel.add(paper_combo_box);
			final JButton paper_button = new JButton("Go");
			paper_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					final Paper chosen_paper = my_papers.get(paper_combo_box.getSelectedIndex());
					new PaperForm(my_user, chosen_paper).start();
				}
			});
			final JPanel button_panel = new JPanel();
			button_panel.add(paper_button);
			paper_panel.add(button_panel);
		}
		
		return paper_panel;
	}
	
	/**
	 * Method to update the ReviewerPanel based on the chosen paper.
	 * 
	 * @param the_paper the Paper that is populating the Reviewer drop downs
	 * @return returns a JPanel of the current Reviewers for a specified Paper
	 */
	private JPanel createReviewerPanel(final Paper the_paper)
	{
		final JPanel reviewer_panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
		
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
					viewReview(the_paper, my_user, reviewer.getReview());
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
	
//	/**
//	 * Method to create and return a JPanel of all the Reviewers
//	 * associated with a specified Paper.
//	 * 
//	 * @param the_paper the Paper that is populating the Reviewer drop downs
//	 * @return returns a JPanel of the current Reviewers for a specified Paper
//	 */
//	private JPanel createReviewerPanel(final Paper the_paper)
//	{
//		final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
//		
//		final JPanel reviewer1_panel = new JPanel();
//		reviewer1_panel.add(new JLabel("Reviewer #1: "));
//		final JComboBox reviewer1_combo_box = new JComboBox(
//			new DefaultComboBoxModel(UserService.getInstance().getAllUsers().toArray()));
//		reviewer1_combo_box.setEditable(false);
//		reviewer1_panel.add(reviewer1_combo_box);
//		final JButton reviewer1_button = new JButton("Choose Reviewer #1");
//		reviewer1_button.addActionListener(new ReviewerAction(reviewer1_button, reviewer1_combo_box, the_paper));
//		reviewer1_panel.add(reviewer1_button);
//		
//		
//		final JPanel reviewer2_panel = new JPanel();
//		reviewer2_panel.add(new JLabel("Reviewer #2: "));
//		final JComboBox reviewer2_combo_box = new JComboBox(
//			new DefaultComboBoxModel(UserService.getInstance().getAllUsers().toArray()));
//		reviewer2_combo_box.setEditable(false);
//		reviewer2_panel.add(reviewer2_combo_box);
//		final JButton reviewer2_button = new JButton("Choose Reviewer #2");
//		reviewer2_button.addActionListener(new ReviewerAction(reviewer2_button, reviewer2_combo_box, the_paper));
//		reviewer2_panel.add(reviewer2_button);
//		
//		final JPanel reviewer3_panel = new JPanel();
//		reviewer3_panel.add(new JLabel("Reviewer #3: "));
//		final JComboBox reviewer3_combo_box = new JComboBox(
//			new DefaultComboBoxModel(UserService.getInstance().getAllUsers().toArray()));
//		reviewer3_combo_box.setEditable(false);
//		reviewer3_panel.add(reviewer3_combo_box);
//		final JButton reviewer3_button = new JButton("Choose Reviewer #3");
//		reviewer3_button.addActionListener(new ReviewerAction(reviewer3_button, reviewer3_combo_box, the_paper));
//		reviewer3_panel.add(reviewer3_button);
//		
//		panel.add(reviewer1_panel);
//		panel.add(reviewer2_panel);
//		panel.add(reviewer3_panel);
//		
//		return panel;
//	}
	
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
		final List<Recommendation> all_recommendations = PaperService.getInstance().getRecommendations(the_paper.getID());
		
		for (Recommendation recommendation : all_recommendations)
		{
			// need to be able to find this Users Recommendation based on their ID
			if (my_user.getID() == recommendation.getRecommender().getID())
			{
				new RecommendationForm(my_user, recommendation).start();
			}
		}
		
		// populate RecommendationForm fields with the given recommendation.
		new RecommendationForm().start();
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
		Review user_review;
		for (Review review : all_user_reviews)
		{
			if (review.getReviewer().getID() == the_user.getID())
			{
				user_review = review;
			}
		}
		
		// use the user_review to populate the ReviewForm here
		new ReviewForm(the_paper, the_user, the_review).start();
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
		// need a PaperService.getPaper(the_paper.getID());	???
		final Paper paper = (Paper) PaperService.getInstance().getAssignedPapers
			(my_user.getID(), my_user.getConference().getID(), Role.SUB_PROGRAM_CHAIR);
		
		// populate PaperForm field with the given paper
		new PaperForm(my_user, paper).start();
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
				((SubProgramChair) my_user).assignReviewer((User) my_combo_box.getSelectedItem(), 
					my_paper, my_user.getConference());
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The Reviewer " + 
					((User) my_combo_box.getSelectedItem()).toString() + " has already been chosen.");
			}
		}
	}
	
	/**
	 * Main method to test the SubPGChairView.
	 * 
	 * @param the_args the command-line arguments
	 */
	public static void main(final String[] the_args)
	{
		// PAPER
		// final User the_author, final String the_title, final String the_keywords,
		// final String the_abstract, final String the_category, final String content
		
		// USER
		// WITH CONFERENCE
		// final Conference the_conference, final Role the_role, 
		// final String the_first_name, final String the_last_name, 
		// final String the_username, final String the_password, final String the_email
		
		// USER 
		// W / O CONFERENCE
		// final String the_first_name, final String the_last_name, 
		// final String the_username, final String the_password, final String the_email
		
		// CONFERENCE
		// final int the_conf_id, final Date the_date, final User the_PG_chair, final String the_topic,
		// final Date the_submit_paper_deadline,
		// final Date the_review_paper_deadline, final Date the_make_recommendation_deadline,
		// final Date the_final_decision_deadline, final Date the_revise_paper_deadline
		
		// DATE (sql.Date)
		// final long the_date (System.currentTimeMillis() -> Date.valueOf("yyyy-[m]m-[d]d"))
		
		final String[] names = {"joe", "bob", "cindy", "alex", "david", "ed", "francis", "gary", "hal4000"};
		final String[] sn = {"32432", "jkjkl32", "jfk3l;29", "xczca", "12", "33kjwk", "#$#@", "#J<DF*>", "_"};
		final String[] password = {"abc", "123", "pwd", "pass", "word", "jam", "ham", "fish", "taco"};
		final String[] email = {"@aol.com", "@me.com", "@microsoft.com", "@thewhitehouse.gov", "@yahoo.com", "@gmail.com", 
			"@eff.org", "@slashdot.org", "@apple.com"};
		final String[] dates = {"1900-09-01", "1901-05-05", "1986-02-01", "1999-09-09", "2000-01-01", "1999-12-31", 
			"2025-02-07", "3000-03-03", "3030-01-01"};
		final String[] titles = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
		final String[] keywords = {"apples", "bananas", "cherries", "dragonfruit", "e-fruit", "f-fruit", "guava", "h-fruit", "i-fruit"};
		final String[] abstracts = {"abstract1", "abstrac2", "abstract3", "abstract4", "abstract5", "abstract6", "abstract7", "abstract8", "abstract9"};
		final String[] categories = {"cat1", "cat2", "cat3", "cat4", "cat5", "cat6", "cat7", "cat8", "cat9"};
		final String[] contents = {"tents1", "tents2", "tents3", "tents4", "tents5", "tents6", "tents7", "tents8", "tents9"};
		
		final Random rand = new Random(System.currentTimeMillis());
		
		final List<Paper> papers = new ArrayList<Paper>();
		final User author = new User(names[Math.abs(rand.nextInt()) % names.length], names[Math.abs(rand.nextInt()) % names.length],
			sn[Math.abs(rand.nextInt()) % sn.length], password[Math.abs(rand.nextInt()) % password.length], 
			email[Math.abs(rand.nextInt()) % email.length]);
		papers.add(new Paper(new Author(author), titles[Math.abs(rand.nextInt()) % titles.length], 
			keywords[Math.abs(rand.nextInt()) % keywords.length], abstracts[Math.abs(rand.nextInt()) % abstracts.length], 
			categories[Math.abs(rand.nextInt()) % categories.length], contents[Math.abs(rand.nextInt()) % contents.length]));
		
		
		final JFrame frame = new JFrame("SubProgram Chair");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(BACKGROUND_COLOR);
		final SubPGChairView view = new SubPGChairView(papers);
		frame.add(view);
		frame.pack();
		frame.setVisible(true);
	}
	
}

