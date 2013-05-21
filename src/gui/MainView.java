package gui;

/**
 * MainView.java
 * Creates a JFrame panel from which all other UI contents can be displayed.
 * @author yongyuwang
 * @version BETA-5-15-1252
 */

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainView extends JFrame {
	
    /**
	 * Default serial to suppress warnings
	 */
	private static final long serialVersionUID = 1L;

	public MainView(){
        this.setTitle("Main View");
        this.setSize(1200,720);
        
        this.setVisible(true);
        
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);

        new LoginView(MainView.this, "Login Window", true);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args){
        new MainView().setVisible(true);
    }
}