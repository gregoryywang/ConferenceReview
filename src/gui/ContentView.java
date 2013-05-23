package gui;

/**
 * ContentView.java
 * @author yongyuwang
 * @version 5-22-1618
 * 
 * Stub for ContentView. Details under construction.
 */

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Role;
import model.User;

public class ContentView extends JPanel {
		
	public ContentView(User the_user) {
		
		Role my_role = the_user.getRole();
		
		// DEBUG
		System.out.println("The current role is: " + my_role);
		/**
		if (my_role.equals("Administrator")) {
			
			final JFrame frame = new JFrame("AdminView");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			final AdminView admin_view = new AdminView();
			admin_view.fillPanel();
			frame.add(admin_view);
			frame.pack();
			frame.setVisible(true);
		}
		**/
		
		
	}
	

}
