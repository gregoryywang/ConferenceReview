package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import model.Paper;
import model.Review;
import model.SubProgramChair;
import model.Viewer;

public class SubPGChairView extends JFrame implements Viewer
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
	 * The default background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.MAGENTA;
	
	/**
	 * Reference to my SubProgramChair Object.
	 */
	private SubProgramChair my_subprogram_chair;
	
	/**
	 * Constructs a new SubPGhairView Object.
	 */
	public SubPGChairView()
	{
		super("Sub-Program Chair");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(BACKGROUND_COLOR);
		my_subprogram_chair = new SubProgramChair();
	}
	
	public void start()
	{
		setVisible(true);
		pack();
	}

	@Override
	public List<Paper> viewPapers() {
		return null;
	}

	@Override
	public List<Review> viewReviews() {
		return null;
	}
	
	/**
	 * Main method that will be used for testing the SubPGChairView.
	 * 
	 * @param the_args the command-line arguments
	 */
	public static void main(final String[] the_args)
	{
		new SubPGChairView().start();
	}
	
}
