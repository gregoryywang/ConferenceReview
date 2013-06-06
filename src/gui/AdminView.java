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
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Administrator;
import model.Conference;
import model.Paper;
import model.Review;
import model.User;
import service.ConferenceService;

/**
 * Class to create a AdministratorView Object that
 * represents what an Administrator User Interface
 * looks like.
 * 
 * @author Levon Kechichian
 * @author Daniell (refactor)
 * @version Spring 2013
 */
public class AdminView extends JPanel
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the Administrator Object
	 * associated with this View.
	 */
	private Administrator my_admin;
	
	/**
	 * Constructs an AdminView Object
	 * based on the given User.
	 * 
	 * @param the_user the Administrative User
	 */
	public AdminView(final User the_admin)
	{
		super(new BorderLayout());
		my_admin = new Administrator(the_admin);
		new ConferenceForm(my_admin, my_admin.getConference()).start();
	}
}
