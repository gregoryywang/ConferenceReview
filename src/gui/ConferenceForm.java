package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that creates a ConferenceForm Object
 * that represents the "form" that a User
 * sees when they click on a Conference Object.
 * 
 * @author Levon Kechichian
 * @version 1.0
 */
public class ConferenceForm extends JFrame
{
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default frame width.
	 */
	private static final int FRAME_WIDTH = 500;
	
	/**
	 * The default frame height.
	 */
	private static final int FRAME_HEIGHT = 300;
	
	/**
	 * Creates a default ConferenceForm Object.
	 */
	public ConferenceForm()
	{
		super("Conference Form");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void start()
	{
		add(new ConferencePanel(), BorderLayout.CENTER);
		add(new JLabel("THIS IS THE NORTH LABEL"), BorderLayout.NORTH);
		add(new JLabel("This is the DIRTY SOUF label!!"), BorderLayout.SOUTH);
		add(new JLabel("This is the WEST COAST labeeel"), BorderLayout.WEST);
		add(new JLabel("This is the EASY COASTY LBl"), BorderLayout.EAST);
		setVisible(true);
		pack();
	}
	
	/**
	 * Method to test my GUI.
	 * 
	 * @param the_args the command-line arguments
	 */
	public static void main(final String[] the_args)
	{
		new ConferenceForm().start();
	}
	
	private class ConferencePanel extends JPanel
	{
		/**
		 * The default serial version UID.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * The number of textfield rows.
		 */
		private static final int ROWS = 2;
		
		/**
		 * The number of textfield columns.
		 */
		private static final int COLUMNS = 10;

		/**
		 * Constructs a default ConferencePanel Object.
		 */
		public ConferencePanel()
		{
			super(new GridLayout(ROWS, COLUMNS));
			setBackground(Color.GREEN);
			setVisible(true);
		}
		
		
	}
	
}
