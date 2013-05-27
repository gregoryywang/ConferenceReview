package gui;

/**
 * MainView.java
 * Creates a JFrame panel from which all other UI contents can be displayed.
 * @author yongyuwang
 * @version 5-20-1931
 */

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.User;


public class MainView extends JFrame {
	
    /**
	 * Default serial to suppress warnings
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Window Width.
	 */
	private static final int WIDTH = 1280;
	
	/**
   * Window Height.
   */
  private static final int HEIGHT = 720;
  
	private JPanel contentPanel;

	public MainView(){
		
        this.setTitle("Main View");
        this.setSize(WIDTH,HEIGHT);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // creates the login panel, gets authenticated user
        LoginView LoginPanel = new LoginView(MainView.this, "Login Window", true);
        User user = LoginPanel.getUser();
        
        // creates and adds HeaderView to MainView
        HeaderView headerPanel = new HeaderView(user);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);
        headerPanel.setVisible(true);
        
        contentPanel = new JPanel();
        this.getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setVisible(true);
  }
    
  public void setContentPanel(JPanel aPanel) {
    this.getContentPane().remove(contentPanel);
    contentPanel = aPanel;
    this.getContentPane().add(contentPanel, BorderLayout.CENTER);
    validate();
    repaint();
  }
  
	public static void main(String[] args){
    MainView app = new MainView();
    app.setSize(WIDTH, HEIGHT);
    app.setVisible(true);
  }
}