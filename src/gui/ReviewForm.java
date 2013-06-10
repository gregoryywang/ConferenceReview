package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import model.Paper;
import model.Review;
import model.Reviewer;
import model.User;
import service.PaperService;
import controller.Controller;
import controller.ReviewerViewController;

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
	 * The default instructions panel width.
	 */
	private static final int INSTRUCTIONS_WIDTH = 650;
	
	/**
	 * The default instructions panel height.
	 */
	private static final int INSTRUCTIONS_HEIGHT = 135;
	
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
	private static final int FRAME_WIDTH = 1000;
	
	/**
	 * The default frame height.
	 */
	private static final int FRAME_HEIGHT = 350;
	
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
	
	private Controller my_controller;
	
	/**
	 * TEST CONSTRUCTOR.
	 */
	public ReviewForm()
	{
		super("Review Form");
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		my_user = new Reviewer(new User("TEST", "TEST", "TEST", "TEST", "TEST"));
		
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
	
	public ReviewForm(final Paper the_paper, final User the_user, final Review the_review, final ReviewerViewController the_controller)
	{
		this(the_paper, the_user, the_review);
		my_controller = the_controller;
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
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		
		my_panel.add(createNorthernPanel(), BorderLayout.NORTH);
		my_panel.add(createCentralPanel(), BorderLayout.CENTER);
		my_panel.add(createSouthernPanel(), BorderLayout.SOUTH);
		
		final JScrollPane review_scrollbar = new JScrollPane(my_panel);
		review_scrollbar.setViewportView(my_panel);
		review_scrollbar.setWheelScrollingEnabled(true);
		review_scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		final JTabbedPane tabbed_pane = new JTabbedPane();
		final JPanel subprogram_panel;
		
		if (my_is_author_flag)
		{
			tabbed_pane.addTab("Review", review_scrollbar);
		}
		else
		{
			if (my_is_reviewer_flag)
			{
				tabbed_pane.addTab("Instructions", createInstructionsTab());
			}
			
			subprogram_panel = new SubProgramChairPanel();
			tabbed_pane.addTab("Review", review_scrollbar);
			tabbed_pane.addTab("Comment to SubProgramChair", subprogram_panel);
		}
		add(tabbed_pane);
		
		pack();
		setVisible(true);
	}
	
	/**
	 * Method to create an instructions panel for the tabbed pane.
	 * 
	 * @return returns a panel to be displayed in the
	 * tabbed pane
	 */
	private JPanel createInstructionsTab()
	{
		final JPanel instructions_panel = new JPanel();
		
		final JTextArea text_area = new JTextArea();
		text_area.setText(Review.INSTRUCTIONS);
		text_area.setEditable(false);
		text_area.setPreferredSize(new Dimension(
			INSTRUCTIONS_WIDTH, INSTRUCTIONS_HEIGHT));
		text_area.setWrapStyleWord(true);
		text_area.setLineWrap(true);
		text_area.setMargin(new Insets(10, 10, 10, 10));
		final JScrollPane scrollpane = new JScrollPane(text_area);
		instructions_panel.add(scrollpane);
		
		return instructions_panel;
	}
	
	/**
	 * Method to create the southern part of the review tab.
	 * 
	 * @return returns a panel to be displayed in the southern
	 * part of the review tab.
	 */
	private JPanel createSouthernPanel()
	{
		final JPanel southern_panel = new JPanel();
		final JButton southern_button = new JButton();
		southern_panel.add(southern_button);
		if (my_is_reviewer_flag)
		{
			southern_button.setText("Close");
			final JButton review_button = new JButton();
			review_button.setText("Submit Review");
			review_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					if (my_reviewer_panel.isValidReviewFields())
					{
						my_reviewer_panel.parseData();
						PaperService.getInstance().addReview(my_review, my_paper);
						JOptionPane.showMessageDialog(null, "You have successfully submitted the Review.");
						if(my_controller != null)
						{
							my_controller.actionPerformed(the_event);
						}
						
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter " +
							"a valid comment for the Paper or press the " +
							"\"Cancel\" button to discard changes.");
					}
				}
			});
			if(my_is_new_review_flag)
			{
				southern_panel.add(review_button);
			}
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
					//JOptionPane.showMessageDialog(null, "No changes were made.");
				}
				dispose();
			}
		});
		
		return southern_panel;
	}
	
	/**
	 * Method to create the central part of the review tab.
	 * 
	 * @return returns a panel to be displayed in the central
	 * part of the review tab.
	 */
	private JPanel createCentralPanel()
	{
		return my_reviewer_panel = new ReviewPanel();
	}
	
	/**
	 * Method to create the northern part of the review tab.
	 * 
	 * @return returns a panel to be displayed in the northern
	 * part of the review tab.
	 */
	private JPanel createNorthernPanel()
	{
		final JPanel northern_panel = new JPanel();
		northern_panel.add(new JLabel("Reviewer: "));
		if (my_is_new_review_flag)
		{
			my_review.setReviewer(my_user);
		}
		northern_panel.add(new JLabel(my_review.getReviewer().toString()));
		northern_panel.add(new JLabel("Paper: "));
		northern_panel.add(new JLabel(my_paper.getTitle()));
		
		return northern_panel;
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
		 * Reference to the collection of JTextAreas.
		 */
		private List<JTextArea> my_textareas;
		
		/**
		 * Reference to the collection of JComboBoxes.
		 */
		private List<JComboBox> my_comboboxes;
		
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
			my_comboboxes = new ArrayList<JComboBox>();
			my_textareas = new ArrayList<JTextArea>();
			createFields();
		}
		
		/**
		 * Method to fill the panel with the JComponents.
		 */
		private void createFields()
		{
			final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
			
			for (int i = 0; i < Review.QUESTIONS.length; i++)
			{
				panel.add(new JSeparator());
				final JPanel question_label = new JPanel();
				question_label.add(new JLabel("Question " + (i + 1)
					+ ": " + Review.QUESTIONS[i]));
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
								Review.RATING_SCALE_LOW_TO_HIGH));
					}
					else
					{
						question_box.addItem(Review.RATING_SCALE_LOW_TO_HIGH[0]);
						comment_field.setEditable(false);
					}
					comment_field.setText(DEFAULT_TEXT);
				}
				else
				{
					if (my_is_reviewer_flag && ((Reviewer) my_user).canAddReview())
					{
						comment_field.setEditable(true);
					}
					else
					{
						comment_field.setEditable(false);
					}
					question_box.addItem(Review.RATING_SCALE_LOW_TO_HIGH[Integer.valueOf(my_review.getRating(i+1))]);
					comment_field.setText(my_review.getComment(i+1));
					if(!my_is_new_review_flag)
					{
						comment_field.setEditable(false);
					}
				}
				
				question_box.setEditable(false);
				question_panel.add(question_box);
				panel.add(question_panel);
				
				my_comboboxes.add(question_box);
				comment_field.setMargin(new Insets(10, 10, 10, 10));
				comment_field.setLineWrap(true);
				comment_field.addFocusListener(new TextAreaListener());
				final JPanel rating_label = new JPanel();
				rating_label.add(new JLabel("Rating Comment: "));
				panel.add(rating_label);
				panel.add(comment_scroll);
				my_textareas.add(comment_field);
			}
			
			if (!my_is_author_flag)
			{
				my_summary_box = new JComboBox();
				my_summary_comment = new JTextArea();
				
				panel.add(new JSeparator());
				
				final JPanel summary_label = new JPanel();
				final JLabel label = new JLabel("Summary");
				label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
				summary_label.add(label);
				panel.add(summary_label);
				
				final JPanel summary_panel = new JPanel();
				summary_panel.add(new JLabel("Summary Rating: "));
				my_summary_box = new JComboBox();
				my_summary_box.setEditable(false);
				summary_panel.add(my_summary_box);
				panel.add(summary_panel);
				
				final JPanel comment_label = new JPanel();
				comment_label.add(new JLabel("Summary Comment: "));
				panel.add(comment_label);
				
				my_summary_comment = new JTextArea();
				if (my_review.getID() == 0)
				{
					my_summary_comment.setText(DEFAULT_TEXT);
					if (!my_is_reviewer_flag)
					{
						my_summary_comment.setEditable(false);
						my_summary_box.addItem(Review.RATING_SCALE_LOW_TO_HIGH[0]);
					}
					else
					{
						my_summary_box.setModel(new DefaultComboBoxModel(
							Review.RATING_SCALE_LOW_TO_HIGH));
					}
				}
				else
				{
					my_summary_comment.setText(my_review.getSummaryComment());
					if (!my_is_reviewer_flag)
					{
						my_summary_comment.setEditable(false);
					}
					my_summary_box.addItem(Review.RATING_SCALE_LOW_TO_HIGH[Integer.valueOf(my_review.getSummaryRating())]);
				}
				my_summary_comment.setPreferredSize(new Dimension(50, 50));
				my_summary_comment.setWrapStyleWord(true);
				my_summary_comment.setLineWrap(true);
				my_summary_comment.setMargin(new Insets(10, 10, 10, 10));
				my_summary_comment.addFocusListener(new TextAreaListener());
				final JScrollPane comment_scroll = new JScrollPane(my_summary_comment);
				panel.add(comment_scroll);
			}
			
			add(panel);
		}
		
		/**
		 * Method to parse the data in the ReviewForm fields.
		 */
		public void parseData()
		{
			for (int i = 0; i < my_comboboxes.size(); i++)
			{
				my_review.setRating(i+1, ((JComboBox) my_comboboxes.
					get(i)).getSelectedIndex());
				my_review.setComment(i+1, ((JTextArea) my_textareas.
					get(i)).getText());
			}
			if (!my_is_author_flag)
			{
				my_review.setSummaryRating(my_summary_box.getSelectedIndex());
				my_review.setSummaryComment(my_summary_comment.getText());
			}
			if (my_is_reviewer_flag)
			{
				my_review.setReviewer(my_user);
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
			
			for (JComboBox component : my_comboboxes)
			{
				if ("--select a rating--".equals(Review.RATING_SCALE_LOW_TO_HIGH[
				    ((JComboBox) component).getSelectedIndex()]))
				{
					result = false;
					break;
				}
			}
			for (JTextArea component : my_textareas)
			{
				if (DEFAULT_TEXT.equals(((JTextArea) component).getText()))
				{
					result = false;
					break;
				}
			}
			if (!my_is_author_flag)
			{
				if ("--select a rating--".equals(Review.RATING_SCALE_LOW_TO_HIGH[
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
		 * Constructs a new SubProgramChairAction for the
		 * SubProgramChair comment.
		 */
		private SubProgramChairPanel()
		{
			super();
			createFields();
		}
		
		/**
		 * Private method to create the fields for the panel.
		 */
		private void createFields()
		{
			
			final JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
			panel.setPreferredSize(new Dimension(700, 175));
			
			final JPanel reviewer_panel = new JPanel();
			reviewer_panel.add(new JLabel("SubProgramChair: " + my_user.toString()));
			panel.add(reviewer_panel);

			final JPanel comment_label = new JPanel();
			comment_label.add(new JLabel("Comment:"));
			panel.add(comment_label);
			
			final JTextArea comment_area = new JTextArea();
			if (my_review.getID() == 0)
			{
				comment_area.setText(DEFAULT_TEXT);
			}
			else
			{
				comment_area.setText(my_review.getSPChairComment());
				comment_area.setEditable(false);
			}
			comment_area.setWrapStyleWord(true);
			comment_area.setLineWrap(true);
			comment_area.setMargin(new Insets(10, 10, 10, 10));
			comment_area.addFocusListener(new FocusListener()
			{
				/**
				 * Overrides the implemented FocusListener interface method.
				 * 
				 * @param the_event the Object that fired the event
				 */
				@Override
				public void focusGained(final FocusEvent the_event) 
				{
					final JTextArea textarea = (JTextArea) the_event.getSource();
					textarea.selectAll();
				}
				
				/**
				 * Overrides the implemented FocusListener interface method.
				 * 
				 * @param the_event the Object that fired the event
				 */
				@Override
				public void focusLost(final FocusEvent the_event) 
				{
					// Do nothing!
				}
			});
			final JScrollPane comment_scroll = new JScrollPane(comment_area);
			comment_scroll.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			panel.add(comment_scroll);
			
			if (!my_is_reviewer_flag)
			{
				comment_area.setEditable(false);
			}
			add(panel);
		}
	}
	
	/**
	 * Private class to create a FocusListener
	 * for the JTextAreas.
	 */
	private class TextAreaListener implements FocusListener
	{
		/**
		 * Overrides the FocusListener interface method.
		 * 
		 * @param the_event the Object that fired the event
		 */
		@Override
		public void focusGained(final FocusEvent the_event) 
		{
			final JTextArea textarea = (JTextArea) the_event.getSource();
			if (DEFAULT_TEXT.equals(textarea.getText()))
			{
				textarea.setText("");
			}
		}
		
		/**
		 * Overrides the FocusListener interface method.
		 * 
		 * @param the_event the Object that fired the event
		 */
		@Override
		public void focusLost(final FocusEvent the_event) 
		{
			final JTextArea textarea = (JTextArea) the_event.getSource();
			if ("".equals(textarea.getText()))
			{
				textarea.setText(DEFAULT_TEXT);
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

