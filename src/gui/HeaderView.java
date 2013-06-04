package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Constructor;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Role;
import model.User;
import service.ConferenceService;
import service.UserService;

/**
 * 
 * @author Danielle Tucker
 * @version 2013 May
 */
public class HeaderView extends JPanel 
{

	private static final long serialVersionUID = 1L;

	private final JComboBox role_selector = new JComboBox();
	private final JComboBox conference_selector = new JComboBox();
	private User my_user;
	private boolean is_admin = false;

	private JLabel my_welcome_msg;

	/**
	 * Create the panel.
	 */
	public HeaderView(User the_user) 
	{
		if(the_user != null)
		{ 
			my_user = the_user;
			is_admin = UserService.getInstance().isAdmin(my_user.getID());
			if(is_admin)
			{
				my_user.setRole(Role.ADMIN);
			}
		}
		else
		{
			my_user = new User();
			my_user.setFirstName("WARNING:NULL USER PASSED TO HEADERVIEW");
			my_user.setLastName("");
		}
		setLayout(new BorderLayout(0, 0));

		JPanel panel_selection = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_selection.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel_selection, BorderLayout.SOUTH);

		panel_selection.add(new JLabel("Conference"));
		makeConferenceSelector();
		panel_selection.add(conference_selector);

		panel_selection.add(new JLabel("Role"));
		role_selector.setEditable(false);
		role_selector.setEnabled(false);
		role_selector.addItem(Role.USER);
		panel_selection.add(role_selector);

		JButton button_go = makeGoButton();
		panel_selection.add(button_go);


		//EAST "Welcome Full Name"
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		my_welcome_msg = new JLabel("Welcome " + my_user.getFullName());
		panel_1.add(my_welcome_msg);

	}
	
	public void setUser(User the_user)
	{

			my_user = the_user;
			my_welcome_msg.setText("Welcome " + my_user.getFullName());
			is_admin = UserService.getInstance().isAdmin(my_user.getID());
			if(is_admin)
			{
				my_user.setRole(Role.ADMIN);
			}
			makeConferenceSelector();		
			role_selector.setEditable(false);
			role_selector.setEnabled(false);
			role_selector.removeAllItems();
			role_selector.addItem(Role.USER);
	}
	
	/**
	 * Create a button to change the user's role and conference based upon the dropdown
	 * selections.
	 * @return the button to change the user's role and conference.
	 * @author Roshun
	 */
	private JButton makeGoButton() {
		JButton button_go = new JButton("Go");
		button_go.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent the_event) 
			{
				Role role = (Role) role_selector.getSelectedItem();
				Conference conf = (Conference)conference_selector.getSelectedItem();

				//check to see if user hit button without making selection before proceeding.
				if(role != null && (Integer) conf.getID() != -1)
				{
					my_user.setConference(conf);
					my_user.setRole(role);

					try {
						//Set Main Content panel to Frame content.
						JPanel panel = null;

						//Get reference to parent frame
						MainView parent = (MainView) getTopLevelAncestor();

						//Get single arg constructor if exist
						@SuppressWarnings({"unchecked", "rawtypes"})
						Constructor constructor = role.getView().getConstructor(User.class);

						if( constructor != null ) {
							constructor.setAccessible(true);
							panel = (JPanel) constructor.newInstance(my_user);
						} else {
							panel = (JPanel) role.getView().newInstance(); //call default constructor
						}

						//set content panel to new panel
						parent.setContentPanel(panel);
					}catch( Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}

		});
		return button_go;
	}

	/**
	 * Creates the conference selector dropdown.
	 */
	private void makeConferenceSelector() 
	{	
		final List<Conference> ro = ConferenceService.getInstance().getConferences();
		final Conference instruct = new Conference();
		instruct.setID(-1);
		instruct.setTopic("--Select a Conference--");
		ro.add(0, instruct);

		if(is_admin) 
		{
			final Conference new_conf = new Conference();
			new_conf.setTopic("CREATE NEW CONFERENCE");
			new_conf.setID(0);
			ro.add(new_conf);
		}
		ComboBoxModel conference_model = new DefaultComboBoxModel((ro.toArray()));
		conference_selector.setModel(conference_model);
		conference_selector.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JComboBox jcb = (JComboBox) the_event.getSource();
				Conference conf_selected = (Conference) jcb.getSelectedItem();

				//Remove the instructions "--select a conference--"
				//ro.remove(instruct);
				//conference_selector.setModel(new DefaultComboBoxModel(ro.toArray()));
				//conference_selector.setSelectedItem(conf_selected);


				role_selector.setEnabled(true);
				List<Role> roles = UserService.getInstance().getRoles(my_user.getID(), conf_selected.getID());
				//Test if the author role is included in the result set, if not, add to result set.
				if(is_admin)
				{
					roles = new ArrayList<Role>();
					roles.add(Role.ADMIN);
				}
				else if (!roles.contains(Role.AUTHOR) && !is_admin)
				{
					roles.add(Role.AUTHOR);
				}
				ComboBoxModel role_model = new DefaultComboBoxModel(roles.toArray());
				role_selector.setModel(role_model);
			}
		});
	}
}

