package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Administrator;
import model.Conference;
import model.Paper;
import model.Review;
import model.User;
import model.Viewer;
import service.ConferenceService;

/**
 * Class to create a AdministratorView Object that
 * represents what an Administrator User Interface
 * looks like.
 * 
 * @author Levon Kechichian
 * @version Spring 2013
 */
public class AdminView extends JPanel implements Viewer
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the Administrator Object
	 * associated with this View.
	 */
	private Administrator my_administrator;
	
	/**
	 * Creates a default AdminView Object.
	 */
	public AdminView()
	{
		super(new BorderLayout());
		my_administrator = new Administrator(0, "admin", 
			"admin", "Super User", "root", "barackobama@thepresi.dent");
	}
	
	/**
	 * Constructs an AdminView Object
	 * based on the given User.
	 * 
	 * @param the_user the Administrative User
	 */
	public AdminView(final User the_user)
	{
		super(new BorderLayout());
		my_administrator = (Administrator) the_user;
	}
	
	/**
	 * Method to fill the JPanel.
	 */
	public void fillPanel()
	{
		add(createCenterPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * Creates the center panel for the AdminView Frame.
	 * 
	 * @return returns the populated central panel
	 */
	private JPanel createCenterPanel()
	{
		final JPanel panel = new JPanel(new GridLayout(0, 1));
		
		final JPanel new_conference_panel = new JPanel(new FlowLayout());
		final JButton new_conference_button = new JButton("CREATE NEW CONFERENCE");
		new_conference_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				final AdminView view = new AdminView();
				User pgrm_chair = new User();
				pgrm_chair.setFirstName("NEWWWWWW PGCHAAAAAIIIIRRR");
				final Conference conference = new Conference(10, new Date(System.currentTimeMillis()),
					pgrm_chair, "FInished", new Date(System.currentTimeMillis()), 
					new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
					new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
				
				final ConferenceForm form = new ConferenceForm(view, conference);
				form.start();
			}
		});
		new_conference_panel.add(new_conference_button);
		panel.add(new_conference_panel);
		
		final JPanel center_panel = new JPanel(new FlowLayout());
		center_panel.add(new JLabel("\t Conferences "));
		final JComboBox conference_combo_box = new JComboBox(
			ConferenceService.getInstance().getConferences().toArray());
		center_panel.add(conference_combo_box);
		final JButton combo_box_button = new JButton("Go");
		combo_box_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				final Conference conference_item = (Conference) ((JComboBox)
					conference_combo_box).getSelectedItem();
				createConferenceForm(ConferenceService.getInstance().
					getConference(conference_item.getID()));
			}
		});
		center_panel.add(combo_box_button);
		panel.add(center_panel);
		
		return panel;
	}
	
	/**
	 * Method to create a ConferenceForm Object
	 * based on what conference they chose
	 * in the JComboBox.
	 */
	public void createConferenceForm(final Conference the_conference)
	{
		new ConferenceForm(this, the_conference).start();
	}
	
	/**
	 * Method to get the Administrator Object associated
	 * with this AdminView.
	 * 
	 * @return returns the Administrator Object
	 */
	public User getAdministrator()
	{
		return my_administrator;
	}
	
	/**
	 * Returns a List of Paper Objects.
	 * 
	 * @return the List of Paper Objects
	 */
	@Override
	public List<Paper> viewPapers() 
	{
	  return new ArrayList<Paper>();
	}

	/**
	 * Returns a List of Review Objects.
	 * 
	 * @return the List of Review Objects
	 */
	@Override
	public List<Review> viewReviews() 
	{
	  return new ArrayList<Review>();
	}
	
	/**
	 * Method to test the AdminView class.
	 * 
	 * @param the_args the command-line args
	 */
	public static void main(final String[] the_args)
	{
		final JFrame frame = new JFrame("AdminView");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final AdminView admin_view = new AdminView();
		admin_view.fillPanel();
		frame.add(admin_view);
		frame.pack();
		frame.setVisible(true);
	}
	
}
