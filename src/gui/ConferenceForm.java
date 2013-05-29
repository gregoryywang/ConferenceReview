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
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	 * Reference to the current User Object.
	 */
	private User my_user;
	
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
	 * The conference flag is set to false if it is a new Conference.
	 */
	private boolean my_conference_flag = false;
	
	/**
	 * TEST.
	 */
	public ConferenceForm(final User the_user)
	{
		super("Conference Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_user = the_user;
		my_conference = new Conference();
		if (my_conference.getID() != 0)
		{
			my_conference_flag = true;
		}
		my_panel = new ConferencePanel(my_conference);
	}
	
	/**
	 * Creates a default ConferenceForm from the given User and Conference.
	 * 
	 * @param the_view the current User
	 * @param the_conference the current Conference the User is viewing
	 */
	public ConferenceForm(final User the_user, final Conference the_conference)
	{
		super("Conference Form");
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_user = the_user;
		my_conference = the_conference;
		System.out.println(the_conference);
		if (the_conference.getID() != 0)
		{
			my_conference_flag = true;
		}
		my_panel = new ConferencePanel(the_conference);
	}
	
	/**
	 * Method to initialize the Panel components.
	 */
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
		final JButton conference_button = new JButton(button_text);
		conference_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				if (checkForValidDates())
				{
					((Administrator) my_user).createConference(my_panel.parseData());
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
		pack();
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
	
	
//	/**
//	 * Main method to test the class.
//	 * 
//	 * @param the_args the command-line args
//	 */
//	public static void main(final String[] the_args)
//	{	
//		new ConferenceForm(new Administrator(new User(
//			"first", "last", "sn", "pwd", "email"))).start();
//	}
	
	
	private class ConferencePanel extends JPanel
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
		 * 
		 * @param the_conference the Conference to be displayed in the JPanel
		 */
		private ConferencePanel(final Conference the_conference)
		{
			super(new GridLayout(ROWS, COLUMNS));
			createFields();
			pack();
			setVisible(true);
		}
		
		/**
		 * Private method to create the JPanel text fields and drop downs.
		 */
		private void createFields()
		{
			add(new JLabel(" Conference Topic: "));
			final JTextField topic_field = new JTextField(TEXTFIELD_SIZE);
			topic_field.setEditable(true);
			topic_field.setText(my_conference.getTopic().toString());
			my_conference_fields.add(topic_field);
			add(topic_field);
			
			add(new JLabel(" Conference Date (YYYY-MM-DD): "));
			final JTextField conference_date_field = new JTextField(TEXTFIELD_SIZE);
			conference_date_field.setEditable(true);
			conference_date_field.addFocusListener(new DateListener((JTextField) conference_date_field));
			conference_date_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(conference_date_field);
			add(conference_date_field);
			
			add(new JLabel(" Program Chair: "));
			final JComboBox program_chair_field = new JComboBox();
			program_chair_field.setEditable(false);
			if (my_conference_flag)
			{
				program_chair_field.addItem(my_conference.getProgramChair());
			}
			else
			{
				program_chair_field.setModel(new DefaultComboBoxModel(
					UserService.getInstance().getAllUsers().toArray()));
			}
			my_conference_fields.add(program_chair_field);
			add(program_chair_field);
			
			add(new JLabel(" Submission Deadline (YYYY-MM-DD): "));
			final JTextField submission_field = new JTextField(TEXTFIELD_SIZE);
			submission_field.setEditable(true);
			submission_field.addFocusListener(new DateListener((JTextField) submission_field));
			submission_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(submission_field);
			add(submission_field);

			add(new JLabel(" Review Deadline (YYYY-MM-DD): "));
			final JTextField review_field = new JTextField(TEXTFIELD_SIZE);
			review_field.setEditable(true);
			review_field.addFocusListener(new DateListener((JTextField) review_field));
			review_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(review_field);
			add(review_field);
			
			add(new JLabel(" Recommendation Deadline (YYYY-MM-DD): "));
			final JTextField recommendation_field = new JTextField(TEXTFIELD_SIZE);
			recommendation_field.setEditable(true);
			recommendation_field.addFocusListener(new DateListener((JTextField) recommendation_field));
			recommendation_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(recommendation_field);
			add(recommendation_field);
			
			add(new JLabel(" Final Decision Deadline (YYYY-MM-DD): "));
			final JTextField final_decision_field = new JTextField(TEXTFIELD_SIZE);
			final_decision_field.setEditable(true);
			final_decision_field.addFocusListener(new DateListener((JTextField) final_decision_field));
			final_decision_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(final_decision_field);
			add(final_decision_field);
			
			add(new JLabel(" Revision Deadline (YYYY-MM-DD): "));
			final JTextField revision_field = new JTextField(TEXTFIELD_SIZE);
			revision_field.setEditable(true);
			revision_field.addFocusListener(new DateListener((JTextField) revision_field));
			revision_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(revision_field);
			add(revision_field);
			
			add(new JLabel(" Notification Deadline (YYYY-MM-DD): "));
			final JTextField notification_field = new JTextField(TEXTFIELD_SIZE);
			notification_field.setEditable(true);
			notification_field.addFocusListener(new DateListener((JTextField) notification_field));
			notification_field.setText(my_conference.getDate().toString());
			my_conference_fields.add(notification_field);
			add(notification_field);
			
			add(new JLabel(" Conference Categories:"));
			final JComboBox categories_field = new JComboBox();
			categories_field.setEditable(false);
			if (my_conference_flag)
			{
				// if this conference already has categories associated with it
				// then I just populate the categories drop down with the list of categories
				categories_field.setModel(new DefaultComboBoxModel(my_conference.getCategories().toArray()));
			}
			else
			{
				final List<String> categories_list = ConferenceService.getInstance().getCategories();
				Collections.sort(categories_list);
				final List<JCheckBox> checkboxes = new ArrayList<JCheckBox>(categories_list.size());
				for (int i = 0; i < categories_list.size(); i++)
				{
					checkboxes.get(i).setText(categories_list.get(i));
				}
				categories_field.setModel(new DefaultComboBoxModel(checkboxes.toArray()));
			}
			my_conference_fields.add(categories_field);
			add(categories_field);
		}
		
		public List<JComponent> getConferenceFields()
		{
			return my_conference_fields;
		}
		
		public Conference parseData()
		{
			int fields_index = 0;
			
			my_conference.setTopic(((JTextField) my_conference_fields.get(fields_index++)).getText());
			String date = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDate(new Date(Long.parseLong(date.replaceAll("-", ""))));
			my_conference.setProgramChair((User) ((JComboBox) my_conference_fields.get(fields_index++)).
				getSelectedItem());
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
			String revision = ((JTextField) my_conference_fields.get(fields_index++)).getText();
			my_conference.setDeadline(Deadline.REVISE_PAPER, new Date(Long.parseLong(
				revision.replaceAll("-", ""))));
			
			final DefaultComboBoxModel model = (DefaultComboBoxModel) ((JComboBox) my_conference_fields.
				get(my_conference_fields.size() - 1)).getModel();
			final List<String> category_list = new ArrayList<String>();
			for (int i = 0; i < model.getSize(); i++)
			{
				if (((JCheckBox) model.getElementAt(i)).isSelected())
				{
					category_list.add(((JCheckBox) model.getElementAt(i)).getText());
				}
			}
			my_conference.setCategories(category_list);
			
			return my_conference;
		}
	}
	
	/**
	 * Private class that listens for FocusEvents
	 * 
	 * @author Levon K
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
		
		/**
		 * Constructs a new DateListener for the given component.
		 * 
		 * @param the_component the component that the listener 
		 * is being attached to
		 */
		private DateListener(final JTextField the_component)
		{
			my_component = the_component;
		}
		
		/**
		 * Private method to check if the given date String is valid.
		 * 
		 * @param the_date the date that is being checked for
		 * validity
		 * @return returns true if the given date is valid
		 * based on the formatting
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
			else
			{
				if (Color.YELLOW.equals(my_component.getBackground()))
				{
					my_northern_label.setText("Invalid Format! Please use the form (YYYY-MM-DD)");
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
				my_northern_label.setText("Invalid Format! Please use the form (YYYY-MM-DD)");
				my_component.setBackground(INVALID_COLOR);
			}
		}
	}
	
}

