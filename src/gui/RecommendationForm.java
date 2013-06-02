package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Recommendation;
import model.User;

public class RecommendationForm extends JFrame
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default frame width.
	 */
	private static final int WIDTH = 300;
	
	/**
	 * The default frame height.
	 */
	private static final int HEIGHT = 300;
	
	/**
	 * The default background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	
	/**
	 * The default JTextArea comment.
	 */
	private static final String DEFAULT_COMMENT = "Please enter a comment here.";
	
	/**
	 * Reference to the Recommendation Object.
	 */
	private Recommendation my_recommendation;
	
	/**
	 * Reference to the panel.
	 */
	private RecommendationPanel my_panel;
	
	/**
	 * Reference to the User
	 */
	private User my_user;
	
	/**
	 * The recommendation flag for whether the given Recommendation
	 *  is pre-existing (true if pre-existing).
	 */
	private boolean my_recommendation_flag = false;
	
	/**
	 * Creates a new RecommendationForm with default parameters.
	 */
	public RecommendationForm()
	{
		super("RecommendationForm");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		my_user = new User();
		my_recommendation = new Recommendation();
	}
	
	/**
	 * Creates a new RecommendationForm from the given parameters.
	 * 
	 * @param the_view the Users view
	 * @param the_recommendation the Recommendation that is populating the form
	 */
	public RecommendationForm(final User the_user, final Recommendation the_recommendation)
	{
		super("RecommendationForm");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		my_user = the_user;
		my_recommendation = the_recommendation;
		if (!"".equals(the_recommendation.getComments()))
		{
			my_recommendation_flag = true;
		}
	}
	
	/**
	 * Initializes the frames components.
	 */
	public void start()
	{
		my_panel = new RecommendationPanel();
		
		add(my_panel, BorderLayout.CENTER);
		
		final JPanel southern_panel = new JPanel();
		
		final JButton cancel_button = new JButton("Cancel");
		cancel_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JOptionPane.showMessageDialog(null, "No changes were saved.");
				dispose();
			}
		});
		
		final JButton recommendation_button = new JButton();
		if (my_recommendation_flag)
		{
			recommendation_button.setText("Save");
		}
		else
		{
			recommendation_button.setText("Create");
		}
		recommendation_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				if (DEFAULT_COMMENT.equals(((JTextArea) my_panel.getRecommendationFields()
					.get(1)).getText()))
				{
					JOptionPane.showMessageDialog(null, "You must add a comment to the Recommendation." +
						"\n If you wish to discard the Recommendation press the \"Cancel\" button.");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You have succesfully " + 
						recommendation_button.getText() + "d the Recommendation!");
					dispose();
				}
			}
		});
		southern_panel.add(recommendation_button);
		southern_panel.add(cancel_button);
		
		add(southern_panel, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	/**
	 * Main method for testing.
	 * 
	 * @param the_args the command-line args
	 */
	public static void main(final String[] the_args)
	{
		new RecommendationForm(new User(), new Recommendation()).start();
	}
	
	/**
	 * Private class that creates a panel to display the Recommendation Object.
	 * 
	 * @author Levon
	 * @version Spring 2013
	 */
	private class RecommendationPanel extends JPanel
	{
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * The default number of rows in the panel.
		 */
		private static final int PANEL_ROWS = 0;
		
		/**
		 * The default number of columns in the panel.
		 */
		private static final int PANEL_COLUMNS = 1;
		
		/**
		 * The default size of the text fields.
		 */
		private static final int TEXTFIELD_SIZE = 10;
		
		/**
		 * Reference to all of the recommendation fields.
		 */
		private List<JComponent> my_recommendation_fields;
		
		/**
		 * Constructs a new RecommendationPanel from the
		 * Recommendation.
		 */
		private RecommendationPanel()
		{
			super(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
			my_recommendation_fields = new ArrayList<JComponent>();
			createFields();
			pack();
		}
		
		/**
		 * Creates the panel fields.
		 */
		private void createFields()
		{
			final JPanel subprogram_panel = new JPanel();
			subprogram_panel.add(new JLabel("Sub-Program Chair: "));
			add(subprogram_panel);		
			
			final JPanel user_panel = new JPanel();
			final JTextField subprogram_chair_field = new JTextField(TEXTFIELD_SIZE);
			subprogram_chair_field.setText(my_user.toString());
			subprogram_chair_field.setEditable(false);
			user_panel.add(subprogram_chair_field);
			add(user_panel);
			
			final JPanel rating_label = new JPanel();
			rating_label.add(new JLabel("Rating: "));
			add(rating_label);
			
			final JPanel rating_panel = new JPanel();
			final JComboBox rating_dropdown = new JComboBox();
			rating_dropdown.setEditable(false);
			if (my_recommendation_flag)
			{
				rating_dropdown.addItem(my_recommendation.getRating());
			}
			else
			{
				rating_dropdown.setModel(new DefaultComboBoxModel(Recommendation.
					RATING_SCALE_HIGH_TO_LOW));
			}
			my_recommendation_fields.add(rating_dropdown);
			rating_panel.add(rating_dropdown);
			add(rating_panel);
			
			final JPanel comments_label = new JPanel();
			comments_label.add(new JLabel("Comments"));
			add(comments_label);
			
			final JPanel comments_panel = new JPanel(new BorderLayout());
			final JTextArea comment_field = new JTextArea();
			my_recommendation_fields.add(comment_field);
			final JScrollPane comment_scroll = new JScrollPane(comment_field);
			comment_field.setMargin(new Insets(10, 10, 10, 10));
			comment_field.setColumns(15);
			comment_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			comment_field.setLineWrap(true);
			comment_field.setWrapStyleWord(true);
			comment_field.setText(DEFAULT_COMMENT);
	    	comments_panel.add(comment_scroll, BorderLayout.CENTER);
	    	add(comments_panel);
		}
		
		private List<JComponent> getRecommendationFields()
		{
			return my_recommendation_fields;
		}
	}
}

