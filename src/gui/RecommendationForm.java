package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

import model.Recommendation;
import model.Viewer;

public class RecommendationForm extends JFrame
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default frame width.
	 */
	private static final int WIDTH = 500;
	
	/**
	 * The default frame height.
	 */
	private static final int HEIGHT = 300;
	
	/**
	 * The default background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	
	/**
	 * Reference to the Recommendation Object.
	 */
	private Recommendation my_recommendation;
	
	/**
	 * Reference to the User
	 */
	private Viewer my_view;
	
	/**
	 * The recommendation flag for whether the given Recommendation
	 *  is pre-existing.
	 */
	private boolean my_recommendation_flag = false;
	
	/**
	 * Creates a new RecommendationForm with default parameters.
	 */
	public RecommendationForm()
	{
		super("RecommendationForm");
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		my_view = new SubPGChairView();
		my_recommendation = new Recommendation();
	}
	
	/**
	 * Creates a new RecommendationForm from the given parameters.
	 * 
	 * @param the_view the Users view
	 * @param the_recommendation the Recommendation that is populating the form
	 */
	public RecommendationForm(final Viewer the_view, final Recommendation the_recommendation)
	{
		super("RecommendationForm");
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		my_view = the_view;
		my_recommendation = the_recommendation;
		if ("".equals(the_recommendation.getComments()))
		{
			my_recommendation_flag = true;
		}
	}
	
	/**
	 * Initializes the frames components.
	 */
	public void start()
	{
		add(new JLabel("Northern Label"), BorderLayout.NORTH);
		add(new RecommendationPanel(), BorderLayout.CENTER);
		
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
				JOptionPane.showMessageDialog(null, "You have succesfully " + 
					recommendation_button.getText() + "d the Recommendation!");
				dispose();
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
		new RecommendationForm(new SubPGChairView(), new Recommendation()).start();
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
		private static final int PANEL_COLUMNS = 2;
		
		/**
		 * The default size of the text fields.
		 */
		private static final int TEXTFIELD_SIZE = 20;
		
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
		}
		
		/**
		 * Creates the panel fields.
		 */
		private void createFields()
		{
			add(new JLabel("SubProgram Chair: "));
		//	add(new JLabel(my_recommendation.getReviewer().toString()));
			add(new JLabel("SubProgramChair"));
			add(new JLabel("Rating"));
			add(new JTextField(TEXTFIELD_SIZE));
			add(new JLabel("Comments"));
			final JTextArea comment_field = new JTextArea();
			final JScrollPane comment_scroll = new JScrollPane(comment_field);
			comment_scroll.setEnabled(true);
			comment_field.setLineWrap(true);
			add(comment_scroll);
			comment_field.setText("This is text");
			System.out.println(comment_field.getText());
		}
		
	}
}