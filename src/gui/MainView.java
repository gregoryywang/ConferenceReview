package gui;

/**
 * MainView.java
 * Creates a JFrame panel from which all other UI contents can be displayed.
 * @author yongyuwang
 * @version 5-20-1931
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.User;

/**
 * 
 * @author Yong
 *
 */
public class MainView extends JFrame {

	/**
	 * Default serial to suppress warnings
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Window Width.
	 */
	public static final int WIDTH = 1280;

	/**
	 * Window Height.
	 */
	public static final int HEIGHT = 720;

	/**
	 * 
	 */
	private JPanel contentPanel;
	
	private Container content_pane;
	
	private HeaderView headerPanel;

	/**
	 * 
	 */
	public MainView(){

		this.setTitle("Main View");
		this.setSize(WIDTH,HEIGHT);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		User user = getUser();
		this.setVisible(true);
		
		content_pane = this.getContentPane();

		// creates and adds HeaderView to MainView
		//final HeaderView headerPanel = new HeaderView(user);
		headerPanel = new HeaderView(user);
		this.getContentPane().add(headerPanel, BorderLayout.NORTH);
		headerPanel.setVisible(true);

		//creates menu to switch users
		JMenuBar menu_bar = new JMenuBar();
		JMenu logout_menu = new JMenu("Logout");
		menu_bar.add(logout_menu);
		JMenuItem logout_item = new JMenuItem("Logout");
		logout_item.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				headerPanel.setVisible(false);
				setContentPanel(new JPanel());
				headerPanel.setUser(getUser());
				headerPanel.setVisible(true);
			}});
		logout_menu.add(logout_item);
		this.setJMenuBar(menu_bar);

		contentPanel = new JPanel();
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setVisible(true);
	}

	/**
	 * 
	 * @param aPanel
	 * @author Roshun ?
	 */
	public void setContentPanel(JPanel aPanel) {
		this.getContentPane().remove(contentPanel);
		contentPanel = aPanel;
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		validate();
		repaint();
	}

	/**
	 * 
	 * @param args the command line arguments (which are ignored.)
	 */
	public static void main(String[] args){
		MainView app = new MainView();
		app.setSize(WIDTH, HEIGHT);
		app.setVisible(true);
	}

	/**
	 * Get the user from a login screen.
	 * @return the authenticated user.
	 * @author Yong
	 */
	private User getUser() {
		// creates the login panel, gets authenticated user
		LoginView LoginPanel = new LoginView(MainView.this, "Login Window", true);
		User user = LoginPanel.getUser();
		return user;
	}
}