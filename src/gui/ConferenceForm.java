package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Administrator;
import model.Conference;
import model.Conference.Deadline;
import model.Viewer;
import service.ConferenceService;

/**
 * Class that creates a ConferenceForm Object
 * that represents the "form" that a User
 * sees when they click on a Conference Object.
 * 
 * @author Levon Kechichian
 * @version 1.0
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
	 * The defualt background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.GREEN;
	
	/**
	 * Reference to the current users Viewer Object.
	 */
	private Viewer my_view;
	
	/**
	 * Reference to the ConferencePanel.
	 */
	private ConferencePanel my_panel;
	
	/**
	 * Reference to the northern label that is
	 * used for error checking.
	 */
	private JLabel my_northern_label;
	
	/**
	 * Reference to the current conference populating the form.
	 */
	private Conference my_conference;
	
	/**
	 * The conference flag.
	 */
	private boolean my_conference_flag = false;
	
	/**
	 * Creates a default ConferenceForm Object.
	 */
	public ConferenceForm(final Viewer the_view, final Conference the_conference)
	{
		super("Conference Form");
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(BACKGROUND_COLOR);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_conference = the_conference;
		if (the_conference.getID() != 0)
		{
			my_conference_flag = true;
		}
		my_view = the_view;
		my_panel = new ConferencePanel(the_conference);
	}
	
	public void start()
	{
		my_northern_label = new JLabel();
		my_northern_label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		final JPanel label_panel = new JPanel(new FlowLayout());
		label_panel.add(my_northern_label);
		add(label_panel, BorderLayout.NORTH);
		
		add(my_panel, BorderLayout.CENTER);
		
		final String button_text;
		if (my_conference_flag)
		{
			button_text ="Save";
		}
		else
		{
			button_text = "Create";
		}
		final JButton button = new JButton(button_text);
		
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				((Administrator) ((AdminView) my_view).getAdministrator()).createConference(
					my_panel.parseData());
				JOptionPane.showMessageDialog(null, 
					"You have succesfully " + button.getText() + "d the conference!");
				dispose();
			}
		});
		final JPanel button_panel = new JPanel();
		button_panel.setBackground(BACKGROUND_COLOR);
		button_panel.add(button);
		add(button_panel, BorderLayout.SOUTH);
		setVisible(true);
		pack();
	}
	
	public static void main(String[] args)
	{
		final Integer[] array = {1,2,3,4,5,6,7,8,9,1,0,3,56,43,43};
		
		final String[] names = {"joe", "moe", "slow", "pro", "flow", "agro", "viagro"};
		
		final String[] categories = {"Categories Shmategories", "Thing 1", "Thing 2", "What color is an Orange?"};
		
		new ConferenceForm(new AdminView(), new Conference(10, new Date(System.currentTimeMillis()),
		"PGCHAAAAAIIIIRRR", "GOAT CHEESE", new Date(System.currentTimeMillis()), 
		new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
		new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()))).start();
	}
	
	private class ConferencePanel extends JPanel
	{
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * The number of textfield rows.
		 */
		private static final int ROWS = 0;
		
		/**
		 * The number of textfield columns.
		 */
		private static final int COLUMNS = 2;
		
		/**
		 * The default size of the JTextField columns.
		 */
		private static final int TEXTFIELD_SIZE = 10;
		
		/**
		 * A random integer array.
		 */
		private final Integer[] array = {1,2,3,4,5,6,7,8,9,1,0,3,56,43,43};
		
		/**
		 * A random name array.
		 */
		private final String[] names = {"joe", "moe", "slow", "pro", "flow", "agro", "viagro"};
		
		/**
		 * A random conference topic array.
		 */
		private final String[] categories = {"Categories Shmategories", "Thing 1", "Thing 2", "What color is an Orange?"};
		
		/**
		 * Array of all JComponents in the ConferencePanel
		 * that will be used to create a Conference Object.
		 */
		private ArrayList<JComponent> my_conference_fields = new ArrayList<JComponent>();
		
		/**
		 * Constructs a default ConferencePanel Object.
		 */
		public ConferencePanel(final Conference the_conference)
		{
			super(new GridLayout(ROWS, COLUMNS));
			createFields();
			pack();
			setVisible(true);
		}
		
		private void createFields()
		{
			add(new JLabel("\t Conference Topic: "));
			final JTextField topic_field = new JTextField(TEXTFIELD_SIZE);
			topic_field.setEditable(true);
			topic_field.setText(my_conference.getTopic().toString());
			my_conference_fields.add(topic_field);
			add(topic_field);
			
			add(new JLabel("\t Conference Date (YYYY-MM-DD): "));
			final JTextField conference_date_field = new JTextField(TEXTFIELD_SIZE);
			conference_date_field.setEditable(true);
			conference_date_field.addFocusListener(new DateListener((JTextField) conference_date_field));
			conference_date_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(conference_date_field);
			add(conference_date_field);
			
			add(new JLabel("\t Program Chair: "));
			final JComboBox program_chair_field = new JComboBox();
			program_chair_field.setEditable(false);
			if (my_conference_flag)
			{
				program_chair_field.addItem(my_conference.getPG_Chair());
			}
			else
			{
				program_chair_field.setModel(new DefaultComboBoxModel(((AdminView) my_view)
					.getUserDAO().getUsersRef().toArray()));
			}
			my_conference_fields.add(program_chair_field);
			add(program_chair_field);
			
			add(new JLabel("\t Submission Deadline (YYYY-MM-DD): "));
			final JTextField submission_field = new JTextField(TEXTFIELD_SIZE);
			submission_field.setEditable(true);
			submission_field.addFocusListener(new DateListener((JTextField) submission_field));
			submission_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(submission_field);
			add(submission_field);

			add(new JLabel("\t Review Deadline (YYYY-MM-DD): "));
			final JTextField review_field = new JTextField(TEXTFIELD_SIZE);
			review_field.setEditable(true);
			review_field.addFocusListener(new DateListener((JTextField) review_field));
			review_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(review_field);
			add(review_field);
			
			add(new JLabel("\t Recommendation Deadline (YYYY-MM-DD): "));
			final JTextField recommendation_field = new JTextField(TEXTFIELD_SIZE);
			recommendation_field.setEditable(true);
			recommendation_field.addFocusListener(new DateListener((JTextField) recommendation_field));
			recommendation_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(recommendation_field);
			add(recommendation_field);
			
			add(new JLabel("\t Final Decision Deadline (YYYY-MM-DD): "));
			final JTextField final_decision_field = new JTextField(TEXTFIELD_SIZE);
			final_decision_field.setEditable(true);
			final_decision_field.addFocusListener(new DateListener((JTextField) final_decision_field));
			final_decision_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(final_decision_field);
			add(final_decision_field);
			
			add(new JLabel("\t Revision Deadline (YYYY-MM-DD): "));
			final JTextField revision_field = new JTextField(TEXTFIELD_SIZE);
			revision_field.setEditable(true);
			revision_field.addFocusListener(new DateListener((JTextField) revision_field));
			revision_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(revision_field);
			add(revision_field);
			
			add(new JLabel("\t Notification Deadline (YYYY-MM-DD): "));
			final JTextField notification_field = new JTextField(TEXTFIELD_SIZE);
			notification_field.setEditable(true);
			notification_field.addFocusListener(new DateListener((JTextField) notification_field));
			notification_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(notification_field);
			add(notification_field);
			
			add(new JLabel("\t Conference Categories:"));
			final JComboBox categories_field = new JComboBox();
			categories_field.setEditable(true);
			if (my_conference_flag)
			{
				categories_field.addItem(ConferenceService.getInstance().getCategories().toArray());
			}
			else
			{
				categories_field.setModel(new DefaultComboBoxModel(ConferenceService.getInstance().getCategories().toArray()));
			}
			my_conference_fields.add(categories_field);
			add(categories_field);
		}
		
		public Conference parseData()
		{
			int fields_index = 0;
			
			my_conference.setTopic(((JTextField) my_conference_fields.get(fields_index++)).getText());
			String date = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDate(new Date(Long.parseLong(date.replaceAll("-", ""))));
			my_conference.set_PG_Chair(((JComboBox) my_conference_fields.get(fields_index++)).
				getSelectedItem().toString());
			String submission = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.SUBMIT_PAPER, new Date(Long.parseLong(
				submission.replaceAll("-", ""))));
			String review = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.REVIEW_PAPER, new Date(Long.parseLong(
				review.replaceAll("-", ""))));
			String recommendation = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.MAKE_RECOMMENDATION, new Date(Long.parseLong(
				recommendation.replaceAll("-", ""))));
			String final_decision = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.FINAL_DECISION, new Date(Long.parseLong(
				final_decision.replaceAll("-", ""))));
			String revision = ((JTextField) my_conference_fields.get(fields_index)).getText();
			my_conference.setDeadline(Deadline.REVISE_PAPER, new Date(Long.parseLong(
				revision.replaceAll("-", ""))));
			//should I add one for Deadline.AUTHOR_NOTIFICATION???
			//what about the list of Categories?
			
			return my_conference;
		}
	}
	
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
			catch (final Exception the_exception)
			{
				// Do nothing
			}
			
			if (month > 0 && month < 13 && day > 0 && day < 32 && year > 1776
				&& "-".equals(the_date.substring(4, 5)) && "-".equals(the_date.substring(7, 8)))
			{
				result = true;
			}
			
			return result;
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
					my_northern_label.setText("Invalid Format! Please use the form (YYYY-MM-DD)");
					my_component.setBackground(INVALID_COLOR);
				}
				else
				{
					my_northern_label.setText("");
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
				if (!isValidDate(my_component.getText()))
				{
					my_northern_label.setText("Invalid Format! Please use the form (YYYY-MM-DD)");
					my_component.setBackground(INVALID_COLOR);
				}
				else
				{
					my_northern_label.setText("");
					my_component.setBackground(VALID_COLOR);
				}
			}
			else
			{
				my_northern_label.setText("");
				my_component.setBackground(VALID_COLOR);
			}
			
		}
	}
	
}
