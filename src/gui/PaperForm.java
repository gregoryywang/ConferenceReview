package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Author;
import model.Paper;
import model.User;
import model.Viewer;

public class PaperForm extends JFrame 
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
	 * The default background Color.
	 */
	private static final Color BACKGROUND_COLOR = Color.ORANGE;
	
	/**
	 * Reference to the Viewer for the current User.
	 */
	private Viewer my_view;
	
	/**
	 * Reference to the PaperPanel in the Frame.
	 */
	private PaperPanel my_panel;
	
	/**
	 * Reference to the Paper that is being viewed.
	 */
	private Paper my_paper;
	
	public PaperForm(final Paper the_paper)
	{
		super("PaperForm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(BACKGROUND_COLOR);
		my_paper = the_paper;
		my_panel = new PaperPanel(the_paper);
	}
	
	/**
	 * Constructs a new PaperForm Object from the given
	 * User and Paper.
	 * 
	 * @param the_view the current Users Viewer
	 * @param the_paper the current Paper the User is viewing
	 */
	public PaperForm(final Viewer the_view, final Paper the_paper)
	{
		super("PaperForm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(BACKGROUND_COLOR);
		my_view = the_view;
		my_paper = the_paper;
		my_panel = new PaperPanel(the_paper);
	}
	
	/**
	 * Method to initialize the JFrame Components.
	 */
	public void start()
	{
		my_panel.createFields();
		add(my_panel, BorderLayout.CENTER);
		
		final JButton paper_button = new JButton("Ok");
		paper_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				dispose();
			}
		});
		final JPanel button_panel = new JPanel(new FlowLayout());
		button_panel.add(paper_button);
		add(button_panel, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	public static void main(final String[] the_args)
	{
		
		//final User the_author, final String the_title, final String the_keywords,
		//final String the_abstract, final String the_category, final String content
		new PaperForm(new Paper(new Author(new User()), "The paper title", 
			"THe paper keywords", "the paper abstract the paper abstract " +
			"the paper abstract the paper abstract", "the Paper categories", "the content")).start();
	}
	
	private class PaperPanel extends JPanel
	{
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The default number of rows in the PaperPanel.
		 */
		private static final int PANEL_ROWS = 0;
		
		/**
		 * The default number of columns in the PaperPanel.
		 */
		private static final int PANEL_COLUMNS = 1;
		
		/**
		 * The default text field size.
		 */
		private static final int TEXTFIELD_SIZE = 10;
		
		/**
		 * Test array for keywords_field.
		 */
		private final String[] KEYWORDS = {"Apples", "Oranges", "Pomegranate", "Kiwis"};
		
		/**
		 * Test array for categories_field.
		 */
		private final String[] CATEGORIES = {"FRUITS","THINGS YOU EAT", "FINGERFOODS", "THINGS THAT ARE ROUND"};
		
		/**
		 * Test array for recommendation_field.
		 */
		private final String RECOMMENDATION = "DON'T QUIT YOUR DAY JOB!";
		
		/**
		 * Test array for reviews_field.
		 */
		private final String[] REVIEWS = {"Reviewer 1 says: Tie My Shoe!", "Reviewer 2 says: I am a Knight", "Reviewer 3 says: I am a Knave"};
		
		/**
		 * Reference to the Paper being displayed in the PaperPanel.
		 */
		private Paper my_paper;
		
		/**
		 * Reference to all the fields in the PaperPanel.
		 */
		private List<JComponent> my_paper_fields;
		
		/**
		 * Constructs a new PaperPanel from the given Paper.
		 * 
		 * @param the_paper the Paper to be displayed in the panel
		 */
		private PaperPanel(final Paper the_paper)
		{
			super(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
			my_paper = the_paper;
			my_paper_fields = new ArrayList<JComponent>();
		}
		
		/**
		 * Creates the JComponents that go in the PaperPanel.
		 */
		public void createFields()
		{
			final JPanel title_panel = new JPanel();
			title_panel.add(new JLabel("Title"));
			add(title_panel);
			final JTextField title_field = new JTextField(TEXTFIELD_SIZE);
			title_field.setText(my_paper.getTitle());
			title_field.setEditable(true);
			my_paper_fields.add(title_field);
			add(title_field);
			
			final JPanel keywords_panel = new JPanel();
			keywords_panel.add(new JLabel("Keywords"));
			add(keywords_panel);
			final JComboBox keywords_field = new JComboBox(KEYWORDS);
			// This is supposed to return an String[] but doesn't
			// Also I think this should be something for PaperService.getInstance().getKeywords(the_paper);
			// keywords_field.setModel(new DefaultComboBoxModel(my_paper.getKeywords());
			keywords_field.setEditable(false);
			my_paper_fields.add(keywords_field);
			add(keywords_field);
			
			final JPanel categories_panel = new JPanel();
			categories_panel.add(new JLabel("Categories"));
			add(categories_panel);
			final JComboBox categories_field = new JComboBox(CATEGORIES);
//			categories_field.setModel(new DefaultComboBoxModel(
//				ConferenceService.getInstance().getCategories().toArray()));
			categories_field.setEditable(false);
			my_paper_fields.add(categories_field);
			add(categories_field);
			
			final JPanel abstract_panel = new JPanel();
			abstract_panel.add(new JLabel("Abstract"));
			add(abstract_panel);
			final JTextArea document_field = new JTextArea(my_paper.getAbstract());
			document_field.setLineWrap(true);
			document_field.setEditable(false);
			my_paper_fields.add(document_field);
			add(document_field);
			
			final JPanel recommendation_panel = new JPanel();
			recommendation_panel.add(new JLabel("Recommendation"));
			add(recommendation_panel);
			final JTextField recommendation_field = new JTextField(TEXTFIELD_SIZE);
			recommendation_field.setText(RECOMMENDATION);
			recommendation_field.setEditable(false);
			my_paper_fields.add(recommendation_field);
			add(recommendation_field);
			
			final JPanel review_panel = new JPanel();
			review_panel.add(new JLabel("Reviews"));
			add(review_panel);
			final JComboBox review_field = new JComboBox();
			review_field.addItem("-- select a review --");
			//look into JPopupMenu
			for (int i = 0; i < REVIEWS.length; i++)
			{
				review_field.addItem(REVIEWS[i]);
			}
			review_field.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					// Test
					JOptionPane.showMessageDialog(null, review_field.getSelectedItem().toString());
					
					// Will really be pulling up a ReviewForm
				}
			});
			//review_field.setModel(new DefaultComboBoxModel(my_paper.getReviews().toArray()));
			review_field.setEditable(false);
			my_paper_fields.add(review_field);
			add(review_field);
			
			final JPanel status_panel = new JPanel();
			status_panel.add(new JLabel("Status"));
			add(status_panel);
			final JTextField status_field = new JTextField(TEXTFIELD_SIZE);
			status_field.setText(my_paper.getStatus().toString());
			status_field.setEditable(false);
			my_paper_fields.add(status_field);
			add(status_field);
		}
	}
}
