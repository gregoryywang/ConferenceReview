package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Administrator;
import model.Paper;
import model.Review;
import model.User;
import model.Viewer;

import common.ReferenceObject;

import dao.ConferenceDAO;
import dao.UserDAO;

/**
 * Class to create a AdministratorView Object that
 * represents what an Administrator User Interface
 * looks like.
 * 
 * @author Levon Kechichian
 * @version 1.0
 */
public class AdminView extends JPanel implements Viewer
{
	/*
	 * depends how I know which conference I am manipulating
	 * I am assuming it is by ID but not sure how to
	 * obtain the correct conference to change its Date Object. 
	 */
	private static final int STUB_ID = 0;
	
	/**
	 * The default number of panel rows.
	 */
	private static final int PANEL_ROWS = 0;
	
	/**
	 * The defualt number of panel columns.
	 */
	private static final int PANEL_COLUMNS = 3;
	
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
	 * Reference to the ConferenceDAO Object
	 * used to manipulate the database data.
	 */
	private ConferenceDAO my_conference_dao;
	
	/**
	 * Reference to the ReferenceObject Object
	 * used to manipulate the database data.
	 */
	private ReferenceObject my_reference_dao;
	
	/**
	 * Reference to the UserDAO Object
	 * used to manipulate the database data.
	 */
	private UserDAO my_user_dao;
	
	/**
	 * Creates a default AdminView Object.
	 */
	public AdminView()
	{
		super(new BorderLayout());
		my_administrator = new Administrator(0, "admin", 
			"admin", "Super User", "root", "barackobama@thepresi.dent");
		my_conference_dao = new ConferenceDAO();
		my_reference_dao = new ReferenceObject();
		my_user_dao = new UserDAO();
	}
	
	/**
	 * Method to fill the Panel.
	 */
	public void fillPanel()
	{
		add(createNorthernPanel(), BorderLayout.NORTH);
		add(createWesternPanel(), BorderLayout.WEST);
		add(createSouthernPanel(), BorderLayout.SOUTH);
		add(createCenterPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createNorthernPanel()
	{
		final JPanel northern_panel = new JPanel(new FlowLayout());
		
		final JLabel username_label = new JLabel("Hello, Administrator");
		northern_panel.add(username_label);
		
		return northern_panel;
	}
	
	private JPanel createWesternPanel()
	{
		final JPanel western_panel = new JPanel(new GridLayout(0, 1));
		
		final JButton signout_button = new JButton("Sign Out");
		signout_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?");
				if (choice == JOptionPane.YES_OPTION)
					{
						JOptionPane.showMessageDialog(null, "Nice try!");
					}
			}
		});
		western_panel.add(signout_button);
		
		final JButton change_password_button = new JButton("Change Password");
		change_password_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					JOptionPane.showMessageDialog(null, 
						"Your password has been set to \"password123\"");
				}
			});
		western_panel.add(change_password_button);
		
		final JButton change_email_button = new JButton("Change E-Mail");
		change_email_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					JOptionPane.showMessageDialog(null,
						"Expect to hear from online advertisers =-o");
				}
			});
		western_panel.add(change_email_button);
		
		final JButton etc_button = new JButton("etc..");
		etc_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					JOptionPane.showMessageDialog(null, "Hey!!! Don't Click that!!!!");
				}
			});
		western_panel.add(etc_button);
		
		return western_panel;
	}
	
	private JPanel createSouthernPanel()
	{
		final JPanel southern_panel = new JPanel(new FlowLayout());
		
		southern_panel.add(new JButton("Why am I here?"));
		
		return southern_panel;
	}
	
	private JPanel createCenterPanel()
	{
		final JPanel center_panel = new JPanel();
		
		center_panel.add(new JLabel("\t Conferences "));
		final JComboBox conference_combo_box = new JComboBox(
			my_conference_dao.getConferencesRef().toArray());
		conference_combo_box.addItem("NEW CONFERENCE");
		center_panel.add(conference_combo_box);
		final JButton button = new JButton("Go");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				createConferenceForm();
			}
		});
		center_panel.add(button);
		
		return center_panel;
	}
	
	
	/**
	 * Method to create a ConferenceForm Object
	 * based on what conference they chose
	 * in the JComboBox.
	 */
	public void createConferenceForm()
	{
		new ConferenceForm(this).start();
	}
	
	/**
	 * Method to get the Administrator Object associated
	 * with this AdminView.
	 * 
	 * @return returns the Administrator Object
	 */
	public User getAdmin()
	{
		return my_administrator;
	}
	
	/**
	 * Method to get the ConferenceDAO Object associated
	 * with this AdminView.
	 * 
	 * @return returns the ConferenceDAO Object
	 */
	public ConferenceDAO getConferenceDAO()
	{
		return my_conference_dao;
	}
	
	/**
	 * Method to get the ConferenceDAO Object associated
	 * with this AdminView.
	 * 
	 * @return returns the ConferenceDAO Object
	 */
	public UserDAO getUserDAO()
	{
		return my_user_dao;
	}
	
	/**
	 * Method to get the ReferenceDAO Object associated
	 * with this AdminView
	 * @return
	 */
	public ReferenceObject getReferenceDAO()
	{
		return my_reference_dao;
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
	//	return new ArrayList<Paper>(((ConferenceDAO) my_administrator.getConferenceDAO())
	//		.getConferencesRef().get(STUB_ID).getPapers());
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
	  //return new ArrayList<Review>(((ConferenceDAO)my_administrator.getConferenceDAO())
		//	.getConferences().get(STUB_ID).getPapers().get(STUB_ID).getReviews());
	}
	
	public static void main(String[] args)
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
