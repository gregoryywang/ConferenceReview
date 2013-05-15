package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Conference;
import model.Conference.Deadline;
import model.Role;
import model.User;
import dao.ConferenceDAO;


public class HeaderView extends JPanel {
	private JLabel lblNewLabel;

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
		
		JLabel lbl_conference = new JLabel("Conference");
		panel.add(lbl_conference);
		
		JComboBox conference_selector = new JComboBox();
		conference_selector.setEditable(true);
		for(Conference conf: getAvaliableConferences())
		{
			conference_selector.addItem(conf.shortTitle());
		}
		//conference_selector.actionPerformed(e);
		//populate role_selector box
		//change user's conference
		panel.add(conference_selector);
		
		JLabel lbl_role = new JLabel("Role");
		panel.add(lbl_role);
		
		JComboBox role_selector = new JComboBox();
		role_selector.setEditable(true);
		//role_selector.actionPerformed(a);
		//change user's role
		panel.add(role_selector);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		lblNewLabel = new JLabel("Welcome " + the_user.getFullName());
		panel_1.add(lblNewLabel);
		
		//add logout button???
	}
	
	private List<Conference> getAvaliableConferences ()
	{
		ConferenceDAO conf_DAO = new ConferenceDAO();
		return new ArrayList<Conference>();
	//	return conf_DAO.getConferences();  need to return a List<Conferences> but DAO is???
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

