package gui;

/**
 * ContentView.java
 * @author yongyuwang
 * @version 5-22-1618
 * 
 * Stub for ContentView. Details under construction.
 */

import javax.swing.JPanel;

import model.Role;
import model.User;

public class ContentView extends JPanel {
	
	public ContentView(User the_user) {
		Role the_role = the_user.getRole();
		if (the_role.equals("Administrator")) {
			
			
		}
		
		
	}
	

}
