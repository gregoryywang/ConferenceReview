import javax.swing.JOptionPane;

/**
 * Class to test that everything is installed correctly.
 * 
 * @author LEVONNY
 * @version 127.0.0.1
 */
public final class HelloWorld 
{
	/**
	 * Private constructor to inhibit instantiation.
	 */
	private HelloWorld()
	{
		//Do nothing
	}

	/**
	 * The method to start the program.
	 * 
	 * @param the_args the command-line arguments
	 */
	public static void main(final String[] the_args) 
	{
		JOptionPane.showMessageDialog(null, "Hello, World!");
	}
}
