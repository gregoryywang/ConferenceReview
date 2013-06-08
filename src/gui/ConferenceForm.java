package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Administrator;
import model.Conference;
import model.Conference.Deadline;
import model.User;
import service.ConferenceService;
import service.UserService;

/**
 * Class that creates a ConferenceForm Object
 * that represents the "form" that a User
 * sees when they click on a Conference Object.
 * 
 * @author Levon Kechichian
 * @version Spring 2013
 */
public class ConferenceForm extends JFrame
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
	 * Reference to the ConferencePanel.
	 */
	private ConferencePanel my_panel;

	/**
	 * Reference to the northern label that is
	 * used for error checking.
	 */
	private JLabel my_error_label;

	/**
	 * Reference to the current conference populating the form.
	 */
	private Conference my_conference;

	/**
	 * The conference flag to see if the user selected to make a new conference.
	 */
	private boolean my_is_new_conf_flag = false;

	private Administrator the_admin;

	/**
	 * Creates a default ConferenceForm Object.
	 * 
	 * @param the_view the current User's view
	 * @param the_conference the current Conference the User is viewing
	 */
	public ConferenceForm(final User the_administrator, final Conference the_conference)
	{
		super("Conference Form");

		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBounds(100, 100, 460, 430);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		the_admin = new Administrator(the_administrator);
		my_conference = the_conference;
		if (my_conference.getID() == 0)
		{
			my_is_new_conf_flag = true;
		}
		my_panel = new ConferencePanel(my_conference);
	}

	/**
	 * Method to initialize the Panel components.
	 */
	public void start()
	{
		my_error_label = new JLabel();
		my_error_label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		final JPanel label_panel = new JPanel(new FlowLayout());
		label_panel.add(my_error_label);
		add(label_panel, BorderLayout.NORTH);

		add(my_panel, BorderLayout.CENTER);

		final String button_text;
		if (my_is_new_conf_flag)
		{
			button_text ="Create";
		}
		else
		{
			button_text = "Save";
		}
		final JButton conference_button = new JButton(button_text);
		conference_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				if (checkForValidDates())
				{
					the_admin.createConference(my_panel.parseData());
					JOptionPane.showMessageDialog(null, 
							"You have succesfully " + conference_button.getText() + "d the conference!");
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "We cannot save a this conference due to" +
							" one or more invalid dates.\nIf you wish to cancel changes, please click the " +
							"\"Cancel\" button");
				}
			}
		});

		final JButton cancel_button = new JButton("Cancel");
		cancel_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JOptionPane.showMessageDialog(null, "No changes were saved.");
				dispose();
			}
		});

		final JPanel button_panel = new JPanel();
		button_panel.add(cancel_button);
		button_panel.add(conference_button);
		add(button_panel, BorderLayout.SOUTH);
		setVisible(true);
		//pack();
	}

	/**
	 * Method to make sure that every Date in the JTextfields
	 * are valid.
	 * 
	 * @return returns true if every Date is valid
	 */
	private boolean checkForValidDates()
	{
		boolean result = true;

		for (JComponent field : my_panel.getConferenceFields())
		{
			if ("JTextField".equals(field.getClass().getSimpleName()))
			{
				if (Color.YELLOW.equals(((JTextField) field).getBackground()) ||
						((JTextField) field).getText().isEmpty())
				{
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Main method to test the class.
	 * 
	 * @param the_args the command-line args
	 */
	/*
	public static void main(final String[] the_args)
	{	
		User pgrm_chair = new User();
		pgrm_chair.setFirstName("PGCHAAAAAIIIIRRR"); //needed due to refactoring (Danielle)
		new ConferenceForm(new AdminView(), new Conference(10, new Date(System.currentTimeMillis()),
				pgrm_chair, "GOAT CHEESE", new Date(System.currentTimeMillis()), 
				new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
				new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()))).start();
	}
	 */

	public class ConferencePanel extends JPanel
	{
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The number of text field rows.
		 */
		private static final int ROWS = 0;

		/**
		 * The number of text field columns.
		 */
		private static final int COLUMNS = 2;

		/**
		 * The default size of the JTextField columns.
		 */
		private static final int TEXTFIELD_SIZE = 10;

		/**
		 * Array of all JComponents in the ConferencePanel
		 * that will be used to create a Conference Object.
		 */
		private List<JComponent> my_conference_fields = new ArrayList<JComponent>();

		/**
		 * Constructs a default ConferencePanel Object.
		 */
		public ConferencePanel(final Conference the_conference)
		{
			//super(new GridLayout(ROWS, COLUMNS));
			super();
			setBorder(new EmptyBorder(5, 5, 5, 5));
			setBounds(100, 100, 600, 444);

			createFields();			
			
			setVisible(true);
		}

		public List<JComponent> getConferenceFields()
		{
			return my_conference_fields;
		}

		/**
		 * 
		 * @return a Conference with the fields from the form.
		 * @author Levon
		 * @author Danielle (Edits: parsing date to sql.date, set program chair,  setting categories)
		 */
		private Conference parseData()
		{
			int fields_index = 0;

			my_conference.setTopic(((JTextField) my_conference_fields.get(fields_index++)).getText());
			String date = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDate(Date.valueOf(date));

			if(my_is_new_conf_flag)
			{
				my_conference.setProgramChair((User) ((JComboBox) my_conference_fields.get(fields_index++)).
						getSelectedItem());
			}
			else
			{
				fields_index++;
			}

			String submission = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.SUBMIT_PAPER, Date.valueOf(submission));

			String review = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.REVIEW_PAPER, Date.valueOf(review));		

			String recommendation = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.MAKE_RECOMMENDATION, Date.valueOf(recommendation));

			String final_decision = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.FINAL_DECISION, Date.valueOf(final_decision));

			String revision = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.REVISE_PAPER, Date.valueOf(revision));

			//Get the list of categories and set them in the conference.
			if(my_is_new_conf_flag)
			{
				Object[] categories = ((JList) my_conference_fields.get(fields_index)).getSelectedValues();
				List<String> category_list = new ArrayList<String>();
				for(Object category: categories)
				{
					category_list.add((String) category);
				}
				my_conference.setCategories(category_list);
			}

			return my_conference;
		}

		/**
		 * Private method to create the JPanel text fields and drop downs.
		 */
		private void createFields()
		{
			setLayout(null);
			

			//add(new JLabel("\t Conference Topic: "));
			JLabel lblNewLabel = new JLabel("Conference Topic: ");
			lblNewLabel.setBounds(10, 30, 240, 20);
			add(lblNewLabel);

			//final JTextField topic_field = new JTextField(TEXTFIELD_SIZE);
			final JTextField topic_field = new JTextField();
			topic_field.setBounds(260, 27, 173, 20);
			add(topic_field);

			topic_field.setEditable(true);
			if(!my_is_new_conf_flag)
			{
				topic_field.setText(my_conference.getTopic().toString());
				topic_field.setCaretPosition(0);
			}
			my_conference_fields.add(topic_field);
			add(topic_field);

			//add(new JLabel("\t Conference Date (YYYY-MM-DD): "));
			JLabel lblConferenceDateyymmdd = new JLabel("Conference Date (YYYY-MM-DD):");
			lblConferenceDateyymmdd.setBounds(10, 61, 240, 20);
			add(lblConferenceDateyymmdd);

			final JTextField conference_date_field = new JTextField(TEXTFIELD_SIZE);
			conference_date_field.setBounds(260, 61, 173, 20);
			conference_date_field.setEditable(true);
			conference_date_field.addFocusListener(new DateListener((JTextField) conference_date_field));
			if(!my_is_new_conf_flag)
			{
				conference_date_field.setText(my_conference.getDate().toString());
			}
			my_conference_fields.add(conference_date_field);
			add(conference_date_field);

			
			//add(new JLabel("\t Program Chair: "));
			JLabel lblProgramChair = new JLabel("Program Chair:");
			lblProgramChair.setBounds(10, 86, 240, 20);
			add(lblProgramChair);


			if (my_is_new_conf_flag)
			{
				final JComboBox program_chair_field = new JComboBox();
				program_chair_field.setEditable(false);
				program_chair_field.setModel(new DefaultComboBoxModel(
						UserService.getInstance().getAllUsers().toArray()));
				program_chair_field.setBounds(260, 86, 173, 20);
				my_conference_fields.add(program_chair_field);
				add(program_chair_field);
			}
			else
			{
				final JLabel program_chair_field = new JLabel(my_conference.getProgramChair().toString());
				//program_chair_field.addItem(my_conference.getProgramChair());
				my_conference_fields.add(program_chair_field);
				program_chair_field.setBounds(260, 86, 173, 20);
				add(program_chair_field);
			}

			//add(new JLabel("\t Submission Deadline (YYYY-MM-DD): "));
			JLabel lblSubmissionDeadlineyyyymmdd = new JLabel("Submission Deadline (YYYY-MM-DD):");
			lblSubmissionDeadlineyyyymmdd.setBounds(10, 111, 240, 20);
			add(lblSubmissionDeadlineyyyymmdd);

			final JTextField submission_field = new JTextField(TEXTFIELD_SIZE);
			submission_field.setEditable(true);
			submission_field.setBounds(260, 111, 173, 20);
			submission_field.addFocusListener(new DateListener((JTextField) submission_field));
			if(!my_is_new_conf_flag)
			{
				submission_field.setText(my_conference.getDeadline(Deadline.SUBMIT_PAPER).toString());
			}
			my_conference_fields.add(submission_field);
			add(submission_field);

			
			
			//add(new JLabel("\t Review Deadline (YYYY-MM-DD): "));
			JLabel lblReviewDeadlineyyyymmdd = new JLabel("Review Deadline (YYYY-MM-DD):");
			lblReviewDeadlineyyyymmdd.setBounds(10, 136, 240, 20);
			add(lblReviewDeadlineyyyymmdd);

			final JTextField review_field = new JTextField(TEXTFIELD_SIZE);
			review_field.setEditable(true);
			review_field.addFocusListener(new DateListener((JTextField) review_field));
			if(!my_is_new_conf_flag)
			{
				review_field.setText(my_conference.getDeadline(Deadline.REVIEW_PAPER).toString());
			}
			my_conference_fields.add(review_field);
			review_field.setBounds(260, 136, 173, 20);
			add(review_field);

			//add(new JLabel("\t Recommendation Deadline (YYYY-MM-DD): "));
			JLabel lblRecommendationDeadlineyyyymmdd = new JLabel("Recommendation Deadline (YYYY-MM-DD):");
			lblRecommendationDeadlineyyyymmdd.setBounds(10, 161, 240, 20);
			add(lblRecommendationDeadlineyyyymmdd);
			
			final JTextField recommendation_field = new JTextField(TEXTFIELD_SIZE);
			recommendation_field.setEditable(true);
			recommendation_field.addFocusListener(new DateListener((JTextField) recommendation_field));
			if(!my_is_new_conf_flag)
			{
				recommendation_field.setText(my_conference.getDeadline(Deadline.MAKE_RECOMMENDATION).toString());
			}
			my_conference_fields.add(recommendation_field);
			recommendation_field.setBounds(260, 161, 173, 20);
			add(recommendation_field);

			//add(new JLabel("\t Final Decision Deadline (YYYY-MM-DD): "));
			JLabel lblFinalDecisionDeadline = new JLabel("Final Decision Deadline (YYYY-MM-DD):");
			lblFinalDecisionDeadline.setBounds(10, 189, 240, 20);
			add(lblFinalDecisionDeadline);

			final JTextField final_decision_field = new JTextField(TEXTFIELD_SIZE);
			final_decision_field.setEditable(true);
			final_decision_field.addFocusListener(new DateListener((JTextField) final_decision_field));
			if(!my_is_new_conf_flag)
			{
				final_decision_field.setText(my_conference.getDeadline(Deadline.FINAL_DECISION).toString());
			}
			my_conference_fields.add(final_decision_field);
			final_decision_field.setBounds(260, 189, 173, 20);
			add(final_decision_field);

			//add(new JLabel("\t Revision Deadline (YYYY-MM-DD): "));
			JLabel lblRevisionDeadlineyyyymmdd = new JLabel("Revision Deadline (YYYY-MM-DD):");
			lblRevisionDeadlineyyyymmdd.setBounds(10, 220, 240, 20);
			add(lblRevisionDeadlineyyyymmdd);

			final JTextField revision_field = new JTextField(TEXTFIELD_SIZE);
			revision_field.setEditable(true);
			revision_field.addFocusListener(new DateListener((JTextField) revision_field));
			if(!my_is_new_conf_flag)
			{
				revision_field.setText(my_conference.getDeadline(Deadline.REVISE_PAPER).toString());
			}
			my_conference_fields.add(revision_field);
			revision_field.setBounds(260, 220, 173, 20);
			add(revision_field);

			//add(new JLabel("\t Conference Categories:"));
			JLabel lblConferenceCategories = new JLabel("Conference Categories:");
			lblConferenceCategories.setBounds(10, 260, 240, 20);
			add(lblConferenceCategories);
			
			final JList categories_field = new JList();
			//categories_field.setEditable(true);
			if (my_is_new_conf_flag)
			{
				categories_field.setModel(new DefaultComboBoxModel(
						ConferenceService.getInstance().getCategories().toArray()));
			}
			else
			{
				categories_field.setModel(new DefaultComboBoxModel(my_conference.getCategories().toArray()));
			}
			my_conference_fields.add(categories_field);
			JScrollPane categories_view = new JScrollPane(categories_field);
			//categories_field.setVisibleRowCount(1);
			categories_view.setBounds(260, 245, 173, 80);
			add(categories_view);
		}
	}

	/**
	 * Private class that listens for FocusEvents
	 * 
	 * @author Levon
	 * @version Spring 2013
	 */
	private class DateListener implements FocusListener
	{
		/**
		 * The default Color Object for a valid Date.
		 */
		private final Color VALID_COLOR = Color.WHITE;

		/**
		 * The default Color Object for an invalid Date.
		 */
		private final Color INVALID_COLOR = Color.yellow;

		/**
		 * Reference to the JTextField component
		 * that we want to listen to.
		 */
		private final JTextField my_component;

		public DateListener(final JTextField the_component)
		{
			my_component = the_component;
		}

		/**
		 * Overrides the implemented FocusListener method.
		 * 
		 * @param the_event the Object that fired the FocusEvent
		 */
		@Override
		public void focusGained(final FocusEvent the_event) 
		{
			if (!my_component.getText().isEmpty())
			{
				if (!isValidDate(my_component.getText()))
				{
					my_error_label.setText("Invalid Format! Please use the form (YYYY-MM-DD)");
					my_component.setBackground(INVALID_COLOR);
				}
				else
				{
					my_error_label.setText("");
					my_component.setBackground(VALID_COLOR);
				}
			}
		}

		/**
		 * Overrides the implemented FocusListener method.
		 * 
		 * @param the_event the Object that fired the FocusEvent
		 */
		@Override
		public void focusLost(final FocusEvent the_event)
		{
			if (!my_component.getText().isEmpty())
			{
				//need a way to check that dates are valid against each other!!!
				if (!isValidDate(my_component.getText()))
				{
					my_error_label.setText("Invalid Format! Please use the form (YYYY-MM-DD)");
					my_component.setBackground(INVALID_COLOR);
				}
				else
				{
					my_error_label.setText("");
					my_component.setBackground(VALID_COLOR);
				}
			}
			else
			{
				my_error_label.setText("");
				my_component.setBackground(VALID_COLOR);
			}

		}

		/**
		 * Private method to check if the given date String is valid.
		 * 
		 * @param the_date the date that is being checked for
		 * validity
		 * 
		 * @return returns true if the given date is valid based
		 */
		private boolean isValidDate(final String the_date)
		{
			boolean result = false;

			int month = 0;
			int day = 0;
			int year = 0;
			try
			{
				year = Integer.parseInt(the_date.substring(0, 4));
				month = Integer.parseInt(the_date.substring(5, 7));
				day = Integer.parseInt(the_date.substring(8));
			}
			catch (final NumberFormatException the_exception)
			{
				// Do nothing
				JOptionPane.showMessageDialog(null, the_exception.getMessage());
			}

			if (month > 0 && month < 13 && day > 0 && day < 32 && year > 1776
					&& "-".equals(the_date.substring(4, 5)) && "-".equals(the_date.substring(7, 8)))
			{
				result = true;
			}

			return result;
		}
	}

}
