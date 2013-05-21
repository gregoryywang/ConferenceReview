package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Conference.Deadline;
import model.Role;
import model.User;

import common.ReferenceObject;

import dao.ConferenceDAO;
import dao.UserDAO;


public class HeaderView extends JPanel 
{
	private final UserDAO user_dao = new UserDAO();
	private final ConferenceDAO conf_dao = new ConferenceDAO();
	private final JComboBox role_selector = new JComboBox();
	private final JComboBox conference_selector = new JComboBox();
	private User my_user;
	private boolean is_admin = false;
	
	/**
	 * Create the panel.
	 */
	public HeaderView(User the_user) 
	{
		my_user = the_user;
		is_admin = user_dao.isAdmin(my_user.getID());
		
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
		role_selector.addItem(new ReferenceObject("none", "    "));
		panel_selection.add(role_selector);
		
		JButton button_go = new JButton("Go");
		panel_selection.add(button_go);
		
		
		//EAST "Welcome Full Name"
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
		panel_1.add(new JLabel("Welcome " + the_user.getFullName()));
		
		//add logout button???
	}

	private void makeConferenceSelector() {	
		//used for testing
		final List<ReferenceObject> ro = new ArrayList<ReferenceObject>();
		Conference c1 = new Conference();
		c1.setTopic("Trees are GREAT");
		c1.setID(12);
		Conference c2 = new Conference();
		c2.setTopic("The Wonderful World of Slugs");
		c2.setID(90);
		ro.add(new ReferenceObject(c1.getTopic(), c1));
		ro.add(new ReferenceObject(c2.getTopic(), c2));	
		//end of testing
		
		//List<ReferenceObject> ro = conf_dao.getConferencesRef();
		final ReferenceObject instruct = new ReferenceObject("--select a conference--", new Conference());
		ro.add(0, instruct);
		/*
		if(is_admin)  
			ro.add(0, new ReferenceObject("CREATE NEW CONFERENCE", new Conference()));
		*/
		ComboBoxModel conference_model = new DefaultComboBoxModel((ro.toArray()));
		conference_selector.setModel(conference_model);
		conference_selector.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JComboBox jcb = (JComboBox) the_event.getSource();
				ReferenceObject conference_name = (ReferenceObject) jcb.getSelectedItem();
				Conference conf_selected = (Conference) conference_name.getData();
				System.out.println(conf_selected.getID());
				
				//Remove the instructions "--select a conference--"
				ro.remove(instruct);
				conference_selector.setModel(new DefaultComboBoxModel(ro.toArray()));
				conference_selector.setSelectedItem(conference_name);
				
				
				role_selector.setEnabled(true);
				List<ReferenceObject> ro_roles;
				if(is_admin)
				{
					ro_roles = new ArrayList<ReferenceObject>();
					ro_roles.add(new ReferenceObject(Role.ADMIN.toString(), Role.ADMIN));
				}
				else
				{
					ro_roles = user_dao.getRolesRef(my_user.getID(), conf_selected.getID());
					//Test if the author role is inculded in the result set, if not, add to result set.
					boolean has_author = false;
					for(ReferenceObject roles: ro_roles)
					{
						if((Role)roles.getData()==Role.AUTHOR)
						{
							has_author = true;
						}
					}
					if (!has_author)
					{
						ro_roles.add(new ReferenceObject(Role.AUTHOR.toString(), Role.AUTHOR));
					}
					
					
					//for testing purposes
					List<ReferenceObject> ro = new ArrayList<ReferenceObject>();
					
					//end testing										
				}
				ComboBoxModel role_model = new DefaultComboBoxModel(ro_roles.toArray());
				role_selector.setModel(role_model);
				//change user's role

				//List<ReferenceObject> ro_role = user_DAO.getRolesRef();
				//ComboBoxModel role_model = new DefaultComboBox(ro_role.toArray[]);
				//role_selector.setModel(role_model);
			}
		});
	}

	public static void main(String...the_args)
	{
		List<String> categories = new ArrayList<String>();
		categories.add("Cat1");
		categories.add("Cat2");
		categories.add("Cat3");
		User pguser = new User("Test", "PGUser", "testpguser", "testpsswd", "test@notvalid.com");
		Map<Conference.Deadline, Date> deadlines = new HashMap<Conference.Deadline, Date>();
		deadlines.put(Deadline.SUBMIT_PAPER, new Date());
		Conference conf = new Conference();
		User user = new User(conf, Role.USER,"Test", "User", "testuser", "testpsswd", "test@notvalid.com");
		HeaderView hv = new HeaderView(user);
		hv.setVisible(true);
		
		JFrame frame = new JFrame();
		frame.add(hv);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

