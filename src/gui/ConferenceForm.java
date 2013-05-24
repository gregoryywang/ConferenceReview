package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import service.ConferenceService;

import dao.CategoryDAO;

import model.Viewer;

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
	 * Reference to whether the current Viewer is an or not.
	 */
	private boolean my_admin_flag = false;
	
	/**
	 * Creates a default ConferenceForm Object.
	 */
	public ConferenceForm(final Viewer the_view)
	{
		super("Conference Form");
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_view = the_view;
		if ("AdminView".equals(my_view.getClass().getSimpleName()))
		{
			my_admin_flag = true;
		}
	}
	
	public void start()
	{
		add(new ConferencePanel(), BorderLayout.CENTER);
		
		final JButton button = new JButton("Create / Save");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JOptionPane.showMessageDialog(null, 
					"You have succesfully created / saved the conference!");
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
		new ConferenceForm(new AdminView()).start();
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
		 * A random integer array.
		 */
		private final Integer[] array = {1,2,3,4,5,6,7,8,9,1,0,3,56,43,43};
		
		/**
		 * A random name array.
		 */
		private final String[] names = {"joe", "moe", "slow", "pro", "flow", "agro", "viagro"};
		
		/**
		 * Constructs a default ConferencePanel Object.
		 */
		public ConferencePanel()
		{
			super(new GridLayout(ROWS, COLUMNS));
			setBackground(BACKGROUND_COLOR);
			addFields();
			pack();
			setVisible(true);
		}
		
		private void addFields()
		{
			add(new JLabel("\t Conference Topic: "));
			// Need to check whether or not to populate the ConferenceForm
			// with data based on whether or not the Conference is pre-existing
			final JTextField topic_field = new JTextField(20);
			topic_field.setEditable(my_admin_flag);
			add(topic_field);
			
			add(new JLabel("\t Conference Date (MM/DD/YY): "));
			final JTextField date_field = new JTextField(20);
			date_field.setEditable(my_admin_flag);
			add(date_field);
			
			add(new JLabel("\t Program Chair: "));
		//	pg_chair_panel.add(new JComboBox(((AdminView) my_view).getUserDAO().getUsersRef().toArray()));
			add(new JComboBox(names));
			
			add(new JLabel("\t Submission Deadline (MM/DD/YYYY): "));
			final JTextField submission_deadline_field = new JTextField(10);
			submission_deadline_field.setEditable(my_admin_flag);
			add(submission_deadline_field);

			add(new JLabel("\t Review Deadline (MM/DD/YYYY): "));
			final JTextField review_deadline_field = new JTextField(10);
			review_deadline_field.setEditable(my_admin_flag);
			add(review_deadline_field);
			
			add(new JLabel("\t Recommendation Deadline (MM/DD/YYYY): "));
			final JTextField recommendation_deadline_field = new JTextField(10);
			recommendation_deadline_field.setEditable(my_admin_flag);
			add(recommendation_deadline_field);
			
			add(new JLabel("\t Final Decision Deadline (MM/DD/YYYY): "));
			final JTextField final_decision_field = new JTextField(10);
			final_decision_field.setEditable(my_admin_flag);
			add(final_decision_field);
			
			add(new JLabel("\t Revision Deadline (MM/DD/YYYY): "));
			final JTextField revision_field = new JTextField(10);
			revision_field.setEditable(my_admin_flag);
			add(revision_field);
			
			add(new JLabel("\t Notification Deadline (MM/DD/YYYY): "));
			final JTextField notification_field = new JTextField(10);
			notification_field.setEditable(false);
			add(notification_field);
			
			add(new JLabel("\t Conference Categories:"));
			final JComboBox conference_categories = 
			    new JComboBox(ConferenceService.getInstance().getCategories().toArray());
			add(conference_categories);
			
		}
		
	}
	
}
