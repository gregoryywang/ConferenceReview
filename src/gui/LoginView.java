package gui;

/**
 * LoginView.java
 * @author yongyuwang
 * @version 5-15-0654
 * Demonstrates the utilization of inner classes to combine JFrame and
 * ActionListener
 */

import java.awt.*;
import javax.swing.*;

import model.User;
import dao.UserDAO;

import java.awt.event.*;

public class LoginView extends JDialog implements ActionListener{

	/**
	 * Default generated serial code.
	 */
	private static final long serialVersionUID = 1L;
	
	final JButton submitButton;
	final JPanel panel;
	final JLabel usernameLabel, passwordLabel;
	final JTextField  usernameField;
	final JPasswordField passwordField;
	
	private User user = null;
    
    public LoginView(MainView view, String title, boolean modal) {
        super(view, title, modal);
        usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(15);

		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(15);

		submitButton = new JButton("SUBMIT");
		submitButton.addActionListener(this);

		panel = new JPanel(new GridLayout(3,2));
		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(submitButton);

		panel.add(new JLabel(""));
		add(panel,BorderLayout.CENTER);

		setTitle("PLEASE LOGIN");
        this.getContentPane().add(panel);
        this.setSize(300, 100);
        this.setVisible(true);

    }
    
    public void actionPerformed(ActionEvent event)
	{
		// creates a UserDAO to authenticate user
		UserDAO user_dao = new UserDAO(); 
		
		// getPassword returns an array of char[], need to convert to String
		final char[] tempPassword = passwordField.getPassword();
		final String password = new String(tempPassword);
		
		// authenticates user, gets user object
		user = user_dao.authenticate(usernameField.getText(), password);
				
		// returns user if login succeeds 
		if (user != null) {
			// DEBUG
			JOptionPane.showMessageDialog(this, "Success!\nPlease delete at some point!");
		}
		else {
			// Dialog box pops up when authentication fails. 
			JOptionPane.showMessageDialog(this, "Sorry, wrong username and/or password.\n" +
					"Please try again.");
		}			
	}
	
	User getUser() {
		return user;
	}
}

/**

class LoginView extends JDialog implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	
	final JButton submitButton;
	final JPanel panel;
	final JLabel usernameLabel, passwordLabel;
	final JTextField  usernameField;
	final JPasswordField passwordField;
	
	private User user = null;

	LoginView()
	{
		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(15);

		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(15);

		submitButton = new JButton("SUBMIT");
		submitButton.addActionListener(this);

		panel = new JPanel(new GridLayout(3,2));
		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(submitButton);

		panel.add(new JLabel(""));
		add(panel,BorderLayout.CENTER);

		setTitle("PLEASE LOGIN");
		
		JDialog dialog = new JDialog();
		dialog.setContentPane(panel);
		dialog.setSize(300,100);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		// creates a UserDAO to authenticate user
		UserDAO user_dao = new UserDAO(); 
		
		// getPassword returns an array of char[], need to convert to String
		final char[] tempPassword = passwordField.getPassword();
		final String password = new String(tempPassword);
		
		// authenticates user, gets user object
		user = user_dao.authenticate(usernameField.getText(), password);
				
		// returns user if login succeeds 
		if (user != null) {
			// DEBUG
			JOptionPane.showMessageDialog(this, "Success!\nPlease delete at some point!");
		}
		else {
			// Dialog box pops up when authentication fails. 
			JOptionPane.showMessageDialog(this, "Sorry, wrong username and/or password.\n" +
					"Please try again.");
		}			
	}
	
	User getUser() {
		return user;
	}	
}

**/


