package GUI;

/**
 * LoginView.java
 * @author yongyuwang
 * Demonstrates the utilization of inner classes to combine JFrame and
 * ActionListener
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class LoginView extends JFrame {

	LoginView() {
		new Login(this);
	}

	/**
	 * TESTING ONLY
	 * @param args
	 */
	public static void main(String[] args) {
		new LoginView();
	}

	class Login extends JFrame implements ActionListener
	{
		final JButton submitBtn;
		final JPanel panel;
		final JLabel usernameLabel, passwordLabel;
		final JTextField  usernameField;
		final JPasswordField passwordField;

		JFrame father;

		Login(JFrame masterForm)
		{
			super("Login form");
			father = masterForm;
			usernameLabel = new JLabel("Username:");
			usernameField = new JTextField(15);

			passwordLabel = new JLabel("Password:");
			passwordField = new JPasswordField(15);

			submitBtn =new JButton("SUBMIT");
			submitBtn.addActionListener(this);

			panel = new JPanel(new GridLayout(3,2));
			panel.add(usernameLabel);
			panel.add(usernameField);
			panel.add(passwordLabel);
			panel.add(passwordField);
			panel.add(submitBtn);

			panel.add(new JLabel(""));
			add(panel,BorderLayout.CENTER);

			setTitle("PLEASE LOGIN");
			setSize(300,100);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent ee)
		{
			if(usernameField.getText().equals("admin")) {
				// getPassword returns an array of char[]
				if(new String(passwordField.getPassword()).equals("admin")) {
					JOptionPane.showMessageDialog(this, "Success!");
					return;
				}
			}
			// Dialog box pops up when authentication fails. 
			JOptionPane.showMessageDialog(this, "Sorry, wrong username and/or password\n " +
					"Please try again");
		}	
	}
}

