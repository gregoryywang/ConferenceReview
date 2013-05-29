package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private User my_administrator;
	
	/**
	 * Creates a default AdminView Object.
	 */
	public AdminView()
	{
		this(new Administrator(0, "admin", 
				"admin", "Super User", "root", "barackobama@thepresi.dent"));
	}
	
	/**
	 * Constructs an AdminView Object
	 * based on the given User.
	 * 
	 * @param the_user the Administrative User
	 */
	public AdminView(final User the_admin)
	{
		super(new BorderLayout());
		my_administrator = new Administrator(the_admin);
		add(createPanel(), BorderLayout.CENTER);
	}
	
	/**
	 * Creates the center panel for the AdminView Frame.
	 * 
	 * @return returns the populated central panel
	 */
	private JPanel createPanel()
	{
		final JPanel panel = new JPanel(new GridLayout(0, 1));
		
		final JPanel new_conference_panel = new JPanel();
		final JButton new_conference_button = new JButton("CREATE A NEW CONFERENCE");
		new_conference_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JOptionPane.showMessageDialog(null, "So you think you can create a conference?");
				final ConferenceForm form = new ConferenceForm(my_administrator, new Conference());
				form.start();
				createPanel();
			}
		});
		new_conference_panel.add(new_conference_button);
		panel.add(new_conference_panel);
		
		final JPanel conference_panel = new JPanel();
		conference_panel.add(new JLabel("Select a Conference: "));
		final JComboBox conferences = new JComboBox(new DefaultComboBoxModel(
			ConferenceService.getInstance().getConferences().toArray()));
		conferences.setEditable(false);
		conference_panel.add(conferences);
		final JButton conference_button = new JButton("Go");
		conference_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JOptionPane.showMessageDialog(null, "Don't click me!");
				final Conference chosen_conference = (Conference) conferences.getSelectedItem();
				final ConferenceForm form = new ConferenceForm(my_administrator, chosen_conference);
				form.start();
				createPanel();
			}
		});
		conference_panel.add(conference_button);
		panel.add(conference_panel);
		
		return panel;
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
	
//	/**
//	 * Method to test the AdminView class.
//	 * 
//	 * @param the_args the command-line args
//	 */
//	public static void main(final String[] the_args)
//	{
//		final JFrame frame = new JFrame("AdminView");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		final AdminView admin_view = new AdminView();
//		frame.add(admin_view);
//		frame.pack();
//		frame.setVisible(true);
//	}
}
