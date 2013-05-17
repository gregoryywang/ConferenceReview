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


public class HeaderView extends JPanel {
	private JComboBox role_selector = new JComboBox();
	/**
	 * Create the panel.
	 */
	public HeaderView(User the_user) 
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel, BorderLayout.SOUTH);
		
		panel.add(new JLabel("Conference"));
		
		JComboBox conference_selector = makeConferenceSelector(the_user);
		panel.add(conference_selector);
		
		panel.add(new JLabel("Role"));
		
		JComboBox role_selector = makeRoleSelector();
		panel.add(role_selector);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
		panel_1.add(new JLabel("Welcome " + the_user.getFullName()));
		
		//add logout button???
	}

	private JComboBox makeRoleSelector() {
		role_selector.setEditable(false);
		//role_selector.actionPerformed(a);
		//change user's role
		return role_selector;
	}

	private JComboBox makeConferenceSelector(final User the_user) {
		final UserDAO user_dao = new UserDAO();
		final ConferenceDAO conf_DAO = new ConferenceDAO();
		
		//used for testing
		List<ReferenceObject> ro = new ArrayList<ReferenceObject>();
		Conference c1 = new Conference();
		c1.setTopic("Trees are GREAT!");
		Conference c2 = new Conference();
		c2.setTopic("The Wonderful World of Slugs!");
		ro.add(new ReferenceObject(c1.getTopic(), c1));
		ro.add(new ReferenceObject(c2.getTopic(), c2));
		
		//end of testing
		
		//List<ReferenceObject> ro = conf_DAO.getConferencesRef();
		//needs a cast!
		ComboBoxModel conference_model = new DefaultComboBoxModel((ro.toArray()));
		JComboBox conference_selector = new JComboBox(conference_model);
		conference_selector.setModel(conference_model);
		conference_selector.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JComboBox jcb = (JComboBox) the_event.getSource();
				//ReferenceObject conference_name = (ReferenceObject) jcb.getSelectedItem();
				//int conf_id = (int) conference_name.getValue(conference_name);
				
				//List<ReferenceObject> ro_role = user_DAO.getRolesRef();
				//ComboBoxModel role_model = new DefaultComboBox(ro_role.toArray[]);
				//role_selector.setModel(role_model);
			}
		});
		return conference_selector;
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

