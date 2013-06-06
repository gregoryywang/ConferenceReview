package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.Paper;
import model.Review;
import model.Reviewer;
import model.SubProgramChair;
import model.User;
import service.PaperService;

public class ReviewForm extends JFrame
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default text area comment.
	 */
	private static final String DEFAULT_TEXT = "Enter a comment here.";
	
	/**
	 * The default number of rows in the panel.
	 */
	private static final int PANEL_ROWS = 0;
	
	/**
	 * The default number of columns in the panel.
	 */
	private static final int PANEL_COLUMNS = 1;
	
	/**
	 * The default frame width.
	 */
	private static final int FRAME_WIDTH = 900;
	
	/**
	 * The default frame height.
	 */
	private static final int FRAME_HEIGHT = 275;
	
	/**
	 * Reference to the User viewing the form.
	 */
	private User my_user;
	
	/**
	 * Reference to the Review being displayed.
	 */
	private Review my_review;
	
	/**
	 * Flag is set to true if the User is an author.
	 */
	private boolean my_is_author_flag = false;
	
	/**
	 * Flag is set to true if the User is a Reviewer.
	 */
	private boolean my_is_reviewer_flag;
	
	/**
	 * Flag is set to true if the Review is new.
	 */
	private boolean my_is_new_review_flag = false;
	
	/**
	 * Reference to the Paper that the Review belongs to.
	 */
	private Paper my_paper;
	
	/**
	 * Reference to the central panel.
	 */
	private ReviewPanel my_reviewer_panel;
	
	/**
	 * Reference to the frame panel.
	 */
	private JPanel my_panel;
	
	/**
	 * TEST CONSTRUCTOR.
	 */
	public ReviewForm()
	{
		super("Review Form");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_user = new Reviewer(new User("TEST", "TEST", "TEST", "TEST", "TEST"));
		//my_user = new SubProgramChair(new User("TEST", "TEST", "TEST", "TEST", "TEST"));
		//my_user = new Author(new User("TEST", "TEST", "TEST", "TEST", "TEST"));
		
		if ("Author".equals(my_user.getClass().getSimpleName()))
		{
			my_is_author_flag = true;
		}
		else if ("Reviewer".equals(my_user.getClass().getSimpleName()))
		{
			my_is_reviewer_flag = true;
		}
		my_review = new Review();
		my_is_new_review_flag = true;
		my_paper = new Paper();
	}
	
	/**
	 * Creates a ReviewForm from the given parameters.
	 * 
	 * @param the_paper the Paper the Review belongs to
	 * @param the_user the User that is viewing the form
	 * @param the_review the Review that is being displayed
	 */
	public ReviewForm(final Paper the_paper, final User the_user, final Review the_review)
	{
		super("Review Form");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_user = the_user;
		if ("Author".equals(my_user.getClass().getSimpleName()))
		{
			my_is_author_flag = true;
		}
		else if ("Reviewer".equals(my_user.getClass().getSimpleName()))
		{
			my_is_reviewer_flag = true;
		}
		my_review = the_review;
		if (the_review.getID() == 0)
		{
			my_is_new_review_flag = true;
		}
		my_paper = the_paper;
	}
	
	/**
	 * Method to initialize the panel components.
	 */
	public void start()
	{
		my_panel = new JPanel(new BorderLayout());
		
		final JPanel northern_panel = new JPanel();
		northern_panel.add(new JLabel("Reviewer: "));
		northern_panel.add(new JLabel(my_review.getReviewer().toString()));
		northern_panel.add(new JLabel("Paper: "));
		northern_panel.add(new JLabel(my_paper.getTitle()));
		my_panel.add(northern_panel, BorderLayout.NORTH);
		
		my_reviewer_panel = new ReviewPanel();
		my_panel.add(my_reviewer_panel, BorderLayout.CENTER);
		
		final JPanel southern_panel = new JPanel();
		final JButton southern_button = new JButton();
		southern_panel.add(southern_button);
		if (my_is_reviewer_flag)
		{
			southern_button.setText("Cancel");
			final JButton review_button = new JButton();
			if (my_is_new_review_flag)
			{
				review_button.setText("Create");
			}
			else
			{
				review_button.setText("Save");
			}
			review_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					if (my_reviewer_panel.isValidReviewFields())
					{
						my_reviewer_panel.parseData();
						PaperService.getInstance().addReview(my_review, my_paper);
						JOptionPane.showMessageDialog(null, "You have successfully " +
							review_button.getText() + "d the Comment.");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter " +
							"a valid comment for the Paper or press the " +
							"\"Cancel\" button to discard changes.");
					}
				}
			});
			southern_panel.add(review_button);
		}
		else
		{
			southern_button.setText("Ok");
		}
		
		southern_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				if ("Cancel".equals(southern_button.getText()))
				{
					JOptionPane.showMessageDialog(null, "No changes were made.");
				}
				dispose();
			}
		});
		my_panel.add(southern_panel, BorderLayout.SOUTH);
		
		final JScrollPane scrollbar = new JScrollPane(my_panel);
		scrollbar.setViewportView(my_panel);
		scrollbar.setWheelScrollingEnabled(true);
		scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		final JTabbedPane tabbed_pane = new JTabbedPane();
		tabbed_pane.addTab("Review", scrollbar);
		add(tabbed_pane);
		
		if (!my_is_author_flag)
		{
			final JPanel subprogram_chair_panel = new SubProgramChairPanel(
				"SubProgramChair".equals(my_user.getClass().getSimpleName()));
			tabbed_pane.addTab("SubProgramChair Comment", subprogram_chair_panel);
		}
		
		pack();
		setVisible(true);
	}
	
	/**
	 * Private class to create the panel that will display the
	 * Review for the Paper.
	 * 
	 * @author Levon K
	 * @version Spring 2013
	 */
	private class ReviewPanel extends JPanel
	{
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Reference to the collection of JComponents.
		 */
		private List<JComponent> my_review_fields;
		
		/**
		 * Reference to the summary combo box.
		 */
		private JComboBox my_summary_box;
		
		/**
		 * Reference to the summary text area.
		 */
		private JTextArea my_summary_comment;
		
		/**
		 * Constructs a new ReviewPanel Object.
		 */
		private ReviewPanel()
		{
			super();
			my_review_fields = new ArrayList<JComponent>();
			add(createFields());
		}
		
		/**
		 * Method to fill the panel with the JComponents.
		 * 
		 * @return returns a panel populated with the 
		 * paper information
		 */
		private JPanel createFields()
		{
			final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
			
			for (int i = 0; i < Review.QUESTIONS.length; i++)
			{
				final JPanel question_label = new JPanel();
				question_label.add(new JLabel(Review.QUESTIONS[i]));
				panel.add(question_label);
				
				final JPanel question_panel = new JPanel();
				question_panel.add(new JLabel("Rating: "));
				final JComboBox question_box = new JComboBox();
				final JTextArea comment_field = new JTextArea();
				final JScrollPane comment_scroll = new JScrollPane(comment_field);
				if (my_is_new_review_flag)
				{
					if (my_is_reviewer_flag)
					{
						comment_field.setEditable(true);
						question_box.setModel(new DefaultComboBoxModel(
								Review.RATING_SCALE_HIGH_TO_LOW));
					}
					else
					{
						question_box.addItem(Review.RATING_SCALE_HIGH_TO_LOW[0]);
						comment_field.setEditable(false);
					}
					comment_field.setText(DEFAULT_TEXT);
				}
				else
				{
					question_box.addItem(Integer.valueOf(my_review.getRating(i)));
					comment_field.setText(my_review.getComment(i));
					if (!my_is_reviewer_flag || !((Reviewer) my_user).canAddReview())
					{
						comment_field.setEditable(false);
					}
				}
				
				question_box.setEditable(false);
				question_panel.add(question_box);
				panel.add(question_panel);
				my_review_fields.add(question_box);
				comment_field.setMargin(new Insets(10, 10, 10, 10));
				comment_field.setLineWrap(true);
				final JPanel rating_label = new JPanel();
				rating_label.add(new JLabel("Rating Comment: "));
				panel.add(rating_label);
				panel.add(comment_scroll);
				my_review_fields.add(comment_field);
			}
			
			if (!my_is_author_flag)
			{
				my_summary_box = new JComboBox();
				my_summary_comment = new JTextArea();
				
				final JPanel central_panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
				final JPanel reviewer_panel = new JPanel();
				reviewer_panel.add(new JLabel("User: " + my_user.toString()));
				central_panel.add(reviewer_panel);
				
				final JPanel summary_panel = new JPanel();
				summary_panel.add(new JLabel("Summary Rating: "));
				my_summary_box = new JComboBox();
				my_summary_box.setEditable(false);
				summary_panel.add(my_summary_box);
				central_panel.add(summary_panel);
				
				final JPanel comment_label = new JPanel();
				comment_label.add(new JLabel("Summary Comment: "));
				central_panel.add(comment_label);
				
				my_summary_comment = new JTextArea();
				if (my_review.getID() == 0)
				{
					my_summary_comment.setText(DEFAULT_TEXT);
					if (!my_is_reviewer_flag)
					{
						my_summary_comment.setEditable(false);
						my_summary_box.addItem(Review.RATING_SCALE_HIGH_TO_LOW[0]);
					}
					else
					{
						my_summary_box.setModel(new DefaultComboBoxModel(
							Review.RATING_SCALE_HIGH_TO_LOW));
					}
				}
				else
				{
					my_summary_comment.setText(my_review.getSummaryComment());
					if (!my_is_reviewer_flag)
					{
						my_summary_comment.setEditable(false);
					}
					my_summary_box.addItem(Integer.valueOf(my_review.getSummaryRating()));
				}
				my_summary_comment.setPreferredSize(new Dimension(50, 50));
				my_summary_comment.setWrapStyleWord(true);
				my_summary_comment.setLineWrap(true);
				my_summary_comment.setMargin(new Insets(10, 10, 10, 10));
				final JScrollPane comment_scroll = new JScrollPane(my_summary_comment);
				central_panel.add(comment_scroll);
				panel.add(central_panel);
			}
			
			return panel;
		}
		
		/**
		 * Method to parse the data in the ReviewForm fields.
		 */
		public void parseData()
		{
			for (int i = 0; i < my_review_fields.size(); i++)
			{
				my_review.setRating(i, ((JComboBox) my_review_fields.
					get(i)).getSelectedIndex());
				i++;
				my_review.setComment(i, ((JTextArea) my_review_fields.get(i)).getText());
			}
			if (!my_is_author_flag)
			{
				my_review.setSummaryRating(my_summary_box.getSelectedIndex());
				my_review.setSummaryComment(my_summary_comment.getText());
			}
		}
		
		/**
		 * Method to check that the Reviewer entered
		 * a rating and valid comments for all questions.
		 * 
		 * @return returns true if all the comments and 
		 * ratings have been chosen
		 */
		public boolean isValidReviewFields()
		{
			boolean result = true;
			
			for (JComponent component : my_review_fields)
			{
				if ("JTextArea".equals(component.getClass().getSimpleName()) &&
					DEFAULT_TEXT.equals(((JTextArea) component).getText()))
				{
					result = false;
					break;
				}
				else if ("--select a rating--".equals(Review.RATING_SCALE_HIGH_TO_LOW[
				    ((JComboBox) component).getSelectedIndex()]))
				{
					result = false;
					break;
				}
			}
			if (!my_is_author_flag)
			{
				if ("--select a rating--".equals(Review.RATING_SCALE_HIGH_TO_LOW[
				    my_summary_box.getSelectedIndex()]) || DEFAULT_TEXT.
				    equals(my_summary_comment.getText()))
				{
				    result = false;
				}
			}
			
			return result;
		}
	}
	
	/**
	 * Private Listener class that creates a Form for the SPChair Comment.
	 */
	private class SubProgramChairPanel extends JPanel
	{	
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Flag is set to true if the User is a SubProgramChair.
		 */
		private boolean my_is_subprogram_chair_flag;

		/**
		 * Constructs a new SubProgramChairAction for the
		 * SubProgramChair comment.
		 * 
		 * @param the_flag is subprogram chair flag
		 */
		private SubProgramChairPanel(final boolean the_flag)
		{
			super(new BorderLayout());
			my_is_subprogram_chair_flag = the_flag;
			createFields();
		}
		
		private void createFields()
		{
			final JPanel central_panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
			final JPanel reviewer_panel = new JPanel();
			reviewer_panel.add(new JLabel("SubProgramChair: " + my_user.toString()));
			central_panel.add(reviewer_panel);
			
			final JPanel comment_label = new JPanel();
			comment_label.add(new JLabel("Comment: "));
			central_panel.add(comment_label);
			
			final JTextArea comment_area = new JTextArea();
			if (my_review.getID() == 0)
			{
				comment_area.setText(DEFAULT_TEXT);
			}
			else
			{
				comment_area.setText(my_review.getSPChairComment());
			}
			comment_area.setPreferredSize(new Dimension(50, 50));
			comment_area.setWrapStyleWord(true);
			comment_area.setLineWrap(true);
			comment_area.setMargin(new Insets(10, 10, 10, 10));
			final JScrollPane comment_scroll = new JScrollPane(comment_area);
			central_panel.add(comment_scroll);
			add(central_panel, BorderLayout.CENTER);
			
			if (my_is_subprogram_chair_flag)
			{
				final JPanel southern_panel = new JPanel();
				final JButton submit_button = new JButton("Submit");
				submit_button.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent the_event)
					{
						if (DEFAULT_TEXT.equals(comment_area.getText()))
						{
							JOptionPane.showMessageDialog(null, "Please enter " +
								"a valid comment for the Paper or press the " +
								"\"Cancel\" button to discard changes.");
						}
						else
						{
							my_review.setSPChairComment(comment_area.getText());
							JOptionPane.showMessageDialog(null, "You have successfully " +
								submit_button.getText() + "d the Comment.");
						}
					}
				});
				southern_panel.add(submit_button);
				add(southern_panel, BorderLayout.SOUTH);
			}
			else
			{
				comment_area.setEditable(false);
			}
		}
	}
	
	/**
	 * Method to test the ReviewForm class.
	 * 
	 * @param the_args the command-line arguments
	 */
	public static void main(final String[] the_args)
	{
		new ReviewForm().start();
	}
}
