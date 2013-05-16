package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Role;
import model.User;
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
		role_selector.addItem(Role.AUTHOR.toString());
		//role_selector.actionPerformed(a);
		//change user's role
		return role_selector;
	}

	private JComboBox makeConferenceSelector(final User the_user) {
		final UserDAO user_dao = new UserDAO();
		final ConferenceDAO conf_DAO = new ConferenceDAO();
		
		JComboBox conference_selector = makeRoleSelector();
		HashMap<String, Conference> conferences = new HashMap<String, Conference>();
		for(Conference conf: conf_DAO.getConferences())
		{
			conferences.put(conf.shortTitle(), conf);
		}
		for(String name: conferences.keySet())
		{
			conference_selector.addItem(name);
		}
		conference_selector.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				JComboBox jcb = (JComboBox) the_event.getSource();
				String conference_name = (String) jcb.getSelectedItem();
				//need to fire off a change in the role selector menus.
				/*
				List<Role> roles = user_dao.getRoles(the_user.getID(), aConfId);
				//populate role_selector box
				//change user's conference */
			}
		});
		return conference_selector;
	}

/*	
	public static void main(String...the_args)
	{
		List<String> categories = new ArrayList<String>();
		categories.add("Cat1");
		categories.add("Cat2");
		categories.add("Cat3");
		User pguser = new User(-1, "Test", "PGUser", "testpguser", "testpsswd", "test@notvalid.com");
		Map<Conference.Deadline, Date> deadlines = new HashMap<Conference.Deadline, Date>();
//		GregorianCalendar cldr = new GregorianCalendar(2013, GregorianCalendar.AUGUST, 13);
//		cldr.set(2019, Calendar.APRIL, 23);
		deadlines.put(Deadline.SUBMIT_PAPER, new Date());
		Conference conf = new Conference(new Date(), pguser, "Conference Topic",
				categories, deadlines);
		User user = new User(-1, conf, Role.USER,"Test", "User", "testuser", "testpsswd", "test@notvalid.com");
		HeaderView hv = new HeaderView(user);
		hv.setVisible(true);
		
		JFrame frame = new JFrame();
		frame.add(hv);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
*/
}

