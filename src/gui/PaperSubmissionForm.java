package gui;

/**
 * PaperSubmissionForm.java
 * @author yongyuwang
 * @version 5-27-1950
 * Displays an new paper submission form
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Author;
import model.Paper;
import model.User;

public class PaperSubmissionForm extends JFrame {
	
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	
	/**
	 * The default frame width.
	 */
	private static final int FRAME_WIDTH = 300;
	
	/**
	 * The default frame height.
	 */
	private static final int FRAME_HEIGHT = 300;
	
	private Author user;

	
	final JLabel nameLabel, name, titleLabel, keywordsLabel, catagoryLabel;
	final JTextField titleField, keywordsField, catagoryField;
	final BackgroundTextArea paperAbstract, paperContent;
	
	final JButton submitButton, cancelButton;
	final JPanel topPanel, midPanel, bottemPanel;
	
	
	public PaperSubmissionForm(final Author the_user) {
		
		super("New Paper Submission");
		this.user = the_user;
		
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		nameLabel = new JLabel("Your name: ");
		name = new JLabel(firstName + " " + lastName);
		
		titleLabel = new JLabel("Submission title:");
		titleField = new JTextField(15);
		
		keywordsLabel = new JLabel("Keywords:");
		keywordsField = new JTextField(15);
		
		catagoryLabel = new JLabel("Catagory:");
		catagoryField = new JTextField(15);
		
		topPanel = new JPanel(new GridLayout(4,2));
		
		topPanel.add(nameLabel);
		topPanel.add(name);
		
		topPanel.add(titleLabel);
		topPanel.add(titleField);
		
		topPanel.add(keywordsLabel);
		topPanel.add(keywordsField);
		
		topPanel.add(catagoryLabel);
		topPanel.add(catagoryField);
		
		paperAbstract = new BackgroundTextArea("Paste or type your abstract here.");
		paperAbstract.setWrapStyleWord(true);
		JScrollPane abstractScrollPane = new JScrollPane(paperAbstract);
		
		paperContent = new BackgroundTextArea("Paste or type your paper here.");
		paperContent.setWrapStyleWord(true);
		JScrollPane contentScrollPane = new JScrollPane(paperContent);
				
		midPanel = new JPanel(new GridLayout(2,1));
		midPanel.add(abstractScrollPane);
		midPanel.add(contentScrollPane);

		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent the_event)
			{				
				// initialize new paper object using information from submission form
				Paper myPaper = new Paper(user, titleField.getText(), keywordsField.getText(),
						paperAbstract.getText(), catagoryField.getText(), paperContent.getText());
				
				// saves the paper into database
				try {
					user.submitPaper(myPaper);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(new JDialog(), "Your paper has been submitted.");
			}
		});
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent the_event)
			{
				dispose();
			}
		});
		
		bottemPanel = new JPanel();
		bottemPanel.add(submitButton);
		bottemPanel.add(cancelButton);
		
		add(topPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottemPanel, BorderLayout.SOUTH);

		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(BACKGROUND_COLOR);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public static void main(String [] args) {
		User test = new User();
		Author test2 = new Author(test);
		new PaperSubmissionForm(test2).setVisible(true);
	}

}

class BackgroundTextArea extends JTextArea implements FocusListener {

    /**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	private final String backgroundText;

    public BackgroundTextArea(final String backgroundText) {
        super(backgroundText);
        this.backgroundText = backgroundText;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText(backgroundText);
        }
    }

    @Override
    public String getText() {
        String typed = super.getText();
        return typed.equals(backgroundText) ? "" : typed;
    }
}
