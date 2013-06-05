package gui;

/**
 * LoginView.java
 * @author yongyuwang
 * @version 5-20-1931
 * Displays Login window, authenticates user credentials and gets 
 * authenticated user.
 * Implements JDialog modal behavior.
 */

import java.awt.*;
import javax.swing.*;

import service.UserService;

import model.User;
import dao.UserDAO;

import java.awt.event.*;

public class LoginView extends JDialog implements ActionListener{

	/**
	 * Default generated serial code.
	 */
	private static final long serialVersionUID = 1L;
	
	final JButton submitButton;
	final JPanel topPanel;
	final JPanel bottemPanel;
	final JLabel usernameLabel, passwordLabel;
	final JTextField  usernameField;
	final JPasswordField passwordField;
	
	private User user = null;
    
    public LoginView(MainView view, String title, boolean modal) {
        super(view, title, modal);
        usernameLabel = new JLabel("  Username:");
		usernameField = new JTextField(15);

		passwordLabel = new JLabel("  Password:");
		passwordField = new JPasswordField(15);

		submitButton = new JButton("SUBMIT");
		submitButton.addActionListener(this);

		topPanel = new JPanel(new GridLayout(2,2));
		topPanel.add(usernameLabel);
		topPanel.add(usernameField);
		topPanel.add(passwordLabel);
		topPanel.add(passwordField);
		
		bottemPanel = new JPanel();
		bottemPanel.add(submitButton);

		add(topPanel, BorderLayout.NORTH);
		add(bottemPanel, BorderLayout.SOUTH);
		
		//Add Window Close listener
		addWindowListener(new WindowAdapter() {
		  public void windowClosing(WindowEvent we) {
		    dispose();
		    System.exit(0);
		  }
		});

		setTitle("PLEASE LOGIN");
        this.getContentPane().add(topPanel);
        this.pack();
        this.setLocation(600, 300);
        this.setVisible(true);

    }
    
    public void actionPerformed(ActionEvent event)
	{
		// getPassword returns an array of char[], need to convert to String
		final char[] tempPassword = passwordField.getPassword();
		final String password = new String(tempPassword);
		
		// authenticates user, gets user object
		user = UserService.getInstance().authenticateUser(usernameField.getText(), password);
				
		// returns user if login succeeds 
		if (user == null) {
			// Dialog box pops up when authentication fails. 
			JOptionPane.showMessageDialog(this, "Sorry, wrong username and/or password.\n" +
				"Please try again.");
		}
		else {
	        this.setVisible(false);
	        return;
		}
	}
	
	User getUser() {
		return user;
	}
}


